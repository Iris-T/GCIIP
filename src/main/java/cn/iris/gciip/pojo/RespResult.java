package cn.iris.gciip.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Iris 2022/8/6
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespResult {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，用于向前端提示错误内容
     */
    private String msg;
    /**
     * 结果数据
     */
    private Object data;

    public RespResult() {}

    public RespResult(Integer code, String msg) {
        this(code, msg, null);
    }

    public RespResult(Integer code, Object data) {
        this(code, null, data);
    }

    public RespResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RespResult success() {
        return new RespResult(HttpStatus.OK.value(), "请求处理成功", null);
    }

    public static RespResult success(String msg) {
        return new RespResult(HttpStatus.OK.value(), msg, null);
    }

    public static RespResult success(String msg, Object data) {
        return new RespResult(HttpStatus.OK.value(), msg, data);
    }

    public static RespResult error() {
        return new RespResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求处理失败", null);
    }

    public static RespResult error(String msg) {
        return new RespResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    public static RespResult error(String msg, Object data) {
        return new RespResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static RespResult bad() {
        return new RespResult(HttpStatus.BAD_REQUEST.value(), "错的请求方式，请重试", null);
    }

    public boolean isSuccess() {
        return this.code == 200;
    }
}
