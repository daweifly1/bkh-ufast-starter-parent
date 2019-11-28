/*    */ package yb.ecp.fast.dto;
/*    */ 
/*    */ 
/*    */ public class RegMemberDTO
/*    */   extends ProfileDTO
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private String workSpaceId;
/*    */   
/*    */   private String workspaceName;
/*    */   
/*    */   private Integer site;
/*    */   
/*    */   private Integer memberType;
/*    */   
/*    */   public String getWorkSpaceId()
/*    */   {
/* 19 */     return this.workSpaceId;
/*    */   }
/*    */   
/*    */   public void setWorkSpaceId(String workSpaceId) {
/* 23 */     this.workSpaceId = workSpaceId;
/*    */   }
/*    */   
/*    */   public String getWorkspaceName() {
/* 27 */     return this.workspaceName;
/*    */   }
/*    */   
/*    */   public void setWorkspaceName(String workspaceName) {
/* 31 */     this.workspaceName = workspaceName;
/*    */   }
/*    */   
/*    */   public Integer getSite() {
/* 35 */     return this.site;
/*    */   }
/*    */   
/*    */   public void setSite(Integer site) {
/* 39 */     this.site = site;
/*    */   }
/*    */   
/*    */   public Integer getMemberType() {
/* 43 */     return this.memberType;
/*    */   }
/*    */   
/*    */   public void setMemberType(Integer memberType) {
/* 47 */     this.memberType = memberType;
/*    */   }
/*    */ }


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\dto\RegMemberDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */