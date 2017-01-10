package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import com.hearglobal.msp.bean.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvzhouyang on 16/12/16.
 */
public class ArrayTest {

    User user = new User();
    User user1 = new User();
    List<User> users = Lists.newArrayList();

    @Before
    //初始化避免重复代码
    public void init() {
        user.setId(1);
        user.setUserName("dsfsadf");
        user.setPassword("12345");
        ObjectUtil.setDefault(user);
        Assembler.assemble(user, user1);
        user1.setPassword("654");
        users.add(user);
        users.add(user1);
    }

    @Test
    //测试反射取属性集合(包含私有域属性)
    public void testGetDeclaredFieldValueList() throws NoSuchFieldException, IllegalAccessException {
        List<Object> DeclarednameList1 = ArrayUtil.getDeclaredFieldValueList(users, "userName");
        System.out.println(ObjectUtil.toString(DeclarednameList1));
    }

    @Test
    //测试反射取属性集合（非私有域属性）
    public void testGetFieldValueList() throws NoSuchFieldException, IllegalAccessException {
        List<Object> pswList = ArrayUtil.getFieldValueList(users, "password");
        System.out.println(ObjectUtil.toString(pswList));
    }

    @Test
    //测试取list中的所需属性值，并转换为sql所需要的格式
    public void testGetFieldSqlString() throws NoSuchFieldException, IllegalAccessException {
        String DeclarednameList = ArrayUtil.getFieldSqlString(users, "password");
        System.out.println(ObjectUtil.toString(DeclarednameList));
    }

    @Test
    //测试拼接给定字符串到集合中的每个元素并组成一个string
    public void testlist2UniqStr() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");
        String Uniq = ArrayUtil.list2UniqStr(list, "+");
        System.out.println(Uniq);
    }

    @Test
    //测试 String[] 转List<String>
    public void testGetUniqList() {
        String[] arr = {"a", "b", "c"};
        List<String> list_arr = ArrayUtil.getUniqList(arr);
        System.out.println(list_arr);
    }
}
