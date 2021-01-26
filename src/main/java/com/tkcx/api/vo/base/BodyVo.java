package com.tkcx.api.vo.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 服务体
 * 
 * @author linghujie
 *
 */
@Getter
@Setter
@ToString
@XStreamAlias("Body")
public class BodyVo {
	
	private Map<String, Object> paramMap;
}
