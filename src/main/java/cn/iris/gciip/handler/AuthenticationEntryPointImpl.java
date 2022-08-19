package cn.iris.gciip.handler;

import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.util.WebUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Iris 2022/8/19
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        RespResult<Object> result = new RespResult<Object>(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重试！");
        String json = JSON.toJSONString(result);
        // 处理异常
        WebUtil.renderString(httpServletResponse, json);
    }
}
