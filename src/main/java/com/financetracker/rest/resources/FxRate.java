package com.financetracker.rest.resources;

public class FxRate {

  private String id;
  private Double val;
  private String to;
  private String fr;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getVal() {
    return val;
  }

  public void setVal(Double val) {
    this.val = val;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getFr() {
    return fr;
  }

  public void setFr(String fr) {
    this.fr = fr;
  }
}
