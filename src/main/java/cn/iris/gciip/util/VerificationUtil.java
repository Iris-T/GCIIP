package cn.iris.gciip.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Iris
 * @ClassName VerificationUtil
 * @Description 验证码生成器工具类
 * @date 2022/8/30 15:20
 */

public class VerificationUtil {

    private static final String SYMBOLS = "7PB2T93KSUV1H68AILNQDWRFECOYXGG450ZM";
    private static final Random RANDOM = new SecureRandom();
    private static final int CODE_LEN = 6;

    // 生成6位随机数
    public static String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LEN; i++) {
            code.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));
        }
        return code.toString();
    }
}
