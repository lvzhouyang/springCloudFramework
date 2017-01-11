package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import com.hearglobal.msp.bean.User;
import com.hearglobal.msp.bean.UserVo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvzhouyang on 16/12/16.
 *
 * The tests for AssemberUtils
 *
 */
public class AssemberTest {

    User user = new User();
    User user1 = new User();
    UserVo userVo = new UserVo();

    @Before
    public void init() {
        user.setPassword("12345");
        user.setId(1);
        user.setUserName("dsfsadf");
        //设置默认值(有属性值的增添，没有属性值的设为默认)
        ObjectUtil.setDefault(user);
        Assembler.assemble(user, user1);
    }

    @Test
    //测试转换工具 对象转对象
    public void testAssember() {
        Assembler.assemble(user, userVo);
        System.out.println(ObjectUtil.toString(userVo));
    }

    @Test
    //测试转换工具 list转list
    public void testAssembleList2List() {
        List<User> users = Lists.newArrayList();
        users.add(user);
        users.add(user1);
        List<UserVo> list = new ArrayList();
        Assembler.assembleList2List(users, list, UserVo.class);
        System.out.println(ObjectUtil.toString(list));
    }

    @Test
    //测试转换工具 list转新的list
    public void testAssemblelist2Newlist() {
        List<User> users = Lists.newArrayList();
        users.add(user);
        users.add(user1);
        List newList = Assembler.assembleList2NewList(users, User.class, "username");
        System.out.println(ObjectUtil.toString(newList));
    }
}
