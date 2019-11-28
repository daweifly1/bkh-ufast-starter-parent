package com.bkrwin.ufast.infra.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil
{
  public static byte[] serialize(Object object)
  {
    ObjectOutputStream objectOutputStream = null;
    ByteArrayOutputStream byteArrayOutputStream = null;
    try
    {
      byteArrayOutputStream = new ByteArrayOutputStream();
      objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
      objectOutputStream.writeObject(object);
      return byteArrayOutputStream.toByteArray();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Object deserialize(byte[] bytes)
  {
    ByteArrayInputStream byteArrayInputStream = null;
    try
    {
      byteArrayInputStream = new ByteArrayInputStream(bytes);
      ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
      return objectInputStream.readObject();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
