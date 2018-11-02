package com.financetracker.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingUtil {
  public static <T> List<List<T>> chunk(List<T> input, int chunkSize) {
    int inputSize = input.size();
    int chunkCount = (int) Math.ceil(inputSize / (double) chunkSize);

    Map<Integer, List<T>> map = new HashMap<>(chunkCount);
    List<List<T>> chunks = new ArrayList<>(chunkCount);

    for (int i = 0; i < inputSize; i++) {
      map.computeIfAbsent(i / chunkSize, (ignore) -> {
        List<T> chunk = new ArrayList<>();
        chunks.add(chunk);
        return chunk;
      }).add(input.get(i));
    }

    return chunks;
  }
}
