package com.sysxx.aspect;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysxx.annotations.LoginValidator;
import com.sysxx.utils.JwtHelper;
import com.sysxx.utils.Result;
import com.sysxx.utils.ResultCodeEnum;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;

@Component
@Aspect
public class LoginAspect {
    @Autowired
    private JwtHelper jwtHelper;

    @Pointcut(value = "@annotation(com.sysxx.annotations.LoginValidator) || @within(com.sysxx.annotations.LoginValidator)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LoginValidator loginValidator = signature.getMethod().getAnnotation(LoginValidator.class);
        if (loginValidator != null && !loginValidator.validated()) {
            return joinPoint.proceed(joinPoint.getArgs());
        }
        // 正常校验 获取request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || requestAttributes.getResponse() == null) {
            // 如果不是从前段过来的，没有request，则直接放行
            return joinPoint.proceed(joinPoint.getArgs());
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)) {

            Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            response.setStatus(401);
            response.setContentType("Application/json;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            //拦截
            return null;
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

}
