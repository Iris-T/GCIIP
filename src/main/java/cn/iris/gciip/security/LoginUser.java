package cn.iris.gciip.security;

import cn.iris.gciip.pojo.User;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Iris 2022/8/6
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    /** 登录用户 */
    private User user;
    /** 权限信息 */
    private List<String> permissions;
    /** 权限信息封装对象 */
    @JSONField(serialize = false) // 不做序列化
    private List<SimpleGrantedAuthority> authorities;
    
    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null != authorities) {
            return authorities;
        }
        // 将permissions中的权限集合封装成SimpleGrantedAuthority对象
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return "0".equals(user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "0".equals(user.getStatus());
    }
}
