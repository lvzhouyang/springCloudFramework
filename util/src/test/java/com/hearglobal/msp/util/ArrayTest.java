package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import com.hearglobal.msp.bean.User;
import org.junit.Test;

import java.util.List;

/**
 * Created by lvzhouyang on 16/12/16.
 */
public class ArrayTest {

    @Test
    public void testGetDeclaredFieldValueList() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        user.setPassword("12345");
        user.setId(1);
        user.setUserName("dsfsadf");
        ObjectUtil.setDefault(user);

        User user1 = new User();
        Assembler.assemble(user,user1);

        List<User> users = Lists.newArrayList();
        users.add(user);
        users.add(user1);

        List<Object> nameList = ArrayUtil.getDeclaredFieldValueList(users,"userName");
        System.out.println(ObjectUtil.toString(nameList));
    }
}
