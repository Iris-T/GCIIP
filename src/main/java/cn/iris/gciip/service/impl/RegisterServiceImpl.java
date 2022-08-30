package cn.iris.gciip.service.impl;

import cn.iris.gciip.dto.RegisterParam;
import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.pojo.User;
import cn.iris.gciip.service.EmailService;
import cn.iris.gciip.service.RegisterService;
import cn.iris.gciip.service.UserService;
import cn.iris.gciip.util.RedisCache;
import cn.iris.gciip.util.VerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Iris
 * @ClassName RegisterServiceImpl
 * @Description 注册服务实现类
 * @date 2022/8/30 16:38
 */

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 发送邮箱验证码
     *
     * @param mail 目标地址
     * @return 发送结果
     */
    @Override
    public RespResult sendCode(String mail) {
        // 验证邮箱是否被注册
        if (userService.isEmailHasRegistered(mail)) {
            return RespResult.error("该邮箱已被注册，请更换邮箱地址！");
        }

        String code = VerificationUtil.generateCode();
        RespResult result = emailService.sendMail(mail, code);
        // 设置5分钟失效
        if (result.isSuccess()) {
            redisCache.setCacheObject("register:"+mail, code, 5, TimeUnit.MINUTES);
        }

        return result;
    }

    /**
     * 用户注册请求
     *
     * @param param 请求参数，包含用户名，密码，邮箱，验证码
     * @return 注册结果
     */
    @Override
    public RespResult register(RegisterParam param) {
        // 验证用户是否存在,避免用户名被重复注册
        if (userService.isUsernameHasRegistered(param.getUsername())) {
            return RespResult.error("用户名已被注册！");
        }

        // 验证邮箱有效性
        if (userService.isEmailHasRegistered(param.getEmail())) {
            return RespResult.error("邮箱已被注册！");
        }

        // 验证验证码
        if (!isCodeValid(param.getCode(), param.getEmail())) {
            return RespResult.error("验证码错误或失效！");
        }

        User user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        user.setEmail(param.getEmail());
        boolean save = userService.save(user);

        return save ? RespResult.success("注册成功！") : RespResult.error("服务器错误，请重试或联系管理员！");
    }

    /**
     * 验证邮箱验证码是否正确
     * @param code 接收的验证码
     * @param email 注册邮箱
     * @return 验证结果
     */
    private boolean isCodeValid(String code, String email) {
        String redisCode = redisCache.getObject("register:"+email);
        if (redisCode.isEmpty()) { // 为空则验证码过期
            return false;
        }
        return redisCode.equals(code);
    }
}
