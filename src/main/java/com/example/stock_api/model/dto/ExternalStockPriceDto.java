package com.example.stock_api.model.dto;

import com.example.stock_api.model.Chart;
import lombok.Data;

@Data
public class ExternalStockPriceDto {

  private Chart chart;
}
