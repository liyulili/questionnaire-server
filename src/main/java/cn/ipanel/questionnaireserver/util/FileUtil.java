package cn.ipanel.questionnaireserver.util;

import cn.ipanel.questionnaireserver.constant.FtpConstant;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author liyu
 * @date 2020/3/13 13:48
 * @description
 */
@UtilityClass
public class FileUtil {

    public File getTempDir(String temp) {
        if (StringUtils.isBlank(temp)) {
            temp = FtpConstant.localTemp;
        }
        //指定暂时存放文件的目录
        String tempFileDir = FtpUtil.class.getResource("/").getPath() + temp;
        tempFileDir = tempFileDir.substring(1);
        System.out.println("tempFileDir = " + tempFileDir);
        File dir = new File(tempFileDir);
        //判断目录是否存在，不存在则创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public File saveMultipartFile(String fileName, MultipartFile multipartFile, File dir) {
        OutputStream out = null;
        byte[] bytes = new byte[0];
        File file = null;
        try {
            //获取文件字节数组
            bytes = multipartFile.getBytes();
            //生成新文件名，防止文件名重复而导致文件覆盖
            //1、获取原文件后缀名 .img .jpg ....
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //2、使用UUID生成新文件名
            fileName = StringUtils.isBlank(fileName) ? String.valueOf(UUID.randomUUID()) : fileName;
            String newFileName = fileName + suffix;
            //3、生成文件 C:\ftpfile\img\sdasdasd.jpg
             file = new File(dir, newFileName);
            //4、传输内容
            //写入指定文件夹


            out = new FileOutputStream(file);
            out.write(bytes);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

//使用此方法保存必须要绝对路径且文件夹必须已存在,否则报错
//        multipartFile.transferTo(file);

    }
}