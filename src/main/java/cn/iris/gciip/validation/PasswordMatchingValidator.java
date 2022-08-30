package cn.iris.gciip.validation;

import cn.iris.gciip.dto.RegisterParam;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Iris
 * @ClassName PasswordMatchingValidator
 * @Description 密码匹配注解执行器
 * @date 2022/8/30 16:19
 */

public class PasswordMatchingValidator implements ConstraintValidator<IsPasswordMatching, Object> {

    @Override
    public void initialize(final IsPasswordMatching constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RegisterParam user = (RegisterParam) obj;
        return user.getPassword().equals(user.getRepeatPwd());
    }

}
