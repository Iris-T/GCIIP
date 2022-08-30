package cn.iris.gciip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.iris.gciip.pojo.User;
import cn.iris.gciip.service.UserService;
import cn.iris.gciip.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-08-18 19:41:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 验证用户验证邮箱是否已被注册
     * @param email 用户邮箱
     * @return 验证结果
     */
    @Override
    public boolean isEmailHasRegistered(String email) {
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("email", email));
        return count != 0;
    }

    /**
     * 验证用户名是否已被注册
     *
     * @param username 用户名
     * @return 验证结果
     */
    @Override
    public boolean isUsernameHasRegistered(String username) {
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username));
        return count != 0;
    }
}




