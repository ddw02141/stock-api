package com.example.stock_api.model;

import lombok.Data;

import java.util.List;

@Data
public class Indicators {
  private List<Quote> quote;
}
