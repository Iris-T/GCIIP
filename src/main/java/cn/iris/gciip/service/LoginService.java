package cn.iris.gciip.service;

import cn.iris.gciip.dto.LoginParam;
import cn.iris.gciip.pojo.RespResult;

/**
 * @author Iris 2022/8/6
 */
public interface LoginService {
    /**
     * 登录业务
     * @param user 登录参数对象
     * @return 登录响应结果对象
     */
    RespResult<Object> login(LoginParam user);

    /**
     * 退出登录
     * @return 退出结果
     */
    RespResult<Object> logout();
}
