package cn.iris.gciip.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iris 2022/8/6
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespResult<T> {
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
    private T data;

    public RespResult(Integer code, String msg) {
        this(code, msg, null);
    }

    public RespResult(Integer code, T data) {
        this(code, null, data);
    }

    public RespResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
