package com.glonk.benchmark;

public interface BenchmarkSet {

  String name();
  
  Benchmark serialization() throws Exception;
  
  Benchmark deserialization() throws Exception;
 
  int encodedSize() throws Exception;
  
}
