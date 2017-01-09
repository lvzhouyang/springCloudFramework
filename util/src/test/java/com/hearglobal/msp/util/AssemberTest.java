package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import com.hearglobal.msp.bean.User;
import com.hearglobal.msp.bean.UserVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvzhouyang on 16/12/16.
 */
public class AssemberTest {
    @Test
    //测试转换工具 对象转对象
    public void testAssember(){
        User user = new User();
        user.setPassword("12345");
        user.setId(1);
        user.setUserName("dsfsadf");
        ObjectUtil.setDefault(user);

        UserVo userVo = new UserVo();
        Assembler.assemble(user,userVo);
        System.out.println(ObjectUtil.toString(userVo));
    }
    
    @Test
    //测试转换工具 list转list
    public void testAssembleList2List(){
        User user = new User();
        user.setPassword("12345");
        user.setId(1);
        user.setUserName("dsfsadf");
        //设置默认值(有属性值的增添，没有属性值的设为默认)
        ObjectUtil.setDefault(user);
        System.out.println(ObjectUtil.toString(user));
        User user1 = new User();
        Assembler.assemble(user, user1);

        List<User> users = Lists.newArrayList();
        users.add(user);
        users.add(user1);

        List<UserVo> list = new ArrayList();
        Assembler.assembleList2List(users,list,UserVo.class);
        System.out.println(ObjectUtil.toString(list));
    }
    
    @Test
    public void testAssemblelist2Newlist(){
        User user = new User();
        user.setPassword("12345");
        user.setId(1);
        user.setUserName("dsfsadf");
        //设置默认值(有属性值的增添，没有属性值的设为默认)
        ObjectUtil.setDefault(user);
        User user1 = new User();
        Assembler.assemble(user, user1);
    
        List<User> users = Lists.newArrayList();
        users.add(user);
        users.add(user1);
        
       List newList =  Assembler.assembleList2NewList(users,User.class,"username");
        System.out.println(ObjectUtil.toString(newList));
    
    }
}
