package com.example.stock_api.model;

import lombok.Data;

import java.util.List;

@Data
public class Chart {
  private List<Result> result;
}
