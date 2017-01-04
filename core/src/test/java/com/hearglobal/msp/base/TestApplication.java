package com.hearglobal.msp.base;

import com.hearglobal.msp.core.context.BaseConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liubin on 2016/3/28.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@Import({BaseConfiguration.class})
public class TestApplication {


}