package cn.iris.gciip.filter;

import cn.iris.gciip.security.LoginUser;
import cn.iris.gciip.util.JwtUtil;
import cn.iris.gciip.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Iris 2022/8/7
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的Token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析Token获取的用户ID
        String userId;
        try {
            Claims claims = JwtUtil.parseJwt(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token非法！");
        }

        // 从Redis获取用户信息
        String redisKey = "user:"+userId;
        LoginUser loginUser = redisCache.getObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录！");
        }
        // 存入SecurityContextHolder
        // 获取权限信息，封装至Authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // filterChain做doFilter
        filterChain.doFilter(request, response);
    }
}
