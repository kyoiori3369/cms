package org.itachi.codestar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/23 19:29
 */
@RestController
public class UploadController {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    @Value("${code.star.upload-file}")
    private String filePath;

    @PostMapping(path = "upload-file", produces = "text/plain;utf-8")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        long time = System.currentTimeMillis();
        String day = FORMAT.format(new Date());
        if (!file.isEmpty()) {
            File path = new File(filePath.replace("/", File.separator) + File.separator + day.replace("/", File.separator));
            if (!path.exists()) {
                path.mkdirs();
            }
            File localFile = new File(path.getAbsolutePath() + File.separator + time + file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(localFile, file.getBytes());
        }
        Map<String, String> map = new HashMap<>(2);
        map.put("path", "/uploads/" + day + "/" + time + file.getOriginalFilename());
        map.put("src", request.getContextPath() + map.get("path"));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

}
