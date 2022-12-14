package com.tkcx.api.utils;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Properties;
import java.util.Vector;
/**
 * 类说明 sftp工具类
 */
public class SFTPUtil {

    private static Logger logger = Logger.getLogger(SFTPUtil.class);

    private ChannelSftp sftp;

    private Session session;
    /** SFTP 登录用户名*/
    @Value("${afe.sftp.username}")
     String username;
    /** SFTP 登录密码*/
    @Value("${afe.sftp.password}")
    private String password;
    /** 私钥 */
    private String privateKey;

    /** SFTP 服务器地址IP地址*/
    @Value("${afe.sftp.host}")
    private String host;

    /** SFTP 端口*/
    @Value("${afe.sftp.port}")
    private int port;



    /**
     * 构造基于密码认证的sftp对象
     */
    public SFTPUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SFTPUtil(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SFTPUtil(){}


    /**
     * 连接sftp服务器
     */
    public void login(){
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }

            session = jsch.getSession(username, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout(){
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }


    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param basePath  服务器的基础路径
     * @param directory  上传到该目录
     * @param sftpFileName  sftp端文件名
     * @param input   输入流
     */
    public void upload(String basePath,String directory, String sftpFileName, InputStream input) throws SftpException{
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String [] dirs=directory.split("/");
            String tempPath=basePath;
            for(String dir:dirs){
                if(null== dir || "".equals(dir)) continue;
                tempPath+="/"+dir;
                try{
                    sftp.cd(tempPath);
                }catch(SftpException ex){
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        sftp.put(input, sftpFileName);  //上传文件
    }


    /**
     * 下载文件。
     * @param downLoadPath 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     */
    public void download(String downLoadPath, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{
        if (downLoadPath != null && !"".equals(downLoadPath)) {
            sftp.cd(downLoadPath);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        return fileData;
    }


    /**
     * 删除文件
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException{
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }


    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    //测试上传和下载
    public static void main(String[] args) throws SftpException, IOException {
        SFTPUtil sftp = new SFTPUtil("ftpuser", "qnsftp", "132.232.91.214", 8022);
        sftp.login();
        File file = new File("D:\\2.txt");
        InputStream is = new FileInputStream(file);

        sftp.upload("/upload/KNQNJY01/","ftp", "2.txt", is);
        sftp.logout();
        //下载测试
        sftp.login();
        sftp.download("/upload/KNQNJY01/ftp", "2.txt", "D:\\data\\acctprint\\fileserver\\download\\2.txt");
        sftp.logout();

    }


    /**
     * 上传文件调用类
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param baseUploadPath   服务器的基础路径
     * @param upLoadDirectory 上传到该目录
     * @param sftpFileName  sftp端文件名
     * @param localFilePath 本地服务器文件路径包括文件名
     * @throws SftpException
     * @throws IOException
     */
    public  void uploadFile(String host,int port,String username,String password,String baseUploadPath,String upLoadDirectory,String sftpFileName,String localFilePath)  {
        try {
            SFTPUtil sftp = new SFTPUtil(username, password, host, port);
            sftp.login();
            File localfile = new File(localFilePath);
            InputStream is = new FileInputStream(localfile);
            sftp.upload(baseUploadPath,upLoadDirectory, sftpFileName, is);
            sftp.logout();
        } catch (Exception e) {
            logger.info("SFTPUtil uploadFile exception:"+e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
    /**
     * 下载文件调用类
     * @param downloadPath   sftp服务器的路径
     * @param downloadFile  sftp端文件名
     * @param saveLocalFile 本地服务器文件路径包括文件名
     * @throws SftpException
     * @throws IOException
     */
    public  boolean downloadFile(String downloadPath,String downloadFile,String saveLocalFile,String host,int port,String username,String password) {
        boolean reslut=true;
        try {
            SFTPUtil sftp = new SFTPUtil(username, password, host, port);
            logger.info("username:"+username);
            logger.info("password:"+password);
            logger.info("host:"+host);
            logger.info("port:"+port);
            sftp.login();
            sftp.download(downloadPath, downloadFile, saveLocalFile);
            sftp.logout();
            return reslut;
        } catch (Exception e) {
            logger.info("SFTPUtil downloadFile exception:"+e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
