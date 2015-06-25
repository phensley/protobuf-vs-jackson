package com.glonk.benchmark.jackson.model;

import java.util.ArrayList;
import java.util.List;


public class Obj {

  protected Type type = Type.UNKNOWN;
  
  protected boolean flag;

  protected int num32;
  
  protected long num64;
  
  protected String str;
  
  protected List<Obj> children;
  
  public void setType(Type type) {
    this.type = type;
  }
  
  public Type getType() {
    return type;
  }
  
  public void setFlag(boolean flag) {
    this.flag = flag;
  }
  
  public boolean getFlag() {
    return flag;
  }
  
  public void setNum32(int num) {
    this.num32 = num;
  }

  public int getNum32() {
    return num32;
  }
  
  public void setNum64(long num) {
    this.num64 = num;
  }
  
  public long getNum64() {
    return num64;
  }
  
  public void setStr(String str) {
    this.str = str;
  }
  
  public String getStr() {
    return str;
  }

  public void setChildren(List<Obj> children) {
    this.children = children;
  }
  
  public List<Obj> getChildren() {
    return this.children;
  }
    
  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    
    private final Obj obj = new Obj();

    private List<Obj> children;

    public Builder setType(Type type) {
      obj.type = type;
      return this;
    }
    
    public Builder setFlag(boolean flag) {
      obj.flag = flag;
      return this;
    }
    
    public Builder setNum32(int num) {
      obj.num32 = num;
      return this;
    }
    
    public Builder setNum64(long num) {
      obj.num64 = num;
      return this;
    }
    
    public Builder setStr(String str) {
      obj.str = str;
      return this;
    }
    
    public Builder addChild(Obj child) {
      if (this.children == null) {
        this.children = new ArrayList<>();
      }
      this.children.add(child);
      return this;
    }
    
    public Obj build() {
      obj.setChildren(children);
      return obj;
    }
    
  }
  
}
