package cn.iris.gciip.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Iris 2022/8/6
 */
public class WebUtil {

    /**
     * 将字符串渲染至前端
     * @param resp 渲染对象
     * @param str 渲染字符串
     * @return null
     */
    public static String renderString(HttpServletResponse resp, String str) {
        try {
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
