package com.bkrwin.ufast.infra.infra;

public class Page
{
  protected int curPage;
  protected int pageSize;
  private int totalRows;
  
  public Page() {}
  
  public Page(int curPage, int pageSize, int totalRows)
  {
    this.curPage = curPage;
    this.pageSize = pageSize;
    this.totalRows = totalRows;
  }
  
  public int getCurPage()
  {
    return this.curPage;
  }
  
  public void setCurPage(int curPage)
  {
    this.curPage = curPage;
  }
  
  public void setCurPage(int curPage, int pageSize)
  {
    this.curPage = ((curPage - 1) * pageSize);
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public void setPageSize(int pageSize, int curPage)
  {
    this.pageSize = (pageSize * curPage);
  }
  
  public int getTotalRows()
  {
    return this.totalRows;
  }
  
  public void setTotalRows(int totalRows)
  {
    this.totalRows = totalRows;
  }
}
