package yb.ecp.fast.flow.inframe;

import com.netflix.client.ClientException;
import feign.FeignException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.el.ELException;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import yb.ecp.fast.infra.infra.ActionResult;

@ControllerAdvice
public class ExceptionAdvice
{
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  @ExceptionHandler({Exception.class})
  @ResponseBody
  public ActionResult processExcption(NativeWebRequest request, Exception e)
  {
    this.logger.error("这个是一个伟大的异常", e);
    ErrorCode code = ErrorCode.UnExceptedError;
    
    return new ActionResult(code.getCode(), code.getDesc(), null);
  }
  
  @ExceptionHandler({FlowException.class})
  @ResponseBody
  public ActionResult processExcption(FlowException e)
  {
    this.logger.error("这个是一个伟大的异常");
    return new ActionResult(e.getCode(), e.getMessage(), null);
  }
  
  @ExceptionHandler({FeignException.class})
  @ResponseBody
  public ActionResult feignExcption(FeignException e)
  {
    this.logger.error("这个是一个伟大的异常", e);
    
    ErrorCode code = ErrorCode.feignException;
    return new ActionResult(code.getCode(), code.getDesc(), null);
  }
  
  @ExceptionHandler({ClientException.class})
  @ResponseBody
  public ActionResult feignExcption(ClientException e)
  {
    this.logger.error("这个是一个伟大的异常", e);
    
    ErrorCode code = ErrorCode.feignException;
    return new ActionResult(code.getCode(), code.getDesc(), null);
  }
  
  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseBody
  public ActionResult processIllegalArumentExcption(NativeWebRequest request, IllegalArgumentException e)
  {
    this.logger.error("这个是一个伟大的异常", e);
    ErrorCode code = ErrorCode.IllegalArgument;
    
    return new ActionResult(code.getCode(), code.getDesc(), null);
  }
  
  @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
  @ResponseBody
  public ActionResult processSQLIntegrityConstraintViolationException(NativeWebRequest request, SQLIntegrityConstraintViolationException e)
  {
    this.logger.error("这个是一个伟大的异常", e);
    ErrorCode code = ErrorCode.SQLIntegrityConstraintViolation;
    return new ActionResult(code.getCode(), code.getDesc(), null);
  }
  
  @ExceptionHandler({ActivitiException.class})
  @ResponseBody
  public ActionResult processSQLIntegrityConstraintViolationException(NativeWebRequest request, ActivitiException e)
  {
    this.logger.error("这个是一个伟大的异常", e);
    if ((e.getCause() instanceof ActivitiException))
    {
      if ((e.getCause().getCause() instanceof ELException))
      {
        if ((e.getCause().getCause().getCause() instanceof FlowException)) {
          return new ActionResult(ErrorCode.Failure.getCode(), e.getCause().getCause().getCause().getMessage());
        }
        if ((e.getCause().getCause().getCause().getCause() instanceof FlowException)) {
          return new ActionResult(ErrorCode.Failure.getCode(), e.getCause().getCause().getCause().getCause().getMessage());
        }
      }
      else
      {
        return new ActionResult(ErrorCode.Failure.getCode(), e.getCause().getCause().getMessage());
      }
    }
    else {
      return new ActionResult(ErrorCode.Failure.getCode(), e.getCause().getMessage());
    }
    return new ActionResult(ErrorCode.Failure.getCode(), e.getMessage());
  }
}
