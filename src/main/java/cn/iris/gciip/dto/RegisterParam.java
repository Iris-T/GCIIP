package cn.iris.gciip.dto;

import cn.iris.gciip.validation.IsEmailValid;
import cn.iris.gciip.validation.IsPasswordMatching;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Iris
 * @ClassName RegisterParam
 * @Description 注册DTO
 * @date 2022/8/30 14:46
 */

@IsPasswordMatching
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterParam {

    // 用户名
    @NotBlank
    private String username;
    // 用户密码
    @NotBlank
    private String password;
    // 重复验证密码
    @NotBlank
    private String repeatPwd;
    // 验证邮箱
    @NotBlank
    @IsEmailValid
    private String email;
    // 验证码
    @NotBlank
    private String code;
}
