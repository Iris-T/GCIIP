package cn.iris.gciip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Iris 2022/8/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginParam {

    private String username;
    private String password;
}
