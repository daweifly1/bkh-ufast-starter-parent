/*    */ package yb.ecp.fast.dto;
/*    */ 
/*    */ public class SessionDataDTO
/*    */ {
/*    */   String userId;
/*    */   Object data;
/*    */   Integer[] codes;
/*    */   
/*    */   public String getUserId() {
/* 10 */     return this.userId;
/*    */   }
/*    */   
/*    */   public void setUserId(String userId) {
/* 14 */     this.userId = userId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object getData()
/*    */   {
/* 22 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(Object data) {
/* 26 */     this.data = data;
/*    */   }
/*    */   
/*    */   public Integer[] getCodes() {
/* 30 */     return this.codes;
/*    */   }
/*    */   
/*    */   public void setCodes(Integer[] codes) {
/* 34 */     this.codes = codes;
/*    */   }
/*    */ }


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\dto\SessionDataDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */