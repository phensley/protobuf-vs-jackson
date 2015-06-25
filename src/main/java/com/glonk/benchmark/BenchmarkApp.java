package com.glonk.benchmark;

import java.text.DecimalFormat;

import com.glonk.benchmark.jackson.JacksonBenchmarks;
import com.glonk.benchmark.protobuf.ProtobufBenchmarks;


public class BenchmarkApp {
  
  private static final DecimalFormat DECIMAL = new DecimalFormat("#,###.0");
  
  private static final int LOOPS = 10;
  
  private static final int ITERS = 20000;
  
  public static void main(String[] args) throws Exception {
    System.out.printf("%30s %8s %8s %8s\n", "test", "min", "max", "avg");
    System.out.println("-----------------------------------------------------------------");
    
    for (int numChildren : new int[] { 0, 8, 16, 32 }) {
      System.out.println("\nwith " + numChildren + " children");

      BenchmarkSet[] sets = new BenchmarkSet[] {
          new JacksonBenchmarks(false, numChildren),
          new JacksonBenchmarks(true, numChildren),
          new ProtobufBenchmarks(numChildren)
      };

      for (BenchmarkSet set : sets) {
        run(set);
      }
      
      System.out.println("\nencoded sizes:");
      for (BenchmarkSet set : sets) {
        System.out.printf("%30s   %s\n", set.name(), set.encodedSize());
      }
      System.out.println();
    }
  }
  
  private static void run(BenchmarkSet set) throws Exception {
    run(set.serialization(), LOOPS, ITERS);
    run(set.deserialization(), LOOPS, ITERS);
  }
  
  private static void run(Benchmark benchmark, int loops, int iters) throws Exception {
    double min = 0;
    double max = 0;
    double total = 0;
    
    // warm up
    for (int i = 0; i < 10; i++) {
      benchmark.run();
    }
    
    Timer timer = new Timer();
    for (int i = 0; i < loops; i++) {
      timer.start();

      // timing loop
      for (int j = 0; j < iters; j++) {
        benchmark.run();
      }

      double elapsed = timer.elapsedMs();
      if (i == 0) {
        min = max = elapsed;
      } else {
        min = Math.min(min, elapsed);
        max = Math.max(max, elapsed);
      }
      total += elapsed;
    }
    
    double avg = total / loops;
    display(benchmark, min, max, avg);
  }

  private static void display(Benchmark benchmark, double min, double max, double avg) {
    System.out.printf("%30s %8s %8s %8s\n", 
        benchmark.name(), 
        DECIMAL.format(min),
        DECIMAL.format(max),
        DECIMAL.format(avg));
  }
  
  static class Timer {
    
    private long start = now();

    public void start() {
      start = now();
    }
    
    public double elapsedMs() {
      long now = now();
      return (now - start) / 1000000.0;
    }
    
    private long now() {
      return System.nanoTime();
    }
    
  }
  
}