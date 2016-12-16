package com.hearglobal.msp.util;

import com.hearglobal.msp.bean.User;
import com.hearglobal.msp.bean.UserVo;
import org.junit.Test;

/**
 * Created by lvzhouyang on 16/12/16.
 */
public class AssemberTest {
    @Test
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
}
