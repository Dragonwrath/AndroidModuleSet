package com.joker.common.utils;

public class ArraysUtils{
  public static <T> T[] mergeArrays(T[] array1,T[] array2) {
    //noinspection all
    T[] result=(T[])new Object[array1.length+array2.length];
    System.arraycopy(result,0,array1,0,array1.length);
    System.arraycopy(result,array1.length,array2,0, array2.length);
    return result;
  }
}
