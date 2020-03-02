package cn.ipanel.questionnaireserver.exception;


import cn.ipanel.questionnaireserver.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.sql.SQLException;

/**
 * @author lenovo
 * @Classname BExceptionHandler
 * @Description 自定义异常处理
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public R handleRRException(BaseException e) {
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error(300, "数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

    @ExceptionHandler(SQLException.class)
    public R handleSQLException(SQLException e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return R.error(403, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public R handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return R.error(403, e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return R.error(403, e.getMessage());
    }

    /**
     *参数绑定异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleConstraintViolationException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return R.error(403, e.getMessage());
    }

    /**
     * BindException
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R handleConstraintViolationException(BindException e) {
        log.error(e.getMessage(), e);
        return R.error(403, e.getMessage());
    }

}
