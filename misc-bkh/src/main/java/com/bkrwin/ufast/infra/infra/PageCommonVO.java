package com.bkrwin.ufast.infra.infra;

import com.github.pagehelper.PageInfo;
import java.util.List;

public class PageCommonVO<T>
{
  private PageInfo pageInfo;
  
  public void setPageInfo(PageInfo pageInfo)
  {
    this.pageInfo = pageInfo;
  }
  
  public PageInfo getPageInfo()
  {
    return this.pageInfo;
  }
  
  public void setPageInfoList(List<T> list)
  {
    this.pageInfo.setList(list);
  }
  
  public List<T> getPageInfoList()
  {
    return this.pageInfo.getList();
  }
}
