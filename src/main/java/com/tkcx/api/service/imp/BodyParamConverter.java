package com.tkcx.api.service.imp;


import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import com.tkcx.api.vo.base.BodyVo;

/**
 * 参数转换器
 *
 * @author linghujie
 *
 */
public class BodyParamConverter implements Converter {

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return type.equals(BodyVo.class);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        BodyVo vo = (BodyVo) source;
        new MapEntryConverter().marshal(vo.getParamMap(), writer, context);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = (HashMap<String, Object>) new MapEntryConverter().unmarshal(reader, context);
        BodyVo vo = new BodyVo();
        vo.setParamMap(paramMap);
        return vo;
    }

}