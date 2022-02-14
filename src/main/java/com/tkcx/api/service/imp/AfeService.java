package com.tkcx.api.service.imp;

import com.thoughtworks.xstream.XStream;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.vo.base.BodyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sxxh.LGBC.sxxh_LGBC;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @project：
 * @author： zhaodan
 * @description： afe前端改造
 * @create： 2022/2/9 15:48
 */
@Slf4j
@Service
public class AfeService {

    @Value("${bank.esb.url}")
    private String esbURL;
    private static final String XML_DECLARATION_BLOCK = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
    private static final int TOTAL_LENGTH_BIT = 8;
    private static final int MAC_BLOCK_LENGTH_BIT = 4;
    private static final String BANK_API_URL = "http://172.18.255.131:8081/";



    @Value("${afe.async.download.ip}")
    private String afeAsyncDownIP;

    @Value("${afe.async.download.port}")
    private int afeAsyncDownPort;

    private static final String P_PASSWD="123456";
    private static final int FILE_NAME_LENGTH_AFE = 16;
    private static final int RESERVE_SPACE = 64;
    private static final int ENCRYPT_LEN = 8;




    @Autowired
    private EncryptService encryptService;


    public void buildLink(HjFileInfoModel fileInfoModel) {

        boolean result = true;
        try {
            // TODO 先实现通讯，然后文件会在前置服务器上系统对应的ftp用户目录下，然后再去get

            //套接字建立时主机地址
            Socket clientSocket = new Socket(afeAsyncDownIP, afeAsyncDownPort);
            log.info("连接成功！");


            /**
             * 先拼xml报文，加密加签，再拼定长报文，然后建立连接，请求发给那个ip端口就行，
             * 等返回了再拆定长报文，验密验签，再拆xml报文然后去ftp
             */
            // TODO 按照ESB标准拼组业务报文XMLStr
            String xmlStr = "";



            // 生成16位随机数
            String randomVar = RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH_AFE);
            String pfxPath = this.getClass().getClassLoader().getResource("./rsa_2048/1.pfx").getPath();
            // 系统生成16位长度随机数明文randomVar，调用密服平台API，使用公钥证书加密随机数生成随机数密文，加密后转字符串类型encryptRnd
            String encryptRnd = new String(sxxh_LGBC.PubKey_Encrypt_LGBC(pfxPath, randomVar.getBytes()));
            // 调用密服平台API，使用明文随机数randomVar（转16进制为32位）对业务报文XMLStr进行加密生成业务报文密文，加密后转字符串类型encryptPkgData
            String encryptPkgData = new String(sxxh_LGBC
                    .PkgEncrypt_LGBC(AcctStringUtils.toHexString(randomVar).getBytes(), xmlStr.getBytes()));
            // 分别计算业务报文密文encryptPkgData长度encryptPkgDataLen和随机数密文encryptRnd长度encryptRndLen，不足8位左补0
            int encryptPkgDataLen = encryptPkgData.length();
            int encryptRndLen = encryptRnd.length();
            if(encryptPkgDataLen< ENCRYPT_LEN || encryptRndLen < ENCRYPT_LEN){
                // 虚列号不足8位的左补0
                encryptPkgData = String.format("%08d", encryptPkgData);
                encryptPkgDataLen = encryptPkgData.length();
                encryptRnd = String.format("%08d", encryptRnd);
                encryptRndLen = encryptRnd.length();

            }
            /**
             * 调用密服平台API
             * 使用私钥证书对“encryptPkgDataLen + encryptRndLen + encryptPkgData + encryptRnd”进行加签生成字符串signData
             */
            StringBuffer encryptData =new StringBuffer();
            encryptData.append(encryptPkgDataLen).append(encryptRndLen).append(encryptPkgData).append(encryptRnd);

            String pFileName = this.getClass().getClassLoader().getResource("./rsa_2048/1.pfx").getPath();
            // 方法名有pfx就是私钥，p12即私钥
            String signData = new String(sxxh_LGBC.Pfx_SignData_LGBC(pFileName, P_PASSWD, xmlStr.getBytes()));


            // 28位流水号：sysid+14位时间yyyymmddhhssmm+8位流水号
            String customerSeqNo = encryptService.getNx();

            StringBuffer totalInfo = new StringBuffer();
            totalInfo.append(customerSeqNo);
            // 64位预留
            totalInfo.append(String.format("%1$" + RESERVE_SPACE + "s", customerSeqNo));
            totalInfo.append(encryptPkgDataLen);
            totalInfo.append(encryptRndLen);
            totalInfo.append(encryptPkgData);
            totalInfo.append(encryptRnd);
            totalInfo.append(signData);

            // 计算“SYSID+28位流水号+64位预留+encryptPkgDataLen+encryptRndLen+ encryptPkgData +encryptRnd+signData”总长度LENGTH（不足8位左补0）
            int  length = totalInfo.length();

            // 拼组请求报文“LENGTH+SYSID+28位流水号+64位预留+encryptPkgDataLen+encryptRndLen+ encryptPkgData +encryptRnd+signData”
            XStream xstream = new XStream();
//            xstream.alias("LENGTH", length);
//            xstream.alias("SYSID", Head.class);
//            xstream.alias("encryptPkgDataLen", Body.class);
//            xstream.alias("encryptRndLen", Body.class);
//            xstream.alias("encryptPkgData", Body.class);
//            xstream.alias("encryptRnd", Body.class);
//            xstream.alias("signData", Body.class);




            //关闭套接字，只能发送一个报文
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {

        }

    }






    /**
     * 获取互金文件
     *
     * @param fileInfoModel
     */
    public void obtainFile(HjFileInfoModel fileInfoModel) {

        BodyVo body = new BodyVo();
        Map<String, Object> filePath = new HashMap<>();
        filePath.put("FilePath", fileInfoModel.getFilePath());

        Map<String, Object> fileTransCode = new HashMap<>();
        filePath.put("FileTranCode", fileInfoModel.getFileTransCode());

        Map<String, Object> fileDate = new HashMap<>();
        filePath.put("Date", fileInfoModel.getFileDate());

        body.setParamMap(filePath);
		body.setParamMap(fileTransCode);
		body.setParamMap(fileDate);

        // TODO 这个地方是直接按照文档中的系统文件下载吗？具体是哪个方法呢？
        // 具体见通讯方案7.4.1
    }

}
