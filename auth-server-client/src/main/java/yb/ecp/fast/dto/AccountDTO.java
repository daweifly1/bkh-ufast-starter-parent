/*    */ package yb.ecp.fast.dto;
/*    */ 
/*    */ 
/*    */ public class AccountDTO
/*    */ {
/*    */   private String loginName;
/*    */   private Integer status;
/*    */   private String userId;
/*    */   
/*    */   public String getUserId()
/*    */   {
/* 12 */     return this.userId;
/*    */   }
/*    */   
/*    */   public void setUserId(String userId) {
/* 16 */     this.userId = userId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public AccountDTO()
/*    */   {
/* 23 */     this.status = Integer.valueOf(0);
/*    */   }
/*    */   
/*    */   public String getLoginName() {
/* 27 */     return this.loginName;
/*    */   }
/*    */   
/*    */   public void setLoginName(String loginName) {
/* 31 */     this.loginName = loginName;
/*    */   }
/*    */   
/*    */   public Integer getStatus() {
/* 35 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(Integer status) {
/* 39 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\dto\AccountDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */