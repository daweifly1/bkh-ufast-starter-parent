package yb.ecp.fast.flow.service.vo;

import java.util.List;

public class RelationVO
{
  private String sourceId;
  private List<RelationVO> relations;
  
  public String getSourceId()
  {
    return this.sourceId;
  }
  
  public void setSourceId(String sourceId)
  {
    this.sourceId = sourceId;
  }
  
  public List<RelationVO> getRelations()
  {
    return this.relations;
  }
  
  public void setRelations(List<RelationVO> relations)
  {
    this.relations = relations;
  }
}
