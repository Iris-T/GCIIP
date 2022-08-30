package cn.iris.gciip.service;

import cn.iris.gciip.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author asus
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2022-08-18 19:41:34
*/
public interface UserService extends IService<User> {

    /**
     * 验证用户验证邮箱是否已被注册
     * @param email 用户邮箱
     * @return 验证结果
     */
    public boolean isEmailHasRegistered(String email);

    /**
     * 验证用户名是否已被注册
     * @param username 用户名
     * @return 验证结果
     */
    public boolean isUsernameHasRegistered(String username);

}
