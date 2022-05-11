package com.tkcx.api.service.imp;

import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.utils.NumberUtils;
import com.tkcx.api.vo.base.BodyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sxxh.LGBC.SecException;
import sxxh.LGBC.sxxh_LGBC;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @project：
 * @author： zhaodan
 * @description： afe前端改造
 * 先拼xml报文，加密加签，再拼定长报文，然后建立连接，请求发给那个ip端口就行
 * @create： 2022/2/9 15:48
 */
@Slf4j
@Service
public class AfeEncryptService {


    private static final String XML_DECLARATION_BLOCK = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";

    @Value("${storage.tempDownload.path}")
    private String tempDownloadPath;


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

    /**
     * 建立链接，进行验签
     *
     * @param fileInfoModel
     * @return
     */
    public boolean verifySignData(HjFileInfoModel fileInfoModel) {

        boolean result = true;
        try {
            // TODO 先实现通讯，然后文件会在前置服务器上系统对应的ftp用户目录下，然后再去get

            //套接字建立时主机地址
            Socket clientSocket = new Socket(afeAsyncDownIP, afeAsyncDownPort);
            log.info("连接成功！");

            String requestInfo = assembleRequestInfo(fileInfoModel.getFilePath());

            PrintWriter os = new PrintWriter(clientSocket.getOutputStream());
            os.write(String.valueOf(requestInfo));
            os.flush();
            os.close();
            //关闭套接字，只能发送一个报文
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            return result;
        }
    }

    /**
     * 拼接请求报文信息
     *
     * @param remoteFilePath
     * @return
     */
    private String assembleRequestInfo(String remoteFilePath) {

        try {
            if (!remoteFilePath.startsWith("/")) {
                remoteFilePath = "/" + remoteFilePath;
            }
            // 按照ESB标准拼组业务报文XMLStr
            StringBuilder xmlStr = new StringBuilder();
            xmlStr.append(XML_DECLARATION_BLOCK).append(remoteFilePath).append(tempDownloadPath);

            // 生成16位随机数
            String randomVar = RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH_AFE);
            String encryptRnd = pubKeyEncryptRnd(randomVar);
            String encryptPkgData = pubKeyEncryptData(randomVar, xmlStr.toString());

            // 加签字符串
            String signData = priKeyEncryptSign(encryptRnd, encryptPkgData, String.valueOf(xmlStr));

            return String.valueOf(addSignRequest(encryptRnd, encryptPkgData, signData));
        }catch (Exception e) {
            // TODO 待补全异常信息及对应的返回值信息
            return null;
        }
    }


    /**
     * 调用密服平台API，使用公钥证书加密随机数生成随机数密文，加密后转字符串类型encryptRnd
     *
     * @return 随机数密钥
     * @throws SecException
     */
    private String pubKeyEncryptRnd(String randomVar) throws SecException {

        String pfxPath = this.getClass().getClassLoader().getResource("./rsa_2048/1.pfx").getPath();
        // 系统生成16位长度随机数明文randomVar，调用密服平台API，使用公钥证书加密随机数生成随机数密文，加密后转字符串类型encryptRnd
        return new String(sxxh_LGBC.PubKey_Encrypt_LGBC(pfxPath, randomVar.getBytes()));
    }

    /**
     * 调用密服平台API，对Str进行加密生成业务报文密文
     * @param randomVar 明文随机数randomVar（转16进制为32位）
     * @param xmlStr 业务报文XML
     * @return 加密后转字符串类型encryptPkgData
     * @throws SecException
     */
    private String pubKeyEncryptData(String randomVar, String xmlStr) throws SecException {

        return new String(sxxh_LGBC
                .PkgEncrypt_LGBC(AcctStringUtils.toHexString(randomVar).getBytes(), xmlStr.getBytes()));
    }

    /**
     * 使用私钥证书对“encryptPkgDataLen + encryptRndLen + encryptPkgData + encryptRnd”进行加签生成字符串signData
     *
     * @param encryptRnd 随机数密文
     * @param encryptPkgData 业务报文密文
     * @param xmlStr
     * @return
     */
    private String priKeyEncryptSign(String encryptRnd, String encryptPkgData, String xmlStr) throws SecException {

        int encryptPkgDataLen = encryptPkgData.length();
        int encryptRndLen = encryptRnd.length();
        if (encryptPkgDataLen < ENCRYPT_LEN || encryptRndLen < ENCRYPT_LEN) {
            // 序列号不足8位的左补0
            encryptPkgData = String.format("%08d", encryptPkgData);
            encryptPkgDataLen = encryptPkgData.length();
            encryptRnd = String.format("%08d", encryptRnd);
            encryptRndLen = encryptRnd.length();

        }
        StringBuilder encryptData = new StringBuilder();
        encryptData.append(encryptPkgDataLen).append(encryptRndLen).append(encryptPkgData).append(encryptRnd);

        String pFileName = this.getClass().getClassLoader().getResource("./rsa_2048/1.pfx").getPath();
        // 方法名有pfx就是私钥，p12即私钥
        return new String(sxxh_LGBC.Pfx_SignData_LGBC(pFileName, P_PASSWD, xmlStr.getBytes()));
    }

    /**
     * 报文加签
     *
     * @param encryptRnd
     * @param encryptPkgData
     * @param signData
     * @return
     */
    private StringBuilder addSignRequest(String encryptRnd, String encryptPkgData, String signData) {

        StringBuilder requestInfo = new StringBuilder();
        int encryptPkgDataLen = encryptPkgData.length();
        int encryptRndLen = encryptRnd.length();
        // 28位流水号：sysid+14位时间yyyymmddhhssmm+8位流水号
        String seqNo = encryptService.getNx();
        StringBuffer totalInfo = new StringBuffer();
        totalInfo.append(seqNo);
        // 64位预留
        totalInfo.append(String.format("%1$" + RESERVE_SPACE + "s", seqNo));
        totalInfo.append(encryptPkgDataLen).append(encryptRndLen);
        totalInfo.append(encryptPkgData).append(encryptRnd).append(signData);
        // 计算“SYSID+28位流水号+64位预留+encryptPkgDataLen+encryptRndLen+ encryptPkgData +encryptRnd+signData”总长度LENGTH（不足8位左补0）
        int length = totalInfo.length();
        // 总长度LENGTH（不足8位左补0）
        if(ENCRYPT_LEN != NumberUtils.getNumDigit(length)) {
            length = Integer.parseInt(String.format("%08d", length));
        }

        // 拼组请求报文“LENGTH+SYSID+28位流水号+64位预留+encryptPkgDataLen+encryptRndLen+ encryptPkgData +encryptRnd+signData”
        return requestInfo.append(length).append(totalInfo);
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
