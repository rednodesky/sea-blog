package com.sea.modal;

import lombok.Setter;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/22.
 */
@Setter
public class HashMapResult extends HashMap {

    private String message;
    private Boolean success;

    public static HashMapResult success(){
        HashMapResult hashMapResult  = new HashMapResult();
        hashMapResult.setSuccess(true);
        return hashMapResult;
    }

    public static HashMapResult failur(String message){
        HashMapResult hashMapResult  = new HashMapResult();
        hashMapResult.setSuccess(false);
        hashMapResult.setMessage(message);
        return hashMapResult;
    }
}
