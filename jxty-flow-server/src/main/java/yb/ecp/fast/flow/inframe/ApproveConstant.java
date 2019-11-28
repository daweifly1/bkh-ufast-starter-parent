package yb.ecp.fast.flow.inframe;

public abstract interface ApproveConstant
{
  public static final String APPROVE_ARG = "direction";
  public static final int AGGREE = 0;
  public static final int REJECT_TO_PREVIOUS = 1;
  public static final int REJECT_TO_FIRST = 2;
  public static final int REJECT_TO_END = 3;
  public static final String SYNC_FLAG = "1";
  public static final String SOURCE_KEY = "sourceFlag";
  public static final String SOURCE_SUPLLIER = "1";
  public static final String SUPPLIER_NAME_KEY = "supplierName";
}
