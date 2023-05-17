package com.example.stock_api.model;

import lombok.Data;

import java.util.List;

@Data
public class Quote {
  private List<Integer> low;
  private List<Integer> open;
  private List<Integer> close;
  private List<Integer> high;
  private List<Integer> volume;
}
