/**
 * Project Name:ccopsms
 * File Name:JaxbUtil.java
 * Package Name:com.ccop.core.util
 * Date:2015年11月13日下午9:32:35
 * Copyright (c) 2015, LiHao All Rights Reserved.
 *
*/

package cn.bainuo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbUtil {

	private static final Logger logger = LoggerFactory.getLogger(JaxbUtil.class);
	private static JaxbUtil _instance = new JaxbUtil();
	private static JAXBContext context = null;
	static {
		logger.info("JaxbUtil初始化");
		try {
			context = JAXBContext.newInstance("com.ccop.common.core.msg");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	synchronized public static JaxbUtil getInstance() {
		return _instance;
	}

	public static void main(String[] args) {
		// BaseXmlResp baseResp=new BaseXmlResp();
		// baseResp.setStatuscode("000000");
		// baseResp.setStatusmsg("成功");
		// SmsSendVo vo=new SmsSendVo();
		// System.out.println(convertToXml(vo));
		// String resultBody="<?xml version=\"1.0\"
		// encoding=\"UTF-8\"?><response><msgid>02e3162d60e84bfab28a423fa63b0fb3</msgid><statuscode>000000</statuscode><statusmsg>成功</statusmsg></response>";
		// SmsSendRespVo smsSendRespVo
		// =converyToJavaBean(resultBody,SmsSendRespVo.class);
		// System.out.println(smsSendRespVo.getMsgid());
	}

	/**
	 * JavaBean转换成xml 默认编码UTF-8
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertToXml(Object obj) {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * 
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * xml转换成JavaBean
	 * 
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

}
