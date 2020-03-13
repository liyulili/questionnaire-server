package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.config.FtpConfig;
import cn.ipanel.questionnaireserver.constant.FtpConstant;
import cn.ipanel.questionnaireserver.util.FileUtil;
import cn.ipanel.questionnaireserver.util.FtpUtil;
import cn.ipanel.questionnaireserver.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static cn.ipanel.questionnaireserver.constant.FtpConstant.remotePath;

/**
 * @author liyu
 * @date 2020/3/2 17:33
 * @description 文件上传，下载类接口
 */
@RestController
@RequestMapping("/ftp")
public class FileController {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");

    @Autowired
    FtpConfig ftpConfig;

    @PostMapping("/uploadFile")
    public R singleFileUpload(MultipartFile multipartFile) {
        //获取本地temp目录
        File dir = FileUtil.getTempDir(null);
        File file = FileUtil.saveMultipartFile(null, multipartFile, dir);

        //5、上传至ftp服务器
        boolean b = FtpUtil.ftpUpload(file.getName(), ftpConfig.getUrl(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), file.getPath(), remotePath);
        System.out.println("b = " + b);
        //6、删除本地文件
        String fileName = file.getName();
        boolean delete = file.delete();
        //ToDo 文件不知道为什么一直删除失败
        System.out.println("delete = " + delete);
        if (b) {
            return R.ok(FtpConstant.address.substring(0, FtpConstant.address.length() - 1) + remotePath + fileName);
        } else {
            return R.error();
        }
    }

    @PostMapping("/uploadFiles")
    public R MluFileUpload(MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();
        //获取本地temp目录
        File dir = FileUtil.getTempDir(null);
        for (MultipartFile multipartFile : multipartFiles) {

            File file = FileUtil.saveMultipartFile(null, multipartFile, dir);
            //5、上传至ftp服务器
            boolean b = FtpUtil.ftpUpload(file.getName(), ftpConfig.getUrl(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), file.getPath(), remotePath);
            System.out.println("b = " + b);
            String fileName = file.getName();
            boolean delete = file.delete();
            if (b) {
                paths.add(FtpConstant.address.substring(0, FtpConstant.address.length() - 1) + remotePath + fileName);

            } else {
                return R.error();
            }
        }
        return R.ok(paths);
    }
}
