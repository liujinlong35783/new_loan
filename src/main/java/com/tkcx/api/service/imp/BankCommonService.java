package com.tkcx.api.service.imp;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.tkcx.api.exception.BankException;
import com.tkcx.api.exception.ErrorCode;
import com.tkcx.api.vo.ResponseVo;
import com.tkcx.api.vo.base.*;
import com.tkcx.api.vo.callback.ServiceRequestVo;
import com.tkcx.api.vo.callback.ServiceResponseVo;
import common.core.exception.ApplicationException;
import common.core.util.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 银行通用服务
 * 
 * @author linghujie
 *
 */
@Slf4j
@Service
public class BankCommonService {

	private static final String XML_DECLARATION_BLOCK = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
	private static final int TOTAL_LENGTH_BIT = 8;
	private static final int MAC_BLOCK_LENGTH_BIT = 4;
	private static final String BANK_API_URL = "http://172.18.255.131:8081/";

	@Value("${bank.esb.url}")
	private String esbURL;

	@Value("${bank.service.sysId}")
	private String sysId;
	@Value("${bank.service.sysId1}")
	private String sysId1;
	@Value("${bank.service.txnChnlTp}")
	private String txnChnlTp;
	@Value("${bank.service.txntlrId}")
	private String txntlrId;
	@Value("${bank.service.orgId}")
	private String orgId;


	@Autowired
	private EncryptService encryptService;

//	@Autowired
//	private AfeDecryptService afeDecryptService;

	/**
	   * 接收请求
	 */
	public <T extends ServiceRequestVo> T receive(String message, Class<T> reqClazz) throws ApplicationException {
		log.info("接收请求-->" + message);
		ServiceVo serviceVo = this.convertMessageToVo(message);
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


	/**
	 * 接收请求
	 */
	public <T extends ServiceRequestVo> T receiveZH(String message, Class<T> reqClazz) throws ApplicationException {
		log.info("接收请求-->" + message);
		ServiceVo serviceVo = this.convertMessageToVoZH(message);
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
	/**
	   * 响应请求
	 */
	public String response(ServiceRequestVo request, ServiceResponseVo response) throws ApplicationException {

		SysHeadVo sysHead = new SysHeadVo();
		sysHead.setSvcCd(request.getSysHeadVo().getSvcCd());
		sysHead.setSvcScn(request.getSysHeadVo().getSvcScn());
		sysHead.setCnsmrSysId(request.getSysHeadVo().getCnsmrSysId());
		sysHead.setCnsmrSeqNo(request.getSysHeadVo().getCnsmrSeqNo());
		sysHead.setOrigCnsmrSeqNo(request.getSysHeadVo().getOrigCnsmrSeqNo());
		sysHead.setOrigCnsmrSvrId(request.getSysHeadVo().getOrigCnsmrSvrId());
		sysHead.setSvcSplrSysId(sysId1);
		
		String customerSeqNo = encryptService.getNx1();
		sysHead.setSvcSplrSeqNo(customerSeqNo);
		sysHead.setTxnSt(response.getTxnSt());
		sysHead.setRetCd(response.getRetCd());
		sysHead.setRetMsg(response.getRetMsg());

		AppHeadVo appHead = new AppHeadVo();
		appHead.setTxnTlrId(request.getAppHeadVo().getTxnTlrId());
		appHead.setOrgId(request.getAppHeadVo().getOrgId());
		appHead.setTlrPwsd(request.getAppHeadVo().getTlrPwsd());
		appHead.setTlrLvl(request.getAppHeadVo().getTlrLvl());

		BodyVo body = new BodyVo();
		body.setParamMap(response.toMap());

		ServiceVo service = new ServiceVo();
		service.setSysHead(sysHead);
		service.setAppHead(appHead);
		service.setBody(body);
		/**
		 *
		 */
		String responseMessage = this.convertVoToMessage(service);
		log.info("响应请求-->" + responseMessage);
		return responseMessage;
	}

	/**
	 * 响应请求
	 */
	public String responseZH(ServiceRequestVo request, ServiceResponseVo response) throws ApplicationException {

		SysHeadVo sysHead = new SysHeadVo();
		sysHead.setSvcCd(request.getSysHeadVo().getSvcCd());
		sysHead.setSvcScn(request.getSysHeadVo().getSvcScn());
		sysHead.setCnsmrSysId(request.getSysHeadVo().getCnsmrSysId());
		sysHead.setCnsmrSeqNo(request.getSysHeadVo().getCnsmrSeqNo());
		sysHead.setOrigCnsmrSeqNo(request.getSysHeadVo().getOrigCnsmrSeqNo());
		sysHead.setOrigCnsmrSvrId(request.getSysHeadVo().getOrigCnsmrSvrId());
		sysHead.setSvcSplrSysId(sysId);

		String customerSeqNo = encryptService.getNx();
		sysHead.setSvcSplrSeqNo(customerSeqNo);
		sysHead.setTxnSt(response.getTxnSt());
		sysHead.setRetCd(response.getRetCd());
		sysHead.setRetMsg(response.getRetMsg());

		AppHeadVo appHead = new AppHeadVo();
		appHead.setTxnTlrId(request.getAppHeadVo().getTxnTlrId());
		appHead.setOrgId(request.getAppHeadVo().getOrgId());
		appHead.setTlrPwsd(request.getAppHeadVo().getTlrPwsd());
		appHead.setTlrLvl(request.getAppHeadVo().getTlrLvl());

		BodyVo body = new BodyVo();
		body.setParamMap(response.toMap());

		ServiceVo service = new ServiceVo();
		service.setSysHead(sysHead);
		service.setAppHead(appHead);
		service.setBody(body);
		/**
		 *
		 */
		String responseMessage = this.convertVoToMessageZH(service);
		log.info("响应请求-->" + responseMessage);
		return responseMessage;
	}

	/**
	   * 发送请求
	 */
	public <T extends ApiResponseVo> T request(String serviceCode, String serviceScreen, ApiRequestVo reqVo,
											   Class<T> clazz) throws ApplicationException, BankException {
		try {
			ServiceVo request = generateRequest(serviceCode, serviceScreen, reqVo);
			ServiceVo response = this.requestInternal(request);
			T rsp = clazz.newInstance();
			rsp.setRequest(request);
			rsp.setResponse(response);
			rsp.withMap(response.getBody().getParamMap());
			rsp.setInternalSN(request.getSysHead().getCnsmrSeqNo());
			rsp.setExternalSN(response.getSysHead().getSvcSplrSeqNo());
			rsp.setTxnDt(response.getSysHead().getTxnDt());
			rsp.setTxnTm(response.getSysHead().getTxnTm());
			rsp.setAcgDt(response.getSysHead().getAcgDt());
		 
			return rsp;
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ApplicationException(ErrorCode.CANNOT_NEW_INSTANCE, "无法实例化对象", e);
		}
	}
	
	private ServiceVo generateRequest(String serviceCode, String serviceScreen, ApiRequestVo reqVo) {
		
		SysHeadVo sysHead = new SysHeadVo();
		sysHead.setSvcCd(serviceCode);
		sysHead.setSvcScn(serviceScreen);
		sysHead.setCnsmrSysId(sysId);
		String customerSeqNo = encryptService.getNx();
		sysHead.setCnsmrSeqNo(customerSeqNo);
		sysHead.setTxnChnlTp(txnChnlTp);
		sysHead.setOrigCnsmrSeqNo(customerSeqNo);
//		if (!StringUtils.equals(acgDt, "1970-01-01")) {
//			sysHead.setAcgDt(acgDt); 
//		}
		reqVo.setSysHead(sysHead);

		AppHeadVo appHead = new AppHeadVo();
		appHead.setTxnTlrId(txntlrId);
		appHead.setOrgId(orgId);
//		appHead.setTlrPwsd(tlrPwsd);
//		appHead.setTlrLvl(tlrLvl);
		reqVo.setAppHead(appHead);

		BodyVo body = new BodyVo();
		body.setParamMap(reqVo.toMap());

		ServiceVo request = new ServiceVo();
		request.setSysHead(sysHead);
		request.setAppHead(appHead);
		request.setBody(body);
		
		return request;
	}

	private ServiceVo requestInternal(ServiceVo request) throws ApplicationException, BankException {

		String requestMessage = convertVoToMessage(request);
		
		Integer customizedTimeout = 8000;

		String responseMessage = customizedPost(esbURL, requestMessage, customizedTimeout);

		ServiceVo response = convertMessageToVo(responseMessage);
/*		if (!StringUtils.equals("000000", response.getSysHead().getRetCd())) {
//		if (!StringUtils.equals("S", response.getSysHead().getTxnSt())) {
			throw new BankException(response.getSysHead().getRetCd(), response.getSysHead().getRetMsg(),
					response.getSysHead().getCnsmrSeqNo());
		}*/
		
		return response;
	}

	private String convertVoToMessage(ServiceVo service) throws ApplicationException {

		XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
		xStream.processAnnotations(ServiceVo.class);
		xStream.registerConverter(new BodyParamConverter());
		xStream.registerConverter(new MapEntryConverter());

		String xmlBlock = XML_DECLARATION_BLOCK + xStream.toXML(service);

		// 生成MAC
		String macBlock = encryptService.generateMac(xmlBlock);

		String lengthBlock = String.format("%08d", macBlock.length() + xmlBlock.length());

		return lengthBlock + macBlock + xmlBlock;
	}

	private String convertVoToMessageZH(ServiceVo service) throws ApplicationException {

		XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
		xStream.processAnnotations(ServiceVo.class);
		xStream.registerConverter(new BodyParamConverter());
		xStream.registerConverter(new MapEntryConverter());

		String xmlBlock = XML_DECLARATION_BLOCK + xStream.toXML(service);

//		// 生成MAC
//		String macBlock = encryptService.generateMac(xmlBlock);
//
//		String lengthBlock = String.format("%08d", macBlock.length() + xmlBlock.length());

//		return lengthBlock + macBlock + xmlBlock;
		return  xmlBlock;
	}

	private ServiceVo convertMessageToVo(String message) throws ApplicationException {

		XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
		xStream.processAnnotations(ServiceVo.class);
		xStream.registerConverter(new BodyParamConverter());
		xStream.registerConverter(new MapEntryConverter());
		xStream.ignoreUnknownElements();
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(new Class[] { ServiceVo.class });
		//MAC地址校验
		int macLenght = Integer.valueOf(message.substring(TOTAL_LENGTH_BIT, TOTAL_LENGTH_BIT + MAC_BLOCK_LENGTH_BIT));
		String expectMacBlock = message.substring(TOTAL_LENGTH_BIT,
				TOTAL_LENGTH_BIT + MAC_BLOCK_LENGTH_BIT + macLenght);
		String xmlBlock = message.substring(TOTAL_LENGTH_BIT + MAC_BLOCK_LENGTH_BIT + macLenght);
		log.info("convertMessageToVo-xmlBlock: "+xmlBlock);
		encryptService.verifyMac(xmlBlock, expectMacBlock);
		log.info("验证MAC通过");
		Object xml = xStream.fromXML(xmlBlock);
		return (ServiceVo)xml;
	}

	private ServiceVo convertMessageToVoZH(String message) throws ApplicationException {

		XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
		xStream.processAnnotations(ServiceVo.class);
		xStream.registerConverter(new BodyParamConverter());
		xStream.registerConverter(new MapEntryConverter());
		xStream.ignoreUnknownElements();
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(new Class[] { ServiceVo.class });

//		int macLenght = Integer.valueOf(message.substring(TOTAL_LENGTH_BIT, TOTAL_LENGTH_BIT + MAC_BLOCK_LENGTH_BIT));
//		String expectMacBlock = message.substring(TOTAL_LENGTH_BIT,
//				TOTAL_LENGTH_BIT + MAC_BLOCK_LENGTH_BIT + macLenght);
//		String xmlBlock = message.substring(TOTAL_LENGTH_BIT + MAC_BLOCK_LENGTH_BIT + macLenght);
//
//		encryptService.verifyMac(xmlBlock, expectMacBlock);
		Object xml = xStream.fromXML(message);
		return (ServiceVo)xml;
	}
	
	public <T extends ApiResponseVo> ResponseVo<T> requestHttpPost(String apiName, ServiceRequestVo request)
			throws ApplicationException {
		
		try {
			String requestMessage = JSON.toJSONString(request);
			String url = BANK_API_URL + apiName;
			ResponseVo<T> response = HttpUtils.post(url, requestMessage, ResponseVo.class);
			if (response.getCode() != response.CODE_SUCCESS) {
				throw new ApplicationException("请求失败:" + response.getMessage());
			}
			return response;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationException(ErrorCode.CANNOT_NEW_INSTANCE, "无法实例化对象", e);
		}
	}
	
	public static String customizedPost(String url, String content, Integer timeout) throws ApplicationException {
        StringBuilder sb = new StringBuilder(url).append(" POST >> ").append(content);
        try {
        	HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE);
            httpPost.setEntity(new StringEntity(content, StandardCharsets.UTF_8));
            
        	RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            
            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpResponse httpResponse = client.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            sb.append(statusCode).append(" ").append(result);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return result;
            } else {
                throw new ApplicationException("请求失败:" + statusCode);
            }
        } catch (IOException e) {
            sb.append(e.getClass());
            log.error(e.getMessage(), e);
            throw new ApplicationException("请求失败", e);
        } finally {
            log.info(sb.toString());
        }
    }

}
