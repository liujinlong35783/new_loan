
package com.tkcx.api.utils.afe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TCPUtil {

    /**
     * 发送TCP请求
     */
    public static Map<String, String> sendTCPRequest(String IP, String port, String reqData, String reqCharset)throws Exception{
        Map<String, String> respMap = new HashMap<String, String>();
        OutputStream out = null;
        InputStream in = null;
        String localPort = null;
        String respData = null;
        Socket socket = new Socket();
        try {
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSoTimeout(30000);
            socket.setSoLinger(true, 5);
            socket.setSendBufferSize(1024);
            socket.setReceiveBufferSize(1024);
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress(IP, Integer.parseInt(port)), 30000);
            localPort = String.valueOf(socket.getLocalPort());
            //发送TCP请求
            out = socket.getOutputStream();
            out.write(reqData.getBytes(reqCharset));
            //接收TCP响应
            in = socket.getInputStream();
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int len = -1;
            while((len=in.read(buffer)) != -1){
                bytesOut.write(buffer, 0, len);
            }

            byte[]   resp= bytesOut.toByteArray();
            respData = new String (resp,"UTF-8");

        } catch (Exception e) {
            log.error("与[" + IP + ":" + port + "]通信遇到异常,堆栈信息如下:",e);
            e.printStackTrace();
            throw e;
        } finally {
            if (null!=socket && socket.isConnected() && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("关闭客户机Socket时发生异常,堆栈信息如下:",e);
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        respMap.put("localPort", localPort);
        respMap.put("reqData", reqData);
        respMap.put("respData", respData);
        return respMap;
    }



    protected static  Map readStream(String  respData) throws Exception {
        //报文总长度
        int    bodyLegth=Integer.parseInt(respData.substring(0,8));
        System.out.println("报文体总长度"+bodyLegth);
        //系统id
        String systemId  = respData.substring(8,14);
        System.out.println(systemId);

        //流水号
        String seqNo  = respData.substring(14,42);
        System.out.println(seqNo);

        //业务报文长度
        int  tranLegth  = Integer.parseInt(respData.substring(106,114));
        System.out.println(tranLegth);
        //随机数密文长度
        int  radomLegth  = Integer.parseInt(respData.substring(114,122));
        System.out.println(radomLegth);
        //业务报文密文
        int tranEndIndex=122+tranLegth;
        String  tranBody=respData.substring(122,tranEndIndex);
        System.out.println(tranBody);
        //随机数密文
        int radomEndIndex=tranEndIndex+radomLegth;
        String  radomBody=respData.substring(tranEndIndex,radomEndIndex);
        System.out.println(radomBody);
        //签名密文
        String sign=respData.substring(radomEndIndex);
        System.out.println(sign);

        String signMw = respData.substring(106,114)+respData.substring(114,122)+tranBody+radomBody;
        return null;
    }


    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }




    public static void main(String[] args) throws Exception{
        String respData="000019381137001135012021060911192110945150000000000000000000000000000000000000000000000000000000000000000000000800000005126965E0BB9397329A809E4E49777D9CA7585C48074EE0F370EB6B162AA57FF96B3389B5D46C38398136A3D3767A7955B6C111995789348DD837D2B8F7BCFBBFFFEC371FD17D1121C8DDA8C9AA446DE65173B96ADE35C495D26EDE8805F5BB2668A44A17403EA1B1F72B508FDA3C126E424C087E4EAF2DFD7BAF766DF346BDD9C7639D1268340BD92D6AFA300902E4180C053BBE7A977572C77675B213D091C091146CBD9451DF2799534940EA04EECA99884B03F2F45EAB2474AF432892A7C34095090C690FFA83876106504F44F0D05373B96ADE35C495D26EDE8805F5BB2668A44A17403EA1B1F72B508FDA3C126E424C087E4EAF2DFD7BAF766DF346BDD9C7639D1268340BD92D6AFA300902E4180CF30FE21E4A843C4010A91034D8D02C90EEB2460A2714C9134F0786E5C8CABDEF15796B487A5E5092842D22D8A2CD912F17D05972141D14846049A7B2A6AC59F145B248D33FC48FB92CCA45A61F9EFCCFD177C1363A3BD00E6CE3695072EF1956BC9C92BB3059A6A72BC2A791E1A06B05EE05BB31A337A8C50CF31E2A89441D2F789793B562D11C46FC7BF42937ABDE539A4EB26925FDB34016ACCEC816657455593C2A97CB2337D4F6466B07C6D7C895C13B3139365E06C355D61D9DD5D7E6C744DF6CDB63CC9649FB677FC606E1A861DF2C0E5A1E341B72A7870041173135F3406C572FAB9BA9CB8BA4230EE3A4178B72D5FC3BABBF56B2DC78A8250320E9F3898D5A8FEEF1E55914C7B0B5CF3D0A8821DD5F6B01F8CBD106A75DAF225A2CA5CDEDAAFC5C375F6B8A92D7012B803EFD9431047D68C679576FC54FE5C5B5C2980F6182515B22BC7598216501FD9D6A46E6601E34850655938CC1C15247D518DB6F819BB3D85C7630E127BF3F23106587F91EF049F397DC0DB6CA66F14E762D4367BE89687F4236EE3AD0E2BE9B513931AD422541E4F7F3FC34BDFFFC4509ADB7C77D833E09E86002439F1917D5F81246D5ACCE006623D8CF46E43530304DE49E4BC8BCEC6E67F4B9230ED9981AADD6593BF3F50D45B390E731A762761C8B46657545B79A21DC739C92AC220628409C258A5523EFABC92FA3BC867347275E9CB8A83BF09D5D5870E26B7000F02BA1BA50154CAE4543A296F8377286CE7F5893D110B38BD95C237ECEDC82307830681B525046924706807FB3830D9F621F4D9C29109963A6A587EE51D98095180AD8E9DCD8191EFF4E13B1A5AD6FD7494023772991811C0CFCAB2F745C339A9BBC252820FA6807D19563EC18FB67D32A28AF7969";
         readStream(respData);
    }

}
 