package cn.iris.gciip.generate;

import cn.iris.gciip.pojo.User;
import cn.iris.gciip.service.UserService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author Iris 2022/8/18
 */
@SpringBootTest
public class GenerateUserData {

    @Autowired
    private UserService userService;

    private static final Faker FAKE_INFO_EN = new Faker();
    private static final Faker FAKE_INFO_CN = Faker.instance(Locale.CHINA);

    @Test
    public void insert() {
        System.out.println(insertUserData(10000));
    }

    private String insertUserData(int num) {
        Random rand = new Random();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            User user = new User();
            String username = FAKE_INFO_EN.name().username().split("\\.")[0];
            user.setUsername(username);
            user.setNickname(FAKE_INFO_CN.name().name());
            user.setPassword("$10$X9zb60/GvzklAzNDZrY3beY9yd8O2s3qVsUWgIZCw351dFsPeVjKe");
            String emailAddress = username + "@" + FAKE_INFO_EN.internet().emailAddress().split("@")[1];
            user.setEmail(emailAddress);
            user.setPhone(FAKE_INFO_CN.phoneNumber().phoneNumber());
            user.setAddress(FAKE_INFO_CN.address().fullAddress());
            user.setGender(String.valueOf(rand.nextInt(3))); // 0 | 1 | 2
            long admin = rand.nextInt(4)+1; // 1 | 2 | 3 | 4
            user.setCreateBy(admin);
            user.setUpdateBy(admin);
            Date date = FAKE_INFO_CN.date().birthday(0, 10);
            user.setCreateTime(date);
            user.setUpdateTime(date);
            users.add(user);
        }
        // 更新用户数据
        boolean b = userService.saveOrUpdateBatch(users);
        String res = "插入" + num + "条数据";
        return b ? res+"成功！" : res+"失败！";
    }
}
