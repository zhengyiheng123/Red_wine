package com.xyd.red_wine.register;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 15:56
 * @description:
 */

public class RegisterMessage {
   public String  phone;
    public  String password;
    public RegisterMessage(String phone,String password){
        this.password=password;
        this.phone=phone;

    }
}
