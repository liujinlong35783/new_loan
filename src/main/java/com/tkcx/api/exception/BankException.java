package com.tkcx.api.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求银行错误
 * 
 * @author zhouzhichao
 *
 */
@Getter
@Setter
@ToString
public class BankException extends Exception {
    private static final long serialVersionUID = 6160273896328379868L;
    private String code;
    private String message;
    private String serviceSn;

    public BankException(String code, String message, String serviceSn) {
        this.code = code;
        this.message = message;
        this.serviceSn = serviceSn;
    }
}
