package com.bkrwin.ufast.infra.infra;

public class SearchCommonVO<T>
{
  private int pageNum;
  private int pageSize;
  private T filters;
  private String sort;
  
  public String getSort()
  {
    return this.sort;
  }
  
  public void setSort(String sort)
  {
    this.sort = sort;
  }
  
  public Integer getPageNum()
  {
    return Integer.valueOf(this.pageNum);
  }
  
  public void setPageNum(int pageNum)
  {
    this.pageNum = pageNum;
  }
  
  public Integer getPageSize()
  {
    return Integer.valueOf(this.pageSize);
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public T getFilters()
  {
    return (T)this.filters;
  }
  
  public void setFilters(T filters)
  {
    this.filters = filters;
  }
}
