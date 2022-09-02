package com.tkcx.api.service.imp;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.tkcx.api.exception.ErrorCode;
import com.tkcx.api.utils.afe.RandomUtils;
import com.tkcx.api.utils.afe.TCPUtil;
import com.tkcx.api.utils.afe.XmlUtils;
import com.tkcx.api.vo.HjFileDataReqVo;
import com.tkcx.api.vo.ZhqdQueryReqVo;
import com.tkcx.api.vo.afe.*;
import com.tkcx.api.vo.base.ServiceVo;
import com.tkcx.api.vo.callback.ServiceRequestVo;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import com.tkcx.api.vo.ftpFile.FileUploadReqVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sxxh.LGBC.sxxh_LGBC;

import java.util.Map;

@Slf4j
@Service("afeCommonService")
public class AfeCommonService {

    @Value("${afe.socket.ip}")
    String socketIp;
    @Value("${afe.upload.port}")
    String socketPort;
    @Value("${afe.download.port}")
    String downLoadPort;

    /**
     *
     * 加密发送下载报文及解析响应报文
     * @param req
     * @return
     */
    public AfeDownFileBodyRspVo getDownLoanRspData(FileDownloadReqVo req, HjFileDataReqVo reqCn) {
        AfeDownFileBodyRspVo receive=new AfeDownFileBodyRspVo();
        try {
            //生成流水
            String cnsmrSeqNo = reqCn.getSysHeadVo().getCnsmrSeqNo();
            log.info("流水号:{}", cnsmrSeqNo);
            //获取16位随机数
            String randomNum = RandomUtils.getGUID();
            log.info("16位随机数:{}", randomNum);
            //获取16位随机数加密密文
            byte[] encryptRnd = getGUID(randomNum);
            String encryptRndStr = new String(encryptRnd);
            log.info("随机数密文字符串:{}", encryptRndStr);
            //获取业务加密数据
            byte[] encryptPkgData = getDownLoadBusinessData(req, randomNum);
            String encryptPkgDataStr = new String(encryptPkgData);
            log.info("业务密文字符串:{}", encryptPkgDataStr);
            //随机数密文长度
            String encryptRndLen = String.format("%08d", encryptRndStr.length());
            log.info("随机数密文长度:{}", encryptRndLen);
            //业务报文密文长度
            String encryptPkgDataLen = String.format("%08d", encryptPkgDataStr.length());
            log.info("业务报文密文长度:{}", encryptPkgDataLen);
            //获取签名数据
            byte[] signData = sign(encryptRndLen, encryptPkgDataLen, encryptRndStr, encryptPkgDataStr);
            //获取发送数据
            String message = getSendMessage(cnsmrSeqNo, encryptPkgDataLen, encryptRndLen, encryptPkgDataStr, encryptRndStr, signData);
            log.info("请求报文:{}", message);
            //发送请求
            Map<String, String> responData = TCPUtil.sendTCPRequest(socketIp, downLoadPort, message, "utf-8");
            String reqData = responData.get("reqData");
            log.info("响应报文:" + reqData);
            String tranData = afeUtilsService.getTranData(reqData);
            log.info("业务报文明文:" + tranData);
            receive = receive(tranData, AfeDownFileBodyRspVo.class);
            return receive;
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return receive;
    }

    @Value("${afe.lspublic.Key.path}")
    String lsPublicKeyPath;
    /**
     * 获取16位随机数，并加密
     *
     * @return
     */
    public byte[] getGUID(String randomNum) throws Exception {
        //随机数密文
        byte[] encryptRnd = null;
        try {
            encryptRnd = sxxh_LGBC.PubKey_Encrypt_LGBC(lsPublicKeyPath, randomNum.getBytes());
        } catch (Exception e) {
            log.info("随机数加密异常!");
            e.printStackTrace();
            throw e;
        }
        log.info("随机数密文:{}" + encryptRnd);
        return encryptRnd;
    }

    /**
     * 获取下载业务数据
     *
     * @param req
     * @return
     */
    public byte[] getDownLoadBusinessData(FileDownloadReqVo req, String randomNum) throws Exception {

        //创建业务报文
        AfeBusinessBodyReqVo afeBusinessBodyReqVo = new AfeBusinessBodyReqVo();

        afeBusinessBodyReqVo.setFilePath(req.getFilePath());
        afeBusinessBodyReqVo.setFileTranCode(req.getDownloadTranCode());
        afeBusinessBodyReqVo.setDate(req.getFileDate());

        AfeBusinessServiceReqVo afeBusinessServiceReqVo = new AfeBusinessServiceReqVo();
        afeBusinessServiceReqVo.setAfeBusinessBodyReqVo(afeBusinessBodyReqVo);
        String xmlStr = XmlUtils.objToXML(afeBusinessServiceReqVo).trim();
        xmlStr=xmlStr.replaceFirst("standalone=\"yes\"","");
        log.info("业务报文明文:{}", xmlStr);
        //业务报文密文
        byte[] encryptPkgData = null;
        try {
            encryptPkgData = sxxh_LGBC.PkgEncrypt_LGBC(strTo16(randomNum).getBytes(), xmlStr.getBytes());
        } catch (Exception e) {
            log.info("业务报文加密异常!");
            e.printStackTrace();
            throw e;
        }
        log.info("业务报文密文>>" + encryptPkgData);
        return encryptPkgData;
    }

    @Value("${afe.private.Key.path}")
    String privateKeyPath;
    @Value("${afe.passwd}")
    String passwd;
    /**
     * 获取签名数据
     *
     * @return
     */
    public byte[] sign(String encryptRndLen, String encryptPkgDataLen, String encryptRndStr, String encryptPkgDataStr) throws Exception {
        log.info("业务报文密文长度>>" + encryptPkgDataLen);
        //签 业务报文密文长度+随机数密文长度+业务报文密文+随机数密文
        String signParms = encryptPkgDataLen + encryptRndLen + encryptPkgDataStr + encryptRndStr;
        log.info("签>>" + signParms);
        byte[] signData = null;
        try {
            signData = sxxh_LGBC.Pfx_SignData_LGBC(privateKeyPath, passwd, signParms.getBytes());
        } catch (Exception e) {
            log.info("签名异常!");
            e.printStackTrace();
            throw e;
        }
        return signData;
    }

    /**
     * 获取发送数据
     *
     * @return
     */
    @Value("${afe.sec.node.id}")
    String secNodeId;
    public String getSendMessage(String cnsmrSeqNo, String encryptPkgDataLen, String encryptRndLen, String encryptPkgDataStr, String encryptRndStr, byte[] signData) {
        //系统节点号+28位流水号+预留位+业务报文密文长度+随机数密文长度+业务报文密文+随机数密文+签
        String reqParam = secNodeId + cnsmrSeqNo + getReserve() + encryptPkgDataLen + encryptRndLen + encryptPkgDataStr + encryptRndStr + new String(signData);
        log.info(">>" + reqParam);
        //报文长度
        String messageLen = String.format("%08d", reqParam.length());
        log.info("报文长度>>" + messageLen);
        //请求报文
        String message = messageLen + reqParam;
        return message;
    }
    /**
     * 获取64位预留位
     *
     * @return
     */
    public String getReserve() {
        return String.format("%064d", 0);
    }




    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    private String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    @Autowired
    private EncryptService encryptService;
    private static final int FILE_NAME_LENGTH_AFE = 16;
    private static final int RESERVE_SPACE = 64;
    private static final int ENCRYPT_LEN = 8;
    private static final String P_PASSWD="123456";
    @Value("${bank.service.sysId}")
    private String systemId;


    public String encrypt(ServiceRequestVo request,  String message) {
        try {
            //生成流水
            String cnsmrSeqNo = request.getSysHeadVo().getCnsmrSeqNo();
            log.info("流水号:{}", cnsmrSeqNo);
            //获取16位随机数
            String randomNum = RandomUtils.getGUID();
            log.info("16位随机数:{}", randomNum);
            //获取16位随机数加密密文
            byte[] encryptRnd = getGUID(randomNum);
            String encryptRndStr = new String(encryptRnd);
            log.info("随机数密文字符串:{}", encryptRndStr);
            //获取业务加密数据
            byte[] encryptPkgData = getRspBusinessData(message, randomNum);
            String encryptPkgDataStr = new String(encryptPkgData);
            log.info("业务密文字符串:{}", encryptPkgDataStr);
            //随机数密文长度
            String encryptRndLen = String.format("%08d", encryptRndStr.length());
            log.info("随机数密文长度:{}", encryptRndLen);
            //业务报文密文长度
            String encryptPkgDataLen = String.format("%08d", encryptPkgDataStr.length());
            log.info("业务报文密文长度:{}", encryptPkgDataLen);
            //获取签名数据
            byte[] signData = sign(encryptRndLen, encryptPkgDataLen, encryptRndStr, encryptPkgDataStr);
            //获取发送数据
            message = getSendMessage(cnsmrSeqNo, encryptPkgDataLen, encryptRndLen, encryptPkgDataStr, encryptRndStr, signData);
            log.info("请求报文:{}", message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }


    /**
     * 测试加密请求报文
     * @param
     * @param
     * @return
     */
    public String encryptXML(String message) {
        try {
            //生成流水
            String cnsmrSeqNo = encryptService.getNx();
            log.info("流水号:{}", cnsmrSeqNo);
            //获取16位随机数
            String randomNum = RandomUtils.getGUID();
            log.info("16位随机数:{}", randomNum);
            //获取16位随机数加密密文
            byte[] encryptRnd = getGUID(randomNum);
            String encryptRndStr = new String(encryptRnd);
            log.info("随机数密文字符串:{}", encryptRndStr);
            //获取业务加密数据
            byte[] encryptPkgData = getRspBusinessData(message, randomNum);
            String encryptPkgDataStr = new String(encryptPkgData);
            log.info("业务密文字符串:{}", encryptPkgDataStr);
            //随机数密文长度
            String encryptRndLen = String.format("%08d", encryptRndStr.length());
            log.info("随机数密文长度:{}", encryptRndLen);
            //业务报文密文长度
            String encryptPkgDataLen = String.format("%08d", encryptPkgDataStr.length());
            log.info("业务报文密文长度:{}", encryptPkgDataLen);
            //获取签名数据
            byte[] signData = sign(encryptRndLen, encryptPkgDataLen, encryptRndStr, encryptPkgDataStr);
            //获取发送数据
            message = getSendMessage(cnsmrSeqNo, encryptPkgDataLen, encryptRndLen, encryptPkgDataStr, encryptRndStr, signData);
            log.info("请求报文:{}", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }
    @Autowired
    private AfeUtilsServiceImpl afeUtilsService;
    /**
     *
     * 加密发送上传报文及解析响应报文
     * @param req
     * @return
     */
    public AfeUploadBodyRspVo getUpLoadRspData(FileUploadReqVo req, ZhqdQueryReqVo reqCn,String fileExt) {
        AfeUploadBodyRspVo receive= new AfeUploadBodyRspVo();
        try {
            //生成流水
            String cnsmrSeqNo = reqCn.getSysHeadVo().getCnsmrSeqNo();
            log.info("流水号:{}", cnsmrSeqNo);
            //获取16位随机数
            String randomNum = RandomUtils.getGUID();
            log.info("16位随机数:{}", randomNum);
            //获取16位随机数加密密文
            byte[] encryptRnd = getGUID(randomNum);
            String encryptRndStr = new String(encryptRnd);
            log.info("随机数密文字符串:{}", encryptRndStr);
            //获取业务加密数据
            byte[] encryptPkgData = getUpLoadBusinessData(req, randomNum,fileExt);
            String encryptPkgDataStr = new String(encryptPkgData);
            log.info("业务密文字符串:{}", encryptPkgDataStr);
            //随机数密文长度
            String encryptRndLen = String.format("%08d", encryptRndStr.length());
            log.info("随机数密文长度:{}", encryptRndLen);
            //业务报文密文长度
            String encryptPkgDataLen = String.format("%08d", encryptPkgDataStr.length());
            log.info("业务报文密文长度:{}", encryptPkgDataLen);
            //获取签名数据
            byte[] signData = sign(encryptRndLen, encryptPkgDataLen, encryptRndStr, encryptPkgDataStr);
            //获取发送数据
            String message = getSendMessage(cnsmrSeqNo, encryptPkgDataLen, encryptRndLen, encryptPkgDataStr, encryptRndStr, signData);
            log.info("请求报文:{}", message);
            //发送请求
            Map<String, String> responData = TCPUtil.sendTCPRequest(socketIp, socketPort, message, "utf-8");
            String rspData = responData.get("respData");
            log.info("响应报文:" + rspData);
            String tranData = afeUtilsService.getTranData(rspData);
            log.info("业务报文明文:" + tranData);
            receive = receive(tranData, AfeUploadBodyRspVo.class);
            return receive;
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return receive;
    }

    public static void main(String[] args) {
        String str = "<?xml version='1.0' encoding='UTF-8'?><Service><Body><FileTransCode>100033</FileTransCode><FileAllPath>/100033/qnafe/113501/f5927c75-5ebc-4ac2-901b-dd8ffa5ad7f1.pdf</FileAllPath></Body></Service>";
        AfeUploadBodyRspVo receive;
        try {
            receive = receive(str, AfeUploadBodyRspVo.class);
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
        receive=new AfeUploadBodyRspVo();
        return ;
    }

    public static  <T extends ServiceRequestVo> T receive(String message, Class<T> reqClazz) throws ApplicationException {
        log.info("接收请求-->" + message);
        ServiceVo serviceVo = convertMessageToVo(message);
        try {
            T req = reqClazz.newInstance();
            req.withMap(serviceVo.getBody().getParamMap());
            req.setSysHeadVo(serviceVo.getSysHead());
            req.setAppHeadVo(serviceVo.getAppHead());
            return req;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(ErrorCode.CANNOT_NEW_INSTANCE, "无法实例化对象", e);
        }
    }
    private static ServiceVo convertMessageToVo(String message) throws ApplicationException {

        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        xStream.processAnnotations(ServiceVo.class);
        xStream.registerConverter(new BodyParamConverter());
        xStream.registerConverter(new MapEntryConverter());
        xStream.ignoreUnknownElements();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{ServiceVo.class});

        Object xml = xStream.fromXML(message);
        return (ServiceVo) xml;
    }

    /** SFTP 文服上传方式 1-集中式 2-分布式，不送默认走集中式*/
    @Value("${afe.sftp.FileServerType}")
    private String fileServerType;
    /** SFTP 分布式文服节点*/
    @Value("${afe.sftp.DisNode}")
    private String disNode;
    /**
     * 获取上传业务数据
     *
     * @param req
     * @return
     */
    public byte[] getUpLoadBusinessData(FileUploadReqVo req, String randomNum,String fileExt) throws Exception {

        //创建业务报文
        AfeBusinessBodyReqVo afeBusinessBodyReqVo = new AfeBusinessBodyReqVo();

        afeBusinessBodyReqVo.setLocalFilePath(req.getRemoteFullPath());
        afeBusinessBodyReqVo.setFileName(req.getFileName()+"."+fileExt);
        afeBusinessBodyReqVo.setFileServerType(fileServerType);
        afeBusinessBodyReqVo.setDisNode(disNode);

        AfeBusinessServiceReqVo afeBusinessServiceReqVo = new AfeBusinessServiceReqVo();
        afeBusinessServiceReqVo.setAfeBusinessBodyReqVo(afeBusinessBodyReqVo);
        String xmlStr = XmlUtils.objToXML(afeBusinessServiceReqVo).trim();
        xmlStr=xmlStr.replaceFirst("standalone=\"yes\"","");
        log.info("业务报文明文:{}", xmlStr);
        //业务报文密文
        byte[] encryptPkgData = null;
        try {
            encryptPkgData = sxxh_LGBC.PkgEncrypt_LGBC(strTo16(randomNum).getBytes(), xmlStr.getBytes());
        } catch (Exception e) {
            log.info("业务报文加密异常!");
            e.printStackTrace();
            throw e;
        }
        log.info("业务报文密文>>" + encryptPkgData);
        return encryptPkgData;
    }
    public byte[] getRspBusinessData(String message, String randomNum) throws Exception {
        log.info("业务响应报文明文:{}", message);
        //业务报文密文
        byte[] encryptPkgData = null;
        try {
            encryptPkgData = sxxh_LGBC.PkgEncrypt_LGBC(strTo16(randomNum).getBytes(), message.getBytes());
        } catch (Exception e) {
            log.info("业务报文加密异常!");
            e.printStackTrace();
            throw e;
        }
        log.info("业务报文密文>>" + encryptPkgData);
        return encryptPkgData;
    }

}
