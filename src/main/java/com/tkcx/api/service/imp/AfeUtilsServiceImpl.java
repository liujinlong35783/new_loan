package com.tkcx.api.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sxxh.LGBC.sxxh_LGBC;

@Slf4j
@Service("AfeUtilsServiceImpl")
public class AfeUtilsServiceImpl {


    @Value("${afe.passwd}")
    private String passwd;

    @Value("${afe.private.Key.path}")
    private String privateKeyPath;

    @Value("${afe.lspublic.Key.path}")
    private String lsPublicKeyPath;



    /**
     * 解密报文
     * @param msg
     * @return
     */
    public String afeDecryptMsg(String msg) {
        log.info("响应报文:"+msg);
        String tranMsg = null;
        try {
            tranMsg = getTranData(msg);
        } catch (Exception e) {
            log.info(e.getMessage());
            return "";
        }
        log.info("业务报文明文:"+tranMsg);
        return tranMsg;
    }

    public   String getTranData(String respData) throws Exception {
        //报文总长度
        int bodyLegth = Integer.parseInt(respData.substring(0, 8));
        log.info("报文体总长度" + bodyLegth);
        //系统id
        String systemId = respData.substring(8, 14);
        log.info(systemId);

        //流水号
        String seqNo = respData.substring(14, 42);
        log.info(seqNo);

        //业务报文长度
        int tranLegth = Integer.parseInt(respData.substring(106, 114));
        log.info(""+tranLegth);
        //随机数密文长度
        int radomLegth = Integer.parseInt(respData.substring(114, 122));
        log.info(""+radomLegth);
        //业务报文密文
        int tranEndIndex = 122 + tranLegth;
        String tranCiphertext = respData.substring(122, tranEndIndex);
        log.info(tranCiphertext);
        //随机数密文
        int radomEndIndex = tranEndIndex + radomLegth;
        String radomCiphertext = respData.substring(tranEndIndex, radomEndIndex);
        log.info(radomCiphertext);
        //签名密文
        String signCiphertext = respData.substring(radomEndIndex);
        log.info(signCiphertext);

        String signPlainText=respData.substring(106,radomEndIndex);
        boolean attestationResult = decodeSign(signPlainText,signCiphertext);
        if(!attestationResult){
            log.info("验签失败!");
            throw  new Exception("验签失败!");
        }
        String radomStr =  decodeRadom( radomCiphertext);
        String tranStr = decodeTran( radomStr, tranCiphertext);


        return tranStr;

    }

    /**
     * 解密返回报文中的随机数密文
     * @param radomCiphertext
     * @return
     * @throws Exception
     */
    private String decodeRadom(String radomCiphertext) throws Exception {
        byte[] radomBytes=null;
        try {
            radomBytes = sxxh_LGBC. PrivateKey_Decrypt_LGBC(privateKeyPath, passwd, radomCiphertext.getBytes());
        } catch (Exception e) {
            throw e;
        }

        String radomStr=new String(radomBytes,"UTF-8");

        return radomStr;
    }

    /**
     * 解密返回报文中的业务报文
     * @param radomStr
     * @return
     */
    private  String decodeTran(String radomStr,String tranCiphertext)throws Exception{

        byte[] radomBytes = strTo16(radomStr).getBytes();
        byte[] tranBytes=tranCiphertext.getBytes();
        byte[]  tranPlaintextBytes =null;
        try {
            tranPlaintextBytes = sxxh_LGBC.PkgDecrypt_LGBC(radomBytes, tranBytes);
        } catch (Exception e) {
            throw e;
        }
        String tranPlaintext =new  String (tranPlaintextBytes,"UTF-8");
        return tranPlaintext;

    }

    /**
     * 验签
     * @return
     * @throws Exception
     */
    private boolean decodeSign(String signPlainText ,String signCiphertext) throws Exception {

        log.info("验签明文:"+signPlainText);
        log.info("验签密文:"+signCiphertext);
        byte[] newSignBytes = signPlainText.getBytes();
        byte[] signCiphertextBytes = signCiphertext.getBytes();
        int result = 0;
        try {
            log.info("参数一："+lsPublicKeyPath);
            log.info("参数二："+newSignBytes.toString());
            log.info("参数三："+signCiphertextBytes.toString());
            System.out.println(signCiphertext);
            result = sxxh_LGBC.Pfx_VerifyData_LGBC(lsPublicKeyPath,newSignBytes,signCiphertextBytes);
        } catch (Exception e) {
            throw e;
        }

        if (result != 0) {
            return false;
        }
        return true;
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

}
