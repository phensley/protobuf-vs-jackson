package com.glonk.benchmark.protobuf;

import com.glonk.benchmark.Benchmark;
import com.glonk.benchmark.BenchmarkSet;
import com.glonk.benchmark.protobuf.model.Obj;
import com.glonk.benchmark.protobuf.model.Type;


public class ProtobufBenchmarks implements BenchmarkSet {

  private static final String name = "protobuf";
  
  private final int numChildren;
  
  public ProtobufBenchmarks(int numChildren) {
    this.numChildren = numChildren;
  }
  
  @Override
  public String name() {
    return name;
  }
  
  @Override
  public Benchmark serialization() {
    final Obj obj = buildObject("parent");
    return new Benchmark() {
      @Override
      public String name() {
        return name + " serialization";
      }
      @Override
      public void run() throws Exception {
        obj.toByteArray();
      }
    };
  }
  
  @Override
  public Benchmark deserialization() {
    Obj obj = buildObject("parent");
    final byte[] bytes = obj.toByteArray();
    return new Benchmark() {
      @Override
      public String name() {
        return name + " deserialization";
      }
      @Override
      public void run() throws Exception {
        Obj.parseFrom(bytes);
      }
    };
  }

  @Override
  public int encodedSize() {
    Obj obj = buildObject("parent");
    byte[] bytes = obj.toByteArray();
    return bytes.length;
  }
  
  private Obj buildObject(String name) {
    return buildObject(name, true);
  }
  
  private Obj buildObject(String name, boolean children) {
    Obj.Builder builder = Obj.newBuilder()
        .setType(Type.FOO)
        .setNum32(Integer.MAX_VALUE)
        .setNum64(Long.MAX_VALUE)
        .setStr(name);
    if (children) {
      for (int i = 0; i < numChildren; i++) {
        builder.addChildren(buildObject("child " + (i + 1), false));
      }
    }
    return builder.build();
  }
  
}
