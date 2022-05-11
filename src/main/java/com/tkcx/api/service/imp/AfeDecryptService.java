package com.tkcx.api.service.imp;

import sxxh.LGBC.sxxh_LGBC;

/**
 * @project：
 * @author： zhaodan
 * @description： 对返回的AFE报文进行拆分，验密验签，再拆xml报文然后去ftp
 * @create： 2022/2/21 9:10
 */
public class AfeDecryptService {

    private static final String P_PASSWD="123456";
    private static final int FILE_NAME_LENGTH_AFE = 16;
    private static final int PFX_VERIFY_RESULT = 0;


    /**
     * TODO 验签解密
     *
     * @param signRes afe响应报文
     */
    public void verifySignOffInfo(String signRes){

        try {
            String pFileName = this.getClass().getClassLoader().getResource("./rsa_2048/1.pfx").getPath();
            // 按照约定长度拆分报文“LENGTH+SYSID+28位流水号+64位预留+encryptPkgDataLen+encryptRndLen+ encryptPkgData +encryptRnd+signData”
            // 报文总长度
            String length = signRes.substring(0, 8);
            // 28位流水号
            String seqNo = signRes.substring(9, 28);
            // 64位预留位
            String reserved = signRes.substring(29, 64);
            // encryptPkgDataLen 8位
            String encryptPkgDataLen = signRes.substring(65, 65+8);
            // encryptRndLen  8位
            String encryptRndLen = signRes.substring(73, 73+8);
            // encryptPkgData

            // encryptRnd
            String encryptRnd = signRes.substring(0,0);

            String signData = signRes.substring(0, 1);

            // 随机数密文 encryptRnd

            /**
             * TODO 调用密服平台API，使用公钥证书进行验签
             */
            // 产生签名的数据首地址 待验签的数据 加签是这四个加起来的串生成signdata，所以验签第二个入参就是这四个，第三个参数就是signdata
            byte[] pPlainMsg = new byte[0];
            // 待验证的签名值（十六进制） 签名数据 signData
            byte[] pSignBuf = new byte[0];
            int i = sxxh_LGBC.Pfx_VerifyData_LGBC(pFileName, pPlainMsg, pSignBuf);
            // 私钥验签不成功
            if(i != PFX_VERIFY_RESULT){
                return;
            }
            // 调用密服平台API，使用私钥证书解密随机数，生成随机数明文
            byte[] randomVar = sxxh_LGBC.PrivateKey_Decrypt_LGBC(pFileName, P_PASSWD, encryptRnd.getBytes());

            // 调用密服平台API，使用随机数明文对业务报文密文进行解密生成业务报文明文（ESB标准XML报文）
            sxxh_LGBC.PkgDecrypt_LGBC(randomVar, signData.getBytes());

        }catch(Exception e) {

        }
    }


}
