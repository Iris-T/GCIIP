package cn.iris.gciip.mapper;

import cn.iris.gciip.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author asus
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2022-08-18 19:41:34
* @Entity cn.iris.gciip.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




