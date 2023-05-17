package com.example.stock_api.model;

import lombok.Data;

import java.util.List;

@Data
public class Meta {
  private List<String> validranges;
  private String range;
  private String datagranularity;
  private Currenttradingperiod currenttradingperiod;
  private int pricehint;
  private int chartpreviousclose;
  private int regularmarketprice;
  private String exchangetimezonename;
  private String timezone;
  private int gmtoffset;
  private int regularmarkettime;
  private int firsttradedate;
  private String instrumenttype;
  private String exchangename;
  private String symbol;
  private String currency;
}
