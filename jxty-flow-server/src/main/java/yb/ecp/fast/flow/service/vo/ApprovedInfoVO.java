package yb.ecp.fast.flow.service.vo;

import java.util.List;

public class ApprovedInfoVO
{
  private Integer hisMaxLength;
  private List<List<HistoryRecordVO>> hisInfos;
  private List<HistoryRecordVO> currInfo;
  
  public Integer getHisMaxLength()
  {
    return this.hisMaxLength;
  }
  
  public void setHisMaxLength(Integer hisMaxLength)
  {
    this.hisMaxLength = hisMaxLength;
  }
  
  public List<List<HistoryRecordVO>> getHisInfos()
  {
    return this.hisInfos;
  }
  
  public void setHisInfos(List<List<HistoryRecordVO>> hisInfos)
  {
    this.hisInfos = hisInfos;
  }
  
  public List<HistoryRecordVO> getCurrInfo()
  {
    return this.currInfo;
  }
  
  public void setCurrInfo(List<HistoryRecordVO> currInfo)
  {
    this.currInfo = currInfo;
  }
}
