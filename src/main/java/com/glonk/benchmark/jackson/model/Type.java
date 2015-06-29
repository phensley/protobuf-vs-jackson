package com.glonk.benchmark.jackson.model;

public enum Type {

  UNKNOWN(0),
  FOO(1),
  BAR(2)
  ;
  
  private final int value;
  
  private Type(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return value;
  }
  
}
