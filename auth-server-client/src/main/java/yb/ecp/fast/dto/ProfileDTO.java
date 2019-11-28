/*     */ package yb.ecp.fast.dto;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfileDTO
/*     */   extends AccountPwdDTO
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String name;
/*     */   private String mobile;
/*     */   private String telephone;
/*     */   private Integer sex;
/*     */   private String deptId;
/*     */   private String spaceId;
/*     */   private String icon;
/*     */   private String nickname;
/*     */   private String email;
/*     */   private Integer locked;
/*     */   private List<String> roleIds;
/*     */   private List<Integer> authIds;
/*     */   private String areaCode;
/*     */   private String myself;
/*     */   private String idNumber;
/*     */   
/*     */   public String getMyself()
/*     */   {
/*  34 */     return this.myself;
/*     */   }
/*     */   
/*     */   public void setMyself(String myself)
/*     */   {
/*  39 */     this.myself = myself;
/*     */   }
/*     */   
/*     */   public String getIdNumber()
/*     */   {
/*  44 */     return this.idNumber;
/*     */   }
/*     */   
/*     */   public void setIdNumber(String idNumber)
/*     */   {
/*  49 */     this.idNumber = idNumber;
/*     */   }
/*     */   
/*     */   public String getSpaceId()
/*     */   {
/*  54 */     return this.spaceId;
/*     */   }
/*     */   
/*     */   public void setSpaceId(String spaceId)
/*     */   {
/*  59 */     this.spaceId = spaceId;
/*     */   }
/*     */   
/*     */   public List<String> getRoleIds()
/*     */   {
/*  64 */     return this.roleIds;
/*     */   }
/*     */   
/*     */   public void setRoleIds(List<String> roleIds)
/*     */   {
/*  69 */     this.roleIds = roleIds;
/*     */   }
/*     */   
/*     */   public String getTelephone()
/*     */   {
/*  74 */     return this.telephone;
/*     */   }
/*     */   
/*     */   public void setTelephone(String telephone)
/*     */   {
/*  79 */     this.telephone = telephone;
/*     */   }
/*     */   
/*     */   public Integer getSex()
/*     */   {
/*  84 */     return this.sex;
/*     */   }
/*     */   
/*     */   public void setSex(Integer sex)
/*     */   {
/*  89 */     this.sex = sex;
/*     */   }
/*     */   
/*     */   public String getDeptId()
/*     */   {
/*  94 */     return this.deptId;
/*     */   }
/*     */   
/*     */   public void setDeptId(String deptId)
/*     */   {
/*  99 */     this.deptId = deptId;
/*     */   }
/*     */   
/*     */   public String getIcon()
/*     */   {
/* 104 */     return this.icon;
/*     */   }
/*     */   
/*     */   public void setIcon(String icon)
/*     */   {
/* 109 */     this.icon = icon;
/*     */   }
/*     */   
/*     */   public String getNickname()
/*     */   {
/* 114 */     return this.nickname;
/*     */   }
/*     */   
/*     */   public void setNickname(String nickname)
/*     */   {
/* 119 */     this.nickname = nickname;
/*     */   }
/*     */   
/*     */   public String getEmail()
/*     */   {
/* 124 */     return this.email;
/*     */   }
/*     */   
/*     */   public void setEmail(String email)
/*     */   {
/* 129 */     this.email = email;
/*     */   }
/*     */   
/*     */   public Integer getLocked()
/*     */   {
/* 134 */     return this.locked;
/*     */   }
/*     */   
/*     */   public void setLocked(Integer locked)
/*     */   {
/* 139 */     this.locked = locked;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 144 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name)
/*     */   {
/* 149 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getMobile()
/*     */   {
/* 154 */     return this.mobile;
/*     */   }
/*     */   
/*     */   public void setMobile(String mobile)
/*     */   {
/* 159 */     this.mobile = mobile;
/*     */   }
/*     */   
/*     */   public List<Integer> getAuthIds()
/*     */   {
/* 164 */     return this.authIds;
/*     */   }
/*     */   
/*     */   public void setAuthIds(List<Integer> authIds)
/*     */   {
/* 169 */     this.authIds = authIds;
/*     */   }
/*     */   
/*     */   public String getAreaCode() {
/* 173 */     return this.areaCode;
/*     */   }
/*     */   
/*     */   public void setAreaCode(String areaCode) {
/* 177 */     this.areaCode = areaCode;
/*     */   }
/*     */ }


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\dto\ProfileDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */