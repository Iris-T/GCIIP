package cn.iris.gciip.service;

import cn.iris.gciip.dto.RegisterParam;
import cn.iris.gciip.pojo.RespResult;

/**
 * @author Iris
 * @ClassName RegisterService
 * @Description 注册服务类
 * @date 2022/8/30 16:36
 */


public interface RegisterService {

    /**
     * 发送邮箱验证码
     * @param mail 目标地址
     * @return 发送结果
     */
    RespResult sendCode(String mail);

    /**
     * 用户注册请求
     * @param param 请求参数，包含用户名，密码，邮箱，验证码
     * @return 注册结果
     */
    RespResult register(RegisterParam param);
}
