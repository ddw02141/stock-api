package com.example.stock_api.model;

import lombok.Data;

@Data
public class Pre {
  private int gmtoffset;
  private int end;
  private int start;
  private String timezone;
}
