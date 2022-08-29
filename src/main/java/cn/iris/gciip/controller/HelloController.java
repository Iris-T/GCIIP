package cn.iris.gciip.controller;

import cn.iris.gciip.pojo.RespResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Iris 2022/8/6
 */
@RestController
//@RequestMapping("t")
public class HelloController {

    @GetMapping("hello")
    @PreAuthorize("@authorityExp.hasAuthority('SuperAdmin')")
    public RespResult hello() {
        return new RespResult(200, "获取成功", "hello");
    }
}
