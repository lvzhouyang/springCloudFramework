package com.hearglobal.msp.util;

import com.hearglobal.msp.bean.User;
import com.hearglobal.msp.bean.UserVo;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccg on 2017/1/8.
 * <p>
 * The tests for ObjectUtils
 */
public class ObjectTest {

    User user = new User();
    User user1 = new User();

    @Before
    public void init() {
        ObjectUtil.setDefault(user);
        ObjectUtil.setDefault(user1);
        user.setUserName("cg");
        user1.setUserName("cg");
    }

    @Test
    //测试设置默认值
    public void setDefaultTest() {
        System.out.println(ObjectUtil.toString(user));
    }

    @Test
    //判断是否有不为空的属性值
    public void hasnullTest() {
        System.out.println(ObjectUtil.hasNonNullProperty(user));
    }

    @Test
    //测试两个对象的属性值是否相同 最好用于基本类型数据
    public void isSameTest() {
        System.out.println(ObjectUtil.isSame(user, user1));
    }

    @Test
    //测试将一个bean 转换成一个map
    public void transBean2MapTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        UserVo uservo = new UserVo();
        uservo.setUserName("ccc");
        uservo.setPassword("333");
        ObjectUtil.setDefault(uservo);
        map = ObjectUtil.transBean2Map(uservo);
        System.out.println(map);
    }

    @Test
    //测试对象属性为null
    public void isNullObj() {
        Map o = new HashMap();
        System.out.println(ObjectUtil.toString(ObjectUtil.isNullObj(o)));
    }
}
