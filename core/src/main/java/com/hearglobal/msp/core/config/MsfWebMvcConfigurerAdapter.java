package com.hearglobal.msp.core.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 自定义HttpConverter
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.02.08
 */
@Configuration
public class MsfWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    /**
     * The constant UTF8.
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Extend message converters.
     * 修改默认的HttpConverter
     *
     * @param converters the converters
     * @since 2017.02.08
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(getStringConverter());
        converters.add(getJson());
    }

    /**
     * Gets json.
     * 定义json转换,修改默认编码
     *
     * @return the json
     */
    private HttpMessageConverter getJson() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = Lists.newArrayList();
        mediaTypes.add(new MediaType("application", "json", UTF8));
        mediaTypes.add(new MediaType("application", "*+json", UTF8));
        mediaTypes.add(new MediaType(MediaType.TEXT_PLAIN, UTF8));
        messageConverter.setSupportedMediaTypes(mediaTypes);
        return messageConverter;
    }

    /**
     * Gets string converter.
     * 自定义字符串转换 修改默认编码
     *
     * @return the string converter
     */
    private HttpMessageConverter getStringConverter() {
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
        List<MediaType> mediaTypes = Lists.newArrayList();
        mediaTypes.add(new MediaType(MediaType.TEXT_PLAIN, UTF8));
        messageConverter.setSupportedMediaTypes(mediaTypes);
        return messageConverter;
    }
}