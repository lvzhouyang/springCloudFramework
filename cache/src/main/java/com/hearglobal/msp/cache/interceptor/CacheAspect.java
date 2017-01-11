package com.hearglobal.msp.cache.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hearglobal.msp.cache.annotation.HearCache;
import com.hearglobal.msp.cache.service.IHedis;
import com.hearglobal.msp.util.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 缓存切面
 *
 * @author lvzhouyang
 */
@Aspect
@Component
public class CacheAspect {
    private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);

    @Resource
    private IHedis appCacheService;


    @Around("execution(* *.*(..)) && @annotation(com.hearglobal.msp.cache.annotation.HearCache)")
    public Object checkPrivilege(ProceedingJoinPoint j) throws Throwable {
        // 获得传入参数
        Object[] args = j.getArgs();
        // 方法对象
        Method m = ReflectUtil.getMethod(j);
        // 方法的cache注解
        HearCache cache = AnnotationUtils.findAnnotation(m, HearCache.class);
        int keepSecond = cache.keepSecond();
        boolean reCache = cache.reCache();
        String cacheKey = cache.cacheKey();

        // 拼接参数
        List<Object> argsList = Lists.newArrayList();
        for (Object arg : args){
            argsList.add(arg);
        }
        if (keepSecond < 0) {
            return j.proceed(args);
        }
        if (!StringUtils.hasLength(cacheKey)) {
            cacheKey = m.getDeclaringClass().toString() + "#" + m.getName() + this.getCachekey(argsList.toArray());
            log.debug("cache - original key:{}", cacheKey);
        }
        Object rtn = appCacheService.get(cacheKey);
        if (rtn == null || reCache) {
            rtn = j.proceed(args);
            if (rtn == null ||
                    (rtn instanceof Collection && ((Collection<?>) rtn).size() == 0) ||
                    (rtn instanceof Map && ((Map<?, ?>) rtn).keySet().size() == 0) ||
                    (rtn.getClass().isArray() && ((Object[]) rtn).length == 0)) {
            } else {
                log.debug("cache into medis");
                appCacheService.set(cacheKey, keepSecond, rtn);
            }
        }
        return rtn;
    }

    private String getCachekey(Object... o) {
        StringBuilder sb = new StringBuilder();
        for (Object object : o) {
            if (object == null) {
                sb.append("NULL");
            } else if (object instanceof Collection ||
                    object instanceof Map ||
                    object.getClass().isEnum() ||
                    object.getClass().isArray()) {
                sb.append(((JSON) JSON.toJSON(object)).toJSONString());
            } else {
                sb.append(object.toString());
            }
            sb.append("&*&");
        }
        return sb.toString();
    }
}
