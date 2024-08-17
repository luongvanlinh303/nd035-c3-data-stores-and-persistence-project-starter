package com.udacity.jdnd.course3.critter.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {
  public static <T> List<T> pickNRandom(Collection<T> lst, int n) {
    List<T> copy = new ArrayList<>(lst);
    Collections.shuffle(copy);
    return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
  }
}
