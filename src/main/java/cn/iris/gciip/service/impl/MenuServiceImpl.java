package cn.iris.gciip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.iris.gciip.pojo.Menu;
import cn.iris.gciip.service.MenuService;
import cn.iris.gciip.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2022-08-19 13:29:59
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




