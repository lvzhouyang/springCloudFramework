package com.hearglobal.msp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.hearglobal.msp.bean.User;
import com.hearglobal.msp.bean.UserVo;
import org.junit.Test;

import java.util.Map;

/**
 * Created by ccg on 2017/1/8.
 */
public class JsonTest {
    
    @Test
//    测试 object 转 json
    public void obj2json(){
        User user = new User();
        user.setUserName("ccg");
        user.setPassword("123");
        user.setUpdateTime(DateUtil.today());
        String userjson = JsonUtils.object2Json(user);
        System.out.println(userjson);
    }
    
    @Test
//    测试 object 转 map
    public void  obj2map(){
        User user = new User();
        user.setUserName("ccg");
        user.setPassword("123");
        user.setUpdateTime(DateUtil.today());
        Map<String, Object> hsmap = JsonUtils.object2Map(user);
        System.out.println(hsmap);
    }
    
    @Test
//    测试 object 转 JsonNode
    public void object2Node(){
        User user = new User();
        user.setUserName("ccg");
        user.setPassword("123");
        user.setUpdateTime(DateUtil.today());
        JsonNode jsonNode = JsonUtils.object2Node(user);
        System.out.println(jsonNode);
    }
    
    @Test
//    测试 JsonNode 转 object
    public void node2Object(){
        User user = new User();
        user.setUserName("ccg");
        user.setPassword("123");
        user.setUpdateTime(DateUtil.today());
        JsonNode jsonNode = JsonUtils.object2Node(user);
        UserVo userVo = JsonUtils.node2Object(jsonNode,UserVo.class);
        System.out.println(ObjectUtil.toString(userVo));
    }
    
    @Test
//    测试 json 转 object  其他类似
    public void json2ObjectTest(){
        String userstr ="{"+'\"'+"userName"+'\"'
                           +':'+'\"'+"zcx"+'\"'+","
                           +'\"'+"password" +'\"'
                           +':'+'\"'+"456"+'\"'+'}';
        System.out.println(JsonUtils.json2Object(userstr,User.class));
    }
}
