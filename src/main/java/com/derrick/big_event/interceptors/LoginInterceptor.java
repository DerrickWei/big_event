package com.derrick.big_event.interceptors;

import com.derrick.big_event.utils.JwtUtil;
import com.derrick.big_event.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            // Get token from redis
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            String redisToken = valueOperations.get(token);

            // Check if redisToken is null
            if (redisToken == null) {
                throw new RuntimeException();
            }

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
