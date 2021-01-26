package com.tkcx.api.service.imp;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.tkcx.api.vo.base.ApiRequestVo;

import java.util.*;


/**
 * Map转XML
 * 
 * @author linghujie
 *
 */
public class MapEntryConverter implements Converter {

    /**
     * 判断是否可以转
     */
    public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
        return AbstractMap.class.isAssignableFrom(clazz);
    }

    /**
     * 转换方法
     */
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        AbstractMap<?, ?> map = (AbstractMap<?, ?>) value;
        for (Object obj : map.entrySet()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) obj;
            writer.startNode(entry.getKey().toString());
            Object val = entry.getValue();
            if (null != val) {
                if (val instanceof AbstractMap) {
                    marshal(val, writer, context);
                } else if (val instanceof AbstractCollection) {
                	writer.addAttribute("type", "array");
                	marshalCollection(val, writer, context);
                } else {
                    writer.setValue(val.toString());
                }
            }
            writer.endNode();
        }
        
    }
    
    private void marshalCollection(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    	@SuppressWarnings("unchecked")
		AbstractCollection<ApiRequestVo> collection = (AbstractCollection<ApiRequestVo>) value;
        for (ApiRequestVo obj : collection) {
        	writer.startNode("Struct");
        	marshal(obj.toMap(), writer, context);
            writer.endNode();
        }
    }

    /**
     * 从XML反解出对象
     */
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map<String, Object> map = new HashMap<String, Object>();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String key = reader.getNodeName();
            if ("array".equals(reader.getAttribute("type"))) {
            	map.put(key, unmarshalCollection(reader, context));
            } else {
            	if (reader.hasMoreChildren()) {
                	map.put(key, unmarshal(reader, context));
                } else {
                    String value = reader.getValue();
                    map.put(key, value);
                }
            }
            reader.moveUp();
        }
        return map;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Object unmarshalCollection(HierarchicalStreamReader reader, UnmarshallingContext context) {
    	List itemList = new ArrayList();
    	while (reader.hasMoreChildren()) {
    		reader.moveDown();
    		Map<String, Object> map = (Map)unmarshal(reader, context);
    		itemList.add(map);
    		reader.moveUp();
    	}
    	return itemList;
    }
}
