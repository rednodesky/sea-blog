package com.sea.constant.enums;

/**
 * Created by Administrator on 2018/7/6.
 */
public enum  BannerType {
    CAROUSEL(1,"轮播"),TOPIC(2,"副标");

    private Integer code;

    private String text;

    BannerType(Integer code,String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode(){
        return code;
    }

    public String getText(){
        return text;
    }
}
