package com.glonk.benchmark.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.glonk.benchmark.Benchmark;
import com.glonk.benchmark.BenchmarkSet;
import com.glonk.benchmark.jackson.model.Obj;
import com.glonk.benchmark.jackson.model.Type;


public class JacksonBenchmarks implements BenchmarkSet {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final String name;

  private final int numChildren;
  
  public JacksonBenchmarks(boolean withAfterburner, int numChildren) {
    this.numChildren = numChildren;
    if (withAfterburner) {
      name = "afterburner";
      objectMapper.registerModule(new AfterburnerModule());
    } else {
      name = "jackson";
    }
  }
  
  @Override
  public String name() {
    return name;
  }
  
  @Override
  public Benchmark serialization() throws Exception {
    final Obj obj = buildObject("parent");
    return new Benchmark() {
      @Override
      public String name() {
        return name + " serialization";
      }
      
      @Override
      public void run() throws Exception {
        objectMapper.writeValueAsString(obj);
      }
    };
  }
  
  @Override
  public Benchmark deserialization() throws Exception {
    final Obj obj = buildObject("parent");
    String json = objectMapper.writeValueAsString(obj);
    return new Benchmark() {
      @Override
      public String name() {
        return name + " deserialization";
      }

      @Override
      public void run() throws Exception {
        objectMapper.readValue(json, Obj.class);
      }
    };
  }

  @Override
  public int encodedSize() throws Exception {
    Obj obj = buildObject("parent");
    String json = objectMapper.writeValueAsString(obj);
    return json.length();
  }

  private Obj buildObject(String name) {
    return buildObject(name, true);
  }
  
  private Obj buildObject(String name, boolean children) {
    Obj.Builder builder = Obj.newBuilder()
        .setType(Type.FOO)
        .setFlag(true)
        .setNum32(Integer.MAX_VALUE)
        .setNum64(Long.MAX_VALUE)
        .setStr(name);
    if (children) {
      for (int i = 0; i < numChildren; i++) {
        builder.addChild(buildObject("child " + (i + 1), false));
      }
    }
    return builder.build();
  }
  
}
