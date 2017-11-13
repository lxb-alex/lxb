package com.lxb.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;


/**
 * @Description Java 的业务逻辑验证框架 fluent-validator（流式风格）<br/>
 *  它的每种类型的验证都需要重写一个验证类
 * @Author Liaoxb
 * @Date 2017/11/13 15:53:53
 */
public class NotNullValidator extends ValidatorHandler<String> implements Validator<String> {
    private String fileName;

    public NotNullValidator(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean validate(ValidatorContext validatorContext, String s) {
        if (null == s) {
            validatorContext.addError(ValidationError.create(String.format("%s不能为空！", fileName))
                    .setErrorCode(-1)
                    .setField(fileName)
                    .setInvalidValue(s));
            return false;
        }
        return true;
    }
}
