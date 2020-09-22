package com.wisrc.sales.webapp.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

public class UploadFile {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }


    public static boolean checkFile(MultipartFile file) {

        StringBuffer sb = null;
        //允许上传的文件类型
        String fileType = "doc,docx,xls,xlsx,pdf,zip,rar";
        //允许上传的文件最大大小(10M,单位为byte)
        int maxSize = 1024 * 1024 * 10;
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String extName = fileName.substring(fileName.indexOf(".") + 1).toLowerCase().trim();
        if (file.getSize() > maxSize || !Arrays.<String>asList(fileType.split(",")).contains(extName)) {
            return false;
        } else {
            return true;
        }
    }
}
