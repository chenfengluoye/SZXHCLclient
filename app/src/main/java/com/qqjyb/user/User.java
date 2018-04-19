package com.qqjyb.user;

/**
 * Created by chengkaiju on 2018/4/6.
 */

public class User {
    public static User myself=new User();
    public static boolean isloging=false;
    public String name="未设置";
    public String bltclass="未设置";
    public String role;
    public String admin;
    public String nowschool="未设置";

    public User(){
        name="游客";
        admin="tourist";
        bltclass="未设置";
        nowschool="未设置";
    }
    public User(String name,String admin,String role){
        this.name=name;
        this.admin=admin;
        this.role=role;
    }

    //学生的构造函数
    public User(String name,String admin,String role,String bltclass){
        this.name=name;
        this.admin=admin;
        this.role=role;
        this.bltclass=bltclass;
    }
}
