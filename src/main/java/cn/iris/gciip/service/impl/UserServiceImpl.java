package cn.iris.gciip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.iris.gciip.pojo.User;
import cn.iris.gciip.service.UserService;
import cn.iris.gciip.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-08-18 19:41:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




