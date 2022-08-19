package cn.iris.gciip.mapper;

import cn.iris.gciip.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author asus
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2022-08-19 13:29:59
* @Entity cn.iris.gciip.pojo.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询用户ID对应权限字段
     * @param uid 用户ID
     * @return 权限字段集合
     */
    List<String> queryPermsByUid(@Param("uid") Long uid);
}




