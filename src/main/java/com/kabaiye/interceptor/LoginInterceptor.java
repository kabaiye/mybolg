package com.kabaiye.interceptor;

import cn.hutool.core.util.StrUtil;
import com.kabaiye.entity.vo.R;
import com.kabaiye.service.TokenService;
import com.kabaiye.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("{} 进入Logging Interceptor", HttpUtil.getClientIp(request));
        String BToken = request.getHeader("Authorization");
        // 如果没有token
        if(StrUtil.isBlank(BToken)){
            HandleErrorResponse.handleErrorResponse(response,R.error("请先登陆！！"));
            return false;
        }
        String accessToken = tokenService.BTokenToToken(BToken);
        // token信息被修改
        if(!tokenService.isRealToken(accessToken)){
            R.error("身份校验失败，请重新登陆");
        }
        // 如果token过期
        if(tokenService.isExpires(accessToken)){
            HandleErrorResponse.handleErrorResponse(response,R.error("身份信息已经过期，请重新登陆"));
            return false;
        }

        Map<String,Integer> payload = tokenService.parseToken(accessToken.strip());
        Integer userId = payload.get("userId");
        Integer admin = payload.get("admin");
        // 如果token中没有用户名
        if(userId==null){
            HandleErrorResponse.handleErrorResponse(response,R.error("身份校验失败，请重新登陆"));
            return false;
        }

        // 将userId和admin存入请求头
        request.setAttribute("userId",userId);
        request.setAttribute("admin",admin);
        log.info("通过登陆拦截器");
        return true;
    }


}
