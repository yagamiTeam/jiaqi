package com.ant.jiaqi.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;

public abstract class BaseController {
    protected static HttpHeaders headers = new HttpHeaders();
    static {
    	headers.add("Content-Type", "application/json; charset=utf-8");
    }

    protected ResponseEntity<String> returnSuccessMsg(){
        Result<String> result = new Result<>();
        result.setMark(this.getMark("0"));
        result.setMessage("SUCCESS");
        result.setData(null);
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.OK);
    }

    protected <T> ResponseEntity<String> returnSuccessMsg(T t){
        Result<T> result = new Result<T>();
        result.setMark(this.getMark("0"));
        result.setMessage("SUCCESS");
        result.setData(t);
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.OK);
    }

    protected <T> ResponseEntity<String> returnSuccessMsg(T t, Long totalCount){
        Result<T> result = new Result<T>();
        result.setMark(this.getMark("0"));
        result.setMessage("SUCCESS");
        result.setData(t);
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.OK);
    }

    protected <T> ResponseEntity<String> returnFailMsg(String msg){
        return this.returnFailMsg(msg, null);
    }

    protected <T> ResponseEntity<String> returnFailMsg(String msg, T t){
        Result<T> result = new Result<T>();
        result.setMark(this.getMark("1"));
        result.setMessage(msg);
        result.setData(t);
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.OK);
    }

    private String getMark(String defaultValue){
        return defaultValue;
    }
}
