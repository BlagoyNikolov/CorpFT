package com.financetracker.rest.resources;

import java.util.HashMap;
import java.util.Map;

public class FxResponse {

  private Map<String, FxRate> results = new HashMap<String, FxRate>();

  public Map<String, FxRate> getResults() {
    return results;
  }

  public void setResults(Map<String, FxRate> results) {
    this.results = results;
  }
}
