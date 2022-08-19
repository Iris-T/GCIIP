package cn.iris.gciip.handler;

import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.util.WebUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Iris 2022/8/19
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        RespResult<Object> result = new RespResult<Object>(HttpStatus.FORBIDDEN.value(), "用户权限不足，拒绝访问！");
        String json = JSON.toJSONString(result);
        // 处理异常
        WebUtil.renderString(httpServletResponse, json);
    }
}
