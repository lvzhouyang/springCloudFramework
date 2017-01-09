package com.hearglobal.msp.util;

import com.hearglobal.msp.bean.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccg on 2017/1/8.
 */
public class ObjectTest {
    @Test
    //测试设置默认值
    public void setDefaultTest() {
        User user = new User();
        ObjectUtil.setDefault(user);
        System.out.println(ObjectUtil.toString(user));
    }
    
    @Test
    //测试有没有空值的属性
    public void hasnullTest() {
        User user = new User();
        ObjectUtil.setDefault(user);
        System.out.println(ObjectUtil.hasNonNullProperty(user));
    }
    
    @Test
    //测试两个对象的属性值是否相同 最好用于基本类型数据
    public void isSameTest() {
        User user = new User();
        User user1 = new User();
        user.setUserName("cg");
        user1.setUserName("cg");
        System.out.println(ObjectUtil.isSameObject(user.getUserName(), user1.getUserName()));
    }
    
    @Test
    //测试将一个bean 转换成一个map
    public void transBean2MapTest(){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = new User();
        user.setUserName("ccc");
        user.setPassword("333");
        ObjectUtil.setDefault(user);
        map = ObjectUtil.transBean2Map(user);
        System.out.println(map);
    }
}
