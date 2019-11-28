package com.bkrwin.ufast.infra.infra;

public class PageActionResult<T>
  extends ActionResult<T>
{
  private Page page;
  
  public PageActionResult() {}
  
  public PageActionResult(int code, String message, T value, Page page)
  {
    this.code = code;
    this.message = message;
    this.value = value;
    this.page = page;
  }
  
  public Page getPage()
  {
    return this.page;
  }
  
  public void setPage(Page page)
  {
    this.page = page;
  }
}
