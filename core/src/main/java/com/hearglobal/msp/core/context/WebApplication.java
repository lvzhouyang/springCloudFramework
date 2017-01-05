package com.hearglobal.msp.core.context;

import com.hearglobal.msp.core.mvc.AppErrorController;
import com.hearglobal.msp.core.mvc.AppExceptionHandlerController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by lvzhouyang on 16/12/14.
 */
@EnableSwagger2
public class WebApplication {

    //error page
    @Bean
    public ErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {

        return new AppErrorController(errorAttributes, serverProperties.getError());
    }

    //exception handler
    @Bean
    public AppExceptionHandlerController appExceptionHandlerController() {

        return new AppExceptionHandlerController();
    }

    //swagger2
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .select()
                .apis(requestHandler -> {
                    assert requestHandler != null;
                    String packageName = requestHandler.getHandlerMethod().getMethod()
                            .getDeclaringClass().getPackage().getName();
                    return packageName.startsWith("com.hearglobal.") && packageName.contains(".web");
                })
                .paths(PathSelectors.any())
                .build();
    }
}
