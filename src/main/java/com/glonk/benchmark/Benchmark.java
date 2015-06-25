package com.glonk.benchmark;

public interface Benchmark {

  String name();
  
  void run() throws Exception;

}
