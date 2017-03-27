package com.hearglobal.msp.cache.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hearglobal.msp.cache.annotation.MspCache;
import com.hearglobal.msp.cache.service.IHedis;
import com.hearglobal.msp.util.ObjectUtil;
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
import java.util.stream.Stream;

/**
 * 缓存切面 实现将方法返回值自动添加到缓存/从缓存取
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.03.27
 */
@Aspect
@Component
public class CacheAspect {
    private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);

    @Resource
    private IHedis appCacheService;


    @Around("execution(* *.*(..)) && @annotation(com.hearglobal.msp.cache.annotation.MspCache)")
    public Object checkPrivilege(ProceedingJoinPoint j) throws Throwable {
        // 获得传入参数
        Object[] args = j.getArgs();
        // 方法对象
        Method m = ReflectUtil.getMethod(j);
        // 方法的cache注解
        MspCache cache = AnnotationUtils.findAnnotation(m, MspCache.class);
        int keepSecond = cache.keepSecond();
        boolean reCache = cache.reCache();
        String cacheKey = cache.cacheKey();

        // 拼接参数
        List<Object> argsList = Lists.newArrayList(args);
        if (keepSecond < 0) {
            return j.proceed(args);
        }
        if (!StringUtils.hasLength(cacheKey)) {
            cacheKey = m.getDeclaringClass().toString() + "#" + m.getName() + this.getCachekey(argsList.toArray());
            log.debug("cache - original key:{}", cacheKey);
        }
        Object rtn = appCacheService.get(cacheKey);
        if (!ObjectUtil.isNullObj(rtn)
                && !reCache) {
            return rtn;
        }
        rtn = j.proceed(args);
        if (rtn == null ||
                (rtn instanceof Collection && ((Collection<?>) rtn).size() == 0) ||
                (rtn instanceof Map && ((Map<?, ?>) rtn).keySet().size() == 0) ||
                (rtn.getClass().isArray() && ((Object[]) rtn).length == 0)) {
        } else {
            log.debug("cache into medis");
            appCacheService.set(cacheKey, keepSecond, rtn);
        }
        return rtn;
    }

    private String getCachekey(Object... o) {
        StringBuilder sb = new StringBuilder();
        Stream.of(o)
                .forEach(object -> sb.append(object2KeyStr(object)).append("&*&"));
        return sb.toString();
    }

    private String object2KeyStr(Object object) {
        if (object == null) {
            return "NULL";
        } else if (object instanceof Collection ||
                object instanceof Map ||
                object.getClass().isEnum() ||
                object.getClass().isArray()) {
            return ((JSON) JSON.toJSON(object)).toJSONString();
        } else {
            return object.toString();
        }
    }
}
