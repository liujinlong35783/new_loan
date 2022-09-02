package com.tkcx.api.service.imp;

import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;
import com.tkcx.api.exception.ErrorCode;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 加密服务
 * 
 * @author linghujie
 *
 */
@Slf4j
@Service("encryptService")
public class EncryptService {

	@Value("${bank.service.sysId}")
	private String systemId;

	@Value("${bank.security.secNodeID}")
	private String secNodeID;
	
	@Value("${bank.security.destSecNodeID}")
	private String destSecNodeID;
	
	private static final int MAC_BLOCK_LENGTH_BIT = 4;

	/**
	 * 初始化加密机客户端
	 */
	@PostConstruct
	private void init() throws ApplicationException {
		try {
			SecAPI.nodeInit(secNodeID);
		} catch (Throwable e) {
			log.error("初始化加密机客户端失败,错误码：" + e.getMessage());
		}
	}

	/**
	 * 退出加密机客户端
	 */
	@PreDestroy
	private void destroy() throws ApplicationException {
		try {
			SecAPI.nodeFinal(secNodeID);
		} catch (Throwable e) {
			log.error("退出加密机客户端失败,错误码：" + e.getMessage());
		}
	}

	/**
	 * 生成MAC
	 */
	public String generateMac(String content) throws ApplicationException {
		try {
			byte[] contentData = content.getBytes(StandardCharsets.UTF_8);
			byte[] macData = SecAPI.mac(secNodeID, destSecNodeID, contentData);
			String mac = new String(macData);
			String length = String.format("%04d", mac.length()  +  secNodeID.length());
			return length + mac + secNodeID;
		} catch (SecException e) {
			log.error("生成mac失败,错误码：" + e.getMessage());
			throw new ApplicationException(ErrorCode.FAIL_GENERATE_MAC, "生成mac失败", e);
		}
	}

	/**
	 * 验证MAC
	 */
	public void verifyMac(String content, String macBlock) throws ApplicationException {
		try {
			byte[] contentData = content.getBytes(StandardCharsets.UTF_8);
			String mac = macBlock.substring(MAC_BLOCK_LENGTH_BIT, macBlock.length() - destSecNodeID.length());
			SecAPI.macVerify(secNodeID, destSecNodeID, contentData, mac.getBytes());
		} catch (SecException e) {
			log.error("验证mac失败,错误码：" + e.getMessage());
			throw new ApplicationException(ErrorCode.FAIL_VERIFY_MAC, "验证mac失败", e);
		}
	}

	/**
	 * 获取下一个序列号,格式如下
	 *
	 * <pre>
	 * 系统ID（6位）+交易日期（8位：YYYYMMDD）+交易时间（6位：HHMMSS）+流水序号（8位）。
	 * 系统ID: 6位数字，是标识全行业务系统的唯一编号
	 * 交易日期：当前流水生成系统的交易日期，按YYYYMMDD格式编排。
	 * 交易时间：当前流水生成系统的交易时间，按HHMMSS格式编排
	 * 流水序号：由当前流水生成系统生成8位的数字序号，从1开始计数，
	 * 依次递增，达到最大流水号后重新从1开始。如果流水号不足8位，用左补0方式拼足8位。
	 * 系统流水号：标识一笔交易的流水号，在相邻的两个系统之间传递，由前端系统产生，
	 * 反映每次调用的唯一标识，后端系统需保存前端系统的“系统流水号”。
	 *
	 * </pre>
	 */
	public String getNx() {
		StringBuilder stringBuilder = new StringBuilder();
		Date time = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		// 拿到系统时间
		String formatTime = dateFormat.format(time);
		// 拿到数据库序列号
		Random rd = new SecureRandom();
		int sn = rd.nextInt(100000000);
		// 超过8位数后从头开始
		int suffix = sn % 100000000;
		// 虚列号不足8位的前边用0补足
		String serialNumber = String.format("%08d", suffix);
		return stringBuilder.append(systemId).append(formatTime).append(serialNumber).toString();
	}

	public static void main(String[] args) {

		EncryptService encryptService = new EncryptService();
		System.out.println(encryptService.getNx());
		System.out.println(encryptService.getNx().length());
	}

}
