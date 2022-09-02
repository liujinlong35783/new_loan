package com.tkcx.api.utils.afe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;


@Slf4j
@Service
public class XmlUtils {


    /**
     * 通过JAXB生成XML字符串
     * @return
     */
    public static String objToXML(Object obj) {
        StringWriter sw = new StringWriter();
        String xml = null;
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
            xml = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    /**
     *通过JAXB将XML转化为Object
     * @param xml
     * @param clazz
     * @return
     */
    public static  <T> Object xmlToObj(String xml, Class<T> clazz) {
        Object obj = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            obj = clazz.newInstance();
            obj = um.unmarshal(sr);
        } catch (JAXBException e){
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}




