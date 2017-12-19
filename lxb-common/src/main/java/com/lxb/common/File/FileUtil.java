package com.lxb.common.File;

import com.lxb.common.utils.DateUtil;
import com.lxb.common.utils.PropertiesFileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/12/19 0019 15:34:34
 */
public class FileUtil extends FileUtils {

    public static String getUploadPath(){
        String uploadDir = PropertiesFileUtil.getPropertiesValue("config.properties", "uploadDir");
        String dayDir = DateUtil.getNowDateStr("yyyyDDmm");
        return File.separator + uploadDir + File.separator + dayDir;
    }

    /**
     * 文件下载
     * @param uploadPath 文件绝对路径 String uploadPath = request.getSession().getServletContext().getRealPath(getUploadPath());
     * @param file 上传的文件
     * @return 文件在服务器上的相对路径
     */
    public static String upload(String uploadPath, MultipartFile file){
        // 判断文件目录是否存在
        File fileDir = new File(uploadPath);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        if (!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String saveFilePathName = uploadPath + File.separator + fileName;
            File saveFile = new File(saveFilePathName);
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return saveFilePathName.substring(saveFilePathName.indexOf(getUploadPath()));
        }
        return null;
    }

    /**
     * 文件下载
     * @param uploadPath 文件绝对路径 String uploadPath = request.getSession().getServletContext().getRealPath(getUploadPath());
     * @param files 上传的文件
     * @return 文件在服务器上的相对路径
     */
    public static String upload (String uploadPath, MultipartFile[] files){
        StringBuilder filePathName = new StringBuilder();
        if (files.length != 0){
            for (MultipartFile file: files){
                filePathName.append(upload(uploadPath, file)).append(",");
            }
            if (StringUtils.isNotEmpty(filePathName.toString())){
                return filePathName.substring(0, filePathName.lastIndexOf(","));
            }
        }
        return null;
    }

/*    *//**
     * 直接在Controller或则service中使用
     * @param fileName
     *//*
    public static void download(String fileName){
        String uploadPath = request.getSession().getServletContext().getRealPath(getUploadPath());
        File file = new File(uploadPath + File.separator + fileName);
        if (file.exists()){
            response.setContentType("multipart/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);//以下载方式接收
            response.setHeader("Content-Length", String.valueOf(file.length()));

            InputStream inputStream = new FileInputStream(uploadPath + fileName);
            OutputStream out = response.getOutputStream();
            writeFile(inputStream, out);
        }
    }*/

    /**
     * 写出文件
     * @param inputStream
     * @param outputStream
     */
    public static void writeFile(InputStream inputStream, OutputStream outputStream){
        byte[] bytes = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
