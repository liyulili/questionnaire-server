package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.config.FtpConfig;
import cn.ipanel.questionnaireserver.util.FtpUtil;
import cn.ipanel.questionnaireserver.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author liyu
 * @date 2020/3/2 17:33
 * @description 文件上传，下载类接口
 */
@RestController
public class FileController {


    private static String remotePath = "/questionnaire/upload/";
    private static String address = "http://192.168.37.50:8070/partybuilding/";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");

    @Autowired
    FtpConfig ftpConfig;

    @PostMapping("/upload")
    public R singleFileUpload(MultipartFile multipartFile) throws IOException {

        //指定暂时存放文件的目录
        String tempFileDir = FtpUtil.class.getResource("/").getPath()+"uploadTemp";
        System.out.println("tempFileDir = " + tempFileDir);
        File dir = new File(tempFileDir);
        //判断目录是否存在，不存在则创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //生成新文件名，防止文件名重复而导致文件覆盖
        //1、获取原文件后缀名 .img .jpg ....
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //2、使用UUID生成新文件名
        String newFileName = UUID.randomUUID() + suffix;
        //3、生成文件 C:\ftpfile\img\sdasdasd.jpg
        File file = new File(dir, newFileName);
        //4、传输内容
        multipartFile.transferTo(file);
        //5、上传至ftp服务器
        boolean b = FtpUtil.ftpUpload(newFileName, ftpConfig.getUrl(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), file.getPath(), remotePath);
        System.out.println("b = " + b);
        //6、删除本地文件
        boolean delete = file.delete();
        System.out.println("delete = " + delete);
        return R.ok(address.substring(0, address.length() - 1) + remotePath + newFileName);
    }


}
