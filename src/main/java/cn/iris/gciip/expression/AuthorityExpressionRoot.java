package cn.iris.gciip.expression;

import cn.iris.gciip.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Iris 2022/8/19
 */
@Component("authorityExp")
public class AuthorityExpressionRoot {

    public boolean hasAuthority(String authority) {
        // 获取当前用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> perms = loginUser.getPermissions();
        // 判断用户权限集合是否存在对应权限
        return perms.contains(authority);
    }
}
