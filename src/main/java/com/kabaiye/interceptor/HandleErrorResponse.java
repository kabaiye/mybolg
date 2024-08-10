package com.kabaiye.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabaiye.entity.vo.R;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;


public class HandleErrorResponse {
    /**
     * 将R类型响应数据封装进Response
     */
    public static void handleErrorResponse(HttpServletResponse response, R r) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(r.getCode());
        String jsonResponse = new ObjectMapper().writeValueAsString(r);
        OutputStream o = response.getOutputStream();
        o.write(jsonResponse.getBytes());
    }
}
