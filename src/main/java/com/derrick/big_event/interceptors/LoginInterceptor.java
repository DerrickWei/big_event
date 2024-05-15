package com.derrick.big_event.interceptors;

import com.derrick.big_event.pojo.Result;
import com.derrick.big_event.utils.JwtUtil;
import com.derrick.big_event.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            // Get user info from JWT
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // Save claims to ThreadLocal
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Clear ThreadLocal data
        ThreadLocalUtil.remove();
    }
}
