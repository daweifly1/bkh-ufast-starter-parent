package yb.ecp.fast.flow.inframe;

public class FlowException
  extends RuntimeException
{
  private static final long serialVersionUID = 2368925481129834020L;
  private int code;
  private Object value;
  
  public FlowException(String message)
  {
    super(message);
  }
  
  public FlowException(int code, String message)
  {
    super(message);
    this.code = code;
  }
  
  public FlowException(ErrorCode errorCode)
  {
    super(errorCode.getDesc());
    this.code = errorCode.getCode();
  }
  
  public FlowException(ErrorCode errorCode, Object value)
  {
    this(errorCode);
    this.value = value;
  }
  
  public FlowException(ErrorCode errorCode, Object value, Throwable cause)
  {
    super(errorCode.getDesc(), cause);
    this.code = errorCode.getCode();
    this.value = value;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public Object getValue()
  {
    return this.value;
  }
}
