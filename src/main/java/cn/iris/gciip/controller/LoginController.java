package cn.iris.gciip.controller;

import cn.iris.gciip.dto.LoginParam;
import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Iris 2022/8/6
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录业务
     * @param user 登录参数对象
     * @return 登录响应结果对象
     */
    @PostMapping("/login")
    public RespResult<Object> login(LoginParam user) {
        return loginService.login(user);
    }

    /**
     * 退出登录
     * @return 退出结果
     */
    @RequestMapping("/logout")
    public RespResult<Object> logout() {
        return loginService.logout();
    }
}
