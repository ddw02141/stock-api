package com.example.stock_api.model;

import lombok.Data;

import java.util.List;

@Data
public class Result {
  private Indicators indicators;
  private List<Integer> timestamp;
}
