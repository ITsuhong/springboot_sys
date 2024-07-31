package com.sysxx.interception;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysxx.utils.JwtHelper;
import com.sysxx.utils.Result;
import com.sysxx.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginProtectInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        String token = request.getHeader("token");
//        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)) {
//
//            Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(result);
//            response.setStatus(401);
//            response.getWriter().print(json);
//            //拦截
//            return false;
//        } else {
//            //放行
//            return true;
//        }

    }
}
