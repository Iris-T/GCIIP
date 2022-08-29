package cn.iris.gciip.controller;

import cn.iris.gciip.pojo.RespResult;
import cn.iris.gciip.util.DiscernUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * 垃圾分类识别接口
 * @author Iris 2022/8/29
 */
@RestController
public class DiscernController {

    @PostMapping("/image/discern")
    public RespResult discernByMultiFile(@RequestPart("image") MultipartFile image) {

        if (image.isEmpty()) {
            return new RespResult(400, "参数有误，请重试", null);
        }
        BufferedImage srcImage;
        HashMap<String, String> res = new HashMap<>();
        try {
            FileInputStream in = (FileInputStream) image.getInputStream();
            srcImage = ImageIO.read(in);
            String[] split = Objects.requireNonNull(DiscernUtil.predict(srcImage)).split(" ");
            res.put("type", split[0]);
            res.put("name", split[1]);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
            return RespResult.error("识别出错，请联系管理员");
        }

        return ObjectUtils.isEmpty(res)
                ? new RespResult(500, "识别出错，请联系管理员")
                : new RespResult(200, "识别成功", res);
    }
}
