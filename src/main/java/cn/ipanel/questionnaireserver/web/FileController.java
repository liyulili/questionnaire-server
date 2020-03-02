package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.vo.R;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author liyu
 * @date 2020/3/2 17:33
 * @description 文件上传，下载类接口
 */
@RestController
public class FileController {

    /**
     * Save the uploaded file to this folder
     */
    private static String UPLOADED_FOLDER = "E://temp//";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");


    @PostMapping("/upload")
    public R singleFileUpload(HttpServletRequest req, MultipartFile file) {
        StringBuffer url = new StringBuffer();
        String filePath = "/questionnaire/" + LocalDateTime.now().format(formatter);
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(file.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return R.ok(url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error("上传失败!");
    }


}
