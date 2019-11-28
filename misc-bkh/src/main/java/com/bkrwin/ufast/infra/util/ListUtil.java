package com.bkrwin.ufast.infra.util;

import java.util.List;

public class ListUtil
{
  public static boolean isNullOrEmpty(List list)
  {
    return (null == list) || (list.isEmpty());
  }
}
