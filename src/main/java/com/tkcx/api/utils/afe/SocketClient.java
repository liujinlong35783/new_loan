package com.tkcx.api.utils.afe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.Map;

@Slf4j
@Service
public class SocketClient {


    /**
     * 发送消息
     *
     * @param sendMessage
     */
    public  static String send(String socketIp,Integer socketPort,String sendMessage) {
        InputStreamReader isr;
        BufferedReader br;
        OutputStreamWriter osw;
        BufferedWriter rw;
        Socket socket;
        String bussinessStr = null;
//        try {
//            socket = new Socket(socketIp, socketPort);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            log.info("send message:" + sendMessage);
//            byte[] content = sendMessage.getBytes("UTF-8");
//            baos.write(content);
//            try {
//                writeStream(socket.getOutputStream(), baos.toByteArray());
//                Map result = readStream(socket.getInputStream());
//                bussinessStr = getBusinessData(result);
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            } finally {
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        socket = null;
//                        log.error("客户端 finally 异常:" + e.getMessage());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
        return bussinessStr;
    }

    protected static void writeStream(OutputStream out, byte[] sndBuffer) throws IOException {
        out.write(sndBuffer);
        out.flush();
    }



    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }


    /**
     * 获取明文交易数据
     */
    private static String getBusinessData(Map map) {
        //1.验签
        //2.解密随机数，生成随机数明文
        //3.获取业务报文明文
        return null;
    }

}
