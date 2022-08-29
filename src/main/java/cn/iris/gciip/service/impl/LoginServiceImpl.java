package cn.iris.gciip.service.impl;

import cn.iris.gciip.dto.LoginParam;
import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.security.LoginUser;
import cn.iris.gciip.service.LoginService;
import cn.iris.gciip.util.JwtUtil;
import cn.iris.gciip.util.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Iris 2022/8/6
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    /**
     * 登录业务
     *
     * @param user 登录参数对象
     * @return 登录响应结果对象
     */
    @Override
    public RespResult login(LoginParam user) {

        // 将用户名密码封装成authentication用于验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // AuthenticationManager authenticate进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证未通过
        if (Objects.isNull(authenticate)) {
            return new RespResult(402, "登录失败");
        }
        // 认证通过，使用UserId生成JWT令牌,将Token放入RespResult中返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        // 将用户信息存入Redis
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", jwt);
        // 隐藏用户敏感信息
        loginUser.getUser().setPassword(null);
        // 缓存数据保存7天
        redisCache.setCacheObject("user:"+id, loginUser, 7, TimeUnit.DAYS);
        return new RespResult(200, "登录成功", hashMap);
    }

    /**
     * 退出登录
     * @return 退出结果
     */
    @Override
    public RespResult logout() {
        // 获取SecurityContextHolder中的用户ID
        UsernamePasswordAuthenticationToken authentication = ((UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        // 删除Redis缓存
        redisCache.delObject( "user:" + userId);
        return new RespResult(200, "注销成功");
    }
}
