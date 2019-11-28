/*    */ package yb.ecp.fast.dto;
/*    */ 
/*    */ public class AccountPwdDTO extends AccountDTO {
/*    */   private Integer type;
/*    */   private String password;
/*    */   
/*    */   public String getPassword() {
/*  8 */     return this.password;
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 12 */     this.password = password;
/*    */   }
/*    */   
/*    */ 
/*    */   public Integer getType()
/*    */   {
/* 18 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(Integer type) {
/* 22 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\dto\AccountPwdDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */