package com.joker.common.utils;

import java.util.ArrayList;
import java.util.Collections;

public class ArraysUtils{
  public static <T> T[] mergeArrays(T[] array1,T[] array2) {
    ArrayList<T> list=new ArrayList<>();
    Collections.addAll(list,array1);
    Collections.addAll(list,array2);
    return (T[])list.toArray(new Object[0]);
  }
}
