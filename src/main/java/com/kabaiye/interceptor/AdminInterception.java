package com.kabaiye.interceptor;

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
public class AdminInterception implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    /**
     * 由于到达这里的请求必须经过登陆拦截器，所以token有效性不再验证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("{} 进入admin interceptor", HttpUtil.getClientIp(request));
        Integer admin = (Integer) request.getAttribute("admin");
        // 如果不是管理员
        if(admin!=1){
            HandleErrorResponse.handleErrorResponse(response,R.error("无权限访问"));
            return false;
        }
        return true;
    }
}
