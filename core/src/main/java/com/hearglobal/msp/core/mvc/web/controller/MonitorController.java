package com.hearglobal.msp.core.mvc.web.controller;

import com.hearglobal.msp.api.AjaxResult;
import com.hearglobal.msp.core.annotation.RestApiController;
import com.hearglobal.msp.util.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 统一监控类 用以监控服务可用性
 * 会与faclon整合 进行服务监控
 * 配置刷新
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@RestApiController
public class MonitorController {


    @GetMapping("/monitor/alive")
    public Map<String, Object> alive() {
        return AjaxResult.createSuccessMap();
    }

    @GetMapping("/config/refresh")
    public void configRefresh(HttpServletRequest request) throws Exception {
        HttpUtil.post("http://"+request.getHeader("host")  + "/refresh","",5000L);
    }
}
