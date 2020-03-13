package cn.ipanel.questionnaireserver.util;

/**
 * @author liyu
 * @date 2020/3/3 11:26
 * @description
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "文件上传/下载===ftp服务器:")
public class FtpUtil {
    private static FTPClient mFTPClient = new FTPClient();
    private static FtpUtil ftp = new FtpUtil();

    public FtpUtil() {
        // 在控制台打印操作过程
        mFTPClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }


    /**
     * 上传文件到ftp服务器
     */
    public static boolean ftpUpload(String fileName, String ftpUrl, int ftpPort,
                                    String ftpUsername, String ftpPassword, String ftpLocalDir, String ftpRemotePath) {
        boolean result = false;
        try {
            boolean isConnection = ftp.openConnection(ftpUrl, ftpPort, ftpUsername, ftpPassword);
            if (isConnection) {
                boolean isSuccess = ftp.upload(ftpRemotePath, ftpLocalDir);
                if (isSuccess) {
                    log.info("文件上传成功！");
                    result = true;
                } else {
                    log.info("文件上传失败！");
                    result = false;
                }
                ftp.logout();
            } else {
                log.info("链接ftp服务器失败，请检查配置信息是否正确！");
                result = false;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从ftp服务器下载文件到本地
     */
    public static boolean ftpDownload(String fileName, String ftpUrl, int ftpPort,
                                      String ftpUsername, String ftpPassword, String ftpRemotePath, String ftpDownDir) {
        boolean result = false;
        try {
            boolean isConnection = ftp.openConnection(ftpUrl, ftpPort, ftpUsername, ftpPassword);
            if (isConnection) {
                boolean isDownloadOk = ftp.downLoad(fileName, ftpDownDir);
                boolean isCreateOk = ftp.createDirectory(ftpRemotePath, mFTPClient);
                if (isDownloadOk && isCreateOk) {
                    log.info("文件下载成功！");
                    result = true;
                } else {
                    log.info("文件下载失败！");
                    result = false;
                }
                ftp.logout();
            } else {
                log.info("链接ftp服务器失败，请检查配置信息是否正确！");
                result = false;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 连接ftp服务器
     *
     * @param host    ip地址
     * @param port    端口号
     * @param account 账号
     * @param pwd     密码
     * @return 是否连接成功
     * @throws SocketException
     * @throws IOException
     */
    private boolean openConnection(String host, int port, String account, String pwd)
            throws SocketException, IOException {
        mFTPClient.setControlEncoding("UTF-8");
        mFTPClient.connect(host, port);

        if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
            mFTPClient.login(account, pwd);
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                System.err.println(mFTPClient.getSystemType());
                FTPClientConfig config = new FTPClientConfig(mFTPClient.getSystemType().split(" ")[0]);
                config.setServerLanguageCode("zh");
                mFTPClient.configure(config);
                return true;
            }
        }
        disConnection();
        return false;
    }

    /**
     * 登出并断开连接
     */
    public void logout() {
        System.err.println("logout");
        if (mFTPClient.isConnected()) {
            System.err.println("logout");
            try {
                mFTPClient.logout();
                disConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 断开连接
     */
    private void disConnection() {
        if (mFTPClient.isConnected()) {
            try {
                mFTPClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件到本地地址
     *
     * @param remotePath 远程地址
     * @param localDir   本地地址
     * @throws IOException
     */
    public boolean downLoad(String remotePath, String localDir) throws IOException {
        // 进入被动模式
        mFTPClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
        FTPFile[] ftpFiles = mFTPClient.listFiles(remotePath);
        if (ftpFiles == null || ftpFiles.length == 0) {
            log.info("远程文件不存在");
            return false;
        } else if (ftpFiles.length > 1) {
            log.info("远程文件是文件夹");
            return false;
        }
        long lRemoteSize = ftpFiles[0].getSize();
        // 本地文件的地址
        File localFileDir = new File(localDir);
        if (!localFileDir.exists()) {
            localFileDir.mkdirs();
        }
        File localFile = new File(localFileDir, ftpFiles[0].getName());
        long localSize = 0;
        FileOutputStream fos = null;
        if (localFile.exists()) {
            if (localFile.length() == lRemoteSize) {
                System.err.println("已经下载完毕");
                return true;
            } else if (localFile.length() < lRemoteSize) {
                // 要下载的文件存在，进行断点续传
                localSize = localFile.length();
                mFTPClient.setRestartOffset(localSize);
                fos = new FileOutputStream(localFile, true);
            }
        }
        if (fos == null) {
            fos = new FileOutputStream(localFile);
        }
        InputStream is = mFTPClient.retrieveFileStream(remotePath);
        byte[] buffers = new byte[1024];
        long step = lRemoteSize / 10;
        long process = localSize / step;
        int len = -1;
        while ((len = is.read(buffers)) != -1) {
            fos.write(buffers, 0, len);
            localSize += len;
            long newProcess = localSize / step;
            if (newProcess > process) {
                process = newProcess;
                System.err.println("下载进度:" + process);
            }
        }
        is.close();
        fos.close();
        boolean isDo = mFTPClient.completePendingCommand();
        if (isDo) {
            System.err.println("下载成功");
        } else {
            System.err.println("下载失败");
        }
        return isDo;

    }

    /**
     * 创建远程目录
     *
     * @param remote    远程目录
     * @param ftpClient ftp客户端
     * @return 是否创建成功
     * @throws IOException
     */
    public boolean createDirectory(String remote, FTPClient ftpClient) throws IOException {
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        System.out.println("directory = " + directory);
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(directory)) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = remote.substring(start, end);
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                        System.out.println("WorkingDirectory = " + ftpClient.printWorkingDirectory());
                    } else {
                        System.err.println("创建目录失败");
                        return false;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                if (end <= start) {
                    break;
                }
            }
        }
        return true;
    }

    /**
     * 上传的文件
     *
     * @param remotePath 上传文件的路径地址（文件夹地址）
     * @param localPath  本地文件的地址
     * @throws IOException 异常
     */
    public boolean upload(String remotePath, String localPath) throws IOException {
        System.out.println("remotePath = " + remotePath);
        BufferedInputStream inStream = null;
        // 进入被动模式
//        mFTPClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            System.err.println("本地文件不存在");
            return false;
        }
        String fileName = localFile.getName();
        if (remotePath.contains("/")) {
            boolean isCreateOk = createDirectory(remotePath, mFTPClient);
            if (!isCreateOk) {
                System.err.println("文件夹创建失败");
                return false;
            }
        }
        inStream = new BufferedInputStream(new FileInputStream(localPath));
        mFTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        System.out.println("mFTPClient = " + mFTPClient.printWorkingDirectory());
        boolean storeFile = mFTPClient.storeFile(fileName, inStream);
        System.err.println("是否上传成功：" + storeFile);
        return storeFile;
    }

    public boolean uploadFile(String remotePath, String localPath, String remoteFileName) throws IOException {
        System.out.println("remotePath = " + remotePath);
        BufferedInputStream inStream = null;
        // 进入被动模式
        mFTPClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            System.err.println("本地文件不存在");
            return false;
        }
        if (remotePath.contains("/")) {
            boolean isCreateOk = createDirectory(remotePath, mFTPClient);
            if (!isCreateOk) {
                System.err.println("文件夹创建失败");
                return false;
            }
        }
        inStream = new BufferedInputStream(new FileInputStream(localPath));
        mFTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        boolean storeFile = mFTPClient.storeFile(remoteFileName, inStream);
        System.err.println("是否上传成功：" + storeFile);
        return storeFile;
    }


    /**
     * 删除目录
     *
     * @param ftpClient
     * @param remoteDirPath
     * @return
     */
    public static boolean deleteDirectory(FTPClient ftpClient, String remoteDirPath) {
        boolean flag = false;
        log.info("Begin delete FTP Directory: {}", remoteDirPath);
        try {
            if (!existDir(ftpClient, remoteDirPath)) {
                log.info("Target Path: {} is not a directory in ftp; delete failed", remoteDirPath);
                return false;
            }

            FTPFile[] ftpFileArr = ftpClient.listFiles(new String(remoteDirPath.getBytes("utf-8"), "iso-8859-1"));
            for (FTPFile ftpFile : ftpFileArr) {
                String fileName = ftpFile.getName();
                if (ftpFile.isDirectory()) {
                    deleteDirectory(ftpClient, remoteDirPath + "/" + fileName);
                } else {
                    deleteFile(ftpClient, remoteDirPath + '/' + fileName);
                }
            }
            if (ftpClient.listFiles(new String(remoteDirPath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)).length == 0) {
                if (ftpClient.removeDirectory(new String(remoteDirPath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))) {
                    log.info("Delete Empty Directory: {} successful", remoteDirPath);
                } else {
                    log.info("Delete Empty Directory: {} failed", remoteDirPath);
                }
            }
            flag = true;
        } catch (Exception e) {
            log.error("Delete FTP Directory: {} failed", remoteDirPath);
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断目录是否存在
     *
     * @param ftpClient
     * @param dir
     * @param fileName
     * @return
     * @throws IOException
     */
    private static boolean existFile(FTPClient ftpClient, String dir, String fileName) throws IOException {
        FTPFile[] ftpFileArr = ftpClient.listFiles(new String(dir.getBytes("utf-8"), "iso-8859-1"));
        if (ftpFileArr.length > 0) {
            for (FTPFile f : ftpFileArr) {
                if (f.getName().trim().equals(fileName)) {
                    log.info("Target file: {} is exist in FTP: {}", fileName, dir);
                    return true;
                }
            }
        }
        log.info("Target file: {} is not exist in FTP: {}", fileName, dir);
        return false;
    }

    /**
     * 判断目录是否存在
     *
     * @param ftpClient
     * @param path
     * @return
     * @throws IOException
     */
    private static boolean existDir(FTPClient ftpClient, String path) throws IOException {
        String dirPath;
        if (!"/".equals(path) && path.endsWith("/")) {
            path = path.substring(0, path.lastIndexOf("/"));
        }
        if (path.lastIndexOf("/") == 0) {
            return true;
        } else {
            dirPath = path.substring(0, path.lastIndexOf("/"));
        }

        String dirName = path.substring(path.lastIndexOf("/") + 1);
        FTPFile[] ftpFiles = ftpClient.listDirectories(new String(dirPath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.getName().trim().equals(dirName)) {
                log.info("Target directory: {} is exist in FTP: {}", dirName, dirPath);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param ftpClient
     * @param remoteFilePath
     * @return
     */
    public static boolean deleteFile(FTPClient ftpClient, String remoteFilePath) {
        boolean flag = false;
        log.info("Begin delete FTP File: {}", remoteFilePath);
        try {
            if (remoteFilePath.endsWith("/")
                    || !existFile(ftpClient, remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/")),
                    remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1, remoteFilePath.length()))) {
                log.info("Target File: {} is not exist in ftp or is not a file", remoteFilePath);
                return false;
            }
            if (ftpClient.deleteFile(new String(remoteFilePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))) {
                log.info("Delete FTP file: {} successful", remoteFilePath);
                flag = true;
            } else {
                log.info("Delete FTP file: {} failed", remoteFilePath);
            }
        } catch (Exception e) {
            log.error("Delete FTP file: {} failed", remoteFilePath);
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

}