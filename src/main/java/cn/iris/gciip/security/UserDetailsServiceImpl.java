package cn.iris.gciip.security;

import cn.iris.gciip.mapper.MenuMapper;
import cn.iris.gciip.mapper.UserMapper;
import cn.iris.gciip.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Iris 2022/8/6
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        User user = userMapper.selectOne(queryWrapper.eq(User::getUsername, username));
        // 用户数据为空就抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误！");
        }
        // 查询对应权限信息
        List<String> perms = menuMapper.queryPermsByUid(user.getId());
        // 将数据封装为UserDetails返回
        return new LoginUser(user, perms);
    }
}
