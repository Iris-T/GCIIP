package cn.iris.gciip.service.impl;

import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Iris
 * @ClassName EmailServiceImpl
 * @Description 邮箱信息服务实现类
 * @date 2022/8/30 15:14
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送注册验证码邮件
     *
     * @param to   目标邮箱地址
     * @param code 验证码
     * @return 发送结果
     */
    @Override
    public RespResult sendMail(String to, String code) {
        try {
            SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setFrom(from);
            mailMsg.setTo(to);
            mailMsg.setSubject("您的注册验证码为");
            mailMsg.setText("尊敬的注册用户,您好:\n"
                    + "\n您的注册邮件验证码为:" + code + ",本验证码 5 分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                    + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请勿直接回复）");

            mailSender.send(mailMsg);
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.error("发送邮件失败,请重试或联系管理员！");
        }
        return RespResult.success("发送邮件成功！");
    }
}
