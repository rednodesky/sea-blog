package com.sea.modal;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/22.
 */
public class HashMapResult extends HashMap<Object, Object> {


    public HashMapResult() {
        this.put("success", Boolean.valueOf(true));
    }

    public static HashMapResult success(){
        HashMapResult hashMapResult  = new HashMapResult();
        hashMapResult.put("success",true);
        return hashMapResult;
    }

    public static HashMapResult success(String message) {
        HashMapResult hashMapResult= new HashMapResult();
        hashMapResult.put("success",true);
        hashMapResult.put("message", message);
        return hashMapResult;
    }

    public  static HashMapResult failure(String message) {
        HashMapResult hashMapResult= new HashMapResult();
        hashMapResult.put("success",false);
        hashMapResult.put("message", message);
        return hashMapResult;
    }

    public static HashMapResult failur(String message){
        HashMapResult hashMapResult  = new HashMapResult();
        hashMapResult.put("success",false);
        hashMapResult.put("message",message);
        return hashMapResult;
    }
}
