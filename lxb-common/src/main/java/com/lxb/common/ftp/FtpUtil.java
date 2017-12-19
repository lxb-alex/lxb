package com.lxb.common.ftp;

import com.lxb.common.utils.PropertiesFileUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @description
 * @author Liaoxb
 * @date 2017/12/18 0018 17:42:42
 */
public class FtpUtil {

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static String hostName; // FTP服务器地址
    private static Integer port;    // FTP端口
    private static String userName; // FTP登录账号
    private static String password; // FTP登录密码
    private static String pathName; // FTP服务器文件路径
    private static FTPClient ftpClient;

    /**
     * 初始参数
     */
    private static void init(){
        hostName = PropertiesFileUtil.getPropertiesValue("ftpConfig.properties", "hostName");
        port =  Integer.parseInt(PropertiesFileUtil.getPropertiesValue("ftpConfig.properties", "port"));
        userName = PropertiesFileUtil.getPropertiesValue("ftpConfig.properties", "userName");
        password = PropertiesFileUtil.getPropertiesValue("ftpConfig.properties", "password");
        pathName = PropertiesFileUtil.getPropertiesValue("ftpConfig.properties", "pathName");
        ftpClient = new FTPClient();
    }

    /**
     * 连接登录 FTP 服务器
     * @return boolean
     */
    public static boolean connect(){
        init();
        boolean flag = false;
        try {
            // 链接FTP服务器
            ftpClient.connect(hostName, port);
            // 被动方式
            ftpClient.enterLocalPassiveMode();
            // 登录 FTP 服务器
            ftpClient.login(userName, password); // 匿名登录：ftpClient.login("anonymous", "");
            // 验证 是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)){
                ftpClient.disconnect();
                logger.error("Connect " + hostName + " success!");
                return  flag;
            }
            // 设置二进制文件类型
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 中文支持
            ftpClient.setControlEncoding("GBK");
            // 进入存放文件目录
            ftpClient.changeWorkingDirectory(pathName);
        } catch (IOException e) {
            logger.error("Connect " + hostName + " failed!");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 登出 断开 FTP 服务
     */
    private static void closeFtp (){
        if (ftpClient!=null && ftpClient.isConnected()){
            try {
                ftpClient.logout(); // 登出
                ftpClient.disconnect(); // 关闭连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FTP 文件上传
     * @param file 文件 或 目录
     */
    public static void upload(File file){
        // 文件夹上传
        if (file.isDirectory()){
            try {
                // FTP 服务器创建文件夹
                ftpClient.makeDirectory(file.getName());
                // 进入刚创建的file.getName()文件目录
                ftpClient.changeWorkingDirectory(file.getName());
                // 循环目录下的文件
                String[] files = file.list();
                for (String fileName : files){
                    File childrenFile = new File(file.getPath() + "/" + fileName);
                    if (childrenFile.isDirectory()){
                        upload(childrenFile);
                        ftpClient.changeToParentDirectory();
                    }else {
                        FileInputStream input = new FileInputStream(childrenFile);
                        ftpClient.storeFile(childrenFile.getName(), input);
                        input.close();
                    }
                }
            } catch (IOException e) {
                logger.error("File directory name " + file.getName() + " upload failed !" );
                e.printStackTrace();
            }
        }
        // 单文件上传
        else{
            File file1 = new File(file.getPath());
            try {
                FileInputStream input = new FileInputStream(file1);
                ftpClient.storeFile(file1.getName(), input);
                input.close();
            } catch (FileNotFoundException e) {
                logger.error("File name " + file1.getName() + " not found !" );
                e.printStackTrace();
            } catch (IOException e) {
                logger.error("File name " + file1.getName() + " upload failed !" );
                e.printStackTrace();
            }
        }
        closeFtp();
    }

    /**
     * FTP 文件下载
     * @param downloadFileName  下载文件名称（目录）
     * @param relativeLocalPath 文件下载到本地地址
     * @param relativeRemotePath 文件在 FTP 服务上地址
     * @return
     */
    private static boolean download(String downloadFileName , String relativeLocalPath, String relativeRemotePath){
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(relativeRemotePath + downloadFileName);
            // 判断是否是空目录
            if (ftpFiles.length == 0){
                return false;
            }
            // 单文件下载
            if (ftpFiles.length == 1){
                // 判断本地是否已经存在该文件
                File localFile = new File(relativeLocalPath + "/" + downloadFileName);
                if (!localFile.exists()){
                    OutputStream out = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ftpFiles[0].getName(), out);
                    out.close();
                }
            }
            // 目录下载
            else {
                String newLocalPath = relativeLocalPath + downloadFileName;
                File localFile = new File(newLocalPath);
                // 判断本地是否存在该目录
                if (!localFile.exists()){
                    localFile.mkdirs();
                    for (FTPFile ftpFile: ftpFiles){
                        // 判断目录中是否存在该文件
                        File tempLocalFile = new File(newLocalPath + "/" + ftpFile.getName());
                        if (!tempLocalFile.exists()){
                            OutputStream out = new FileOutputStream(tempLocalFile);
                            ftpClient.retrieveFile(ftpFile.getName(), out);
                            out.flush();
                            out.close();
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeFtp();
        }
        return false;
    }

    /**
     * FTP 浏览器下载
     * @param downloadFileName 下载文件名称（目录）
     * @param relativeRemotePath 下载文件在 FTP 地址
     * @param response
     * @return
     */
    public static boolean download(String downloadFileName, String relativeRemotePath, HttpServletResponse response){
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(downloadFileName);
            // 判断是否是空目录
            if (ftpFiles.length==0){
                return false;
            }
            String fileSize = null;
            for (FTPFile file : ftpFiles){
                if (file.getName().equals(downloadFileName)){
                    fileSize = String.valueOf(file.getSize());
                }
            }
            InputStream input = ftpClient.retrieveFileStream(new String((relativeRemotePath + downloadFileName).getBytes("GBK"),"ISO-8859-1"));
            // 该目录下不存在要下载的文件
            if (input==null){
                return false;
            }
            // 设置Response返回信息
            response.setContentType("multipart/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(downloadFileName.getBytes("GBK"), "ISO-8859-1"));
            response.setHeader("Content-Length", fileSize);
            OutputStream output = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len= input.read(bytes)) != -1){
                output.write(bytes, 0 ,len);
            }
            input.close();
            output.flush();
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeFtp();
        }
        return false;
    }

}
