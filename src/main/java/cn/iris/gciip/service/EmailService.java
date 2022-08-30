package cn.iris.gciip.service;

import cn.iris.gciip.pojo.RespResult;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Iris
 * @ClassName EmailService
 * @Description 邮箱信息服务类
 * @date 2022/8/30 15:13
 */

public interface EmailService {

    /**
     * 发送注册验证码邮件
     * @param to 目标邮箱地址
     * @param code 验证码
     * @return 发送结果
     */
    public RespResult sendMail(String to, String code);
}
