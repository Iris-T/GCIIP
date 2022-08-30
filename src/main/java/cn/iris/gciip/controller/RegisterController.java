package cn.iris.gciip.controller;

import cn.iris.gciip.dto.RegisterParam;
import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.service.EmailService;
import cn.iris.gciip.service.RegisterService;
import cn.iris.gciip.service.UserService;
import cn.iris.gciip.util.RedisCache;
import cn.iris.gciip.util.VerificationUtil;
import cn.iris.gciip.validation.IsEmailValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Iris
 * @ClassName RegisterController
 * @Description 用户注册接口
 * @date 2022/8/30 14:44
 */

@RestController("/user")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public RespResult register(RegisterParam param) {
        return registerService.register(param);
    }

    @PostMapping("/register/code")
    public RespResult sendCode(String mail) {
        if (mail.isEmpty()) {
            return RespResult.bad();
        }
        return registerService.sendCode(mail);
    }

    @PostMapping("/register/username/valid")
    public RespResult usernameValid(String username) {
        return userService.isUsernameHasRegistered(username)
                ? RespResult.error("用户名已被注册！")
                : RespResult.success("用户名有效！");
    }
}
