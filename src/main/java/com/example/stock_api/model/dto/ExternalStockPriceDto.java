package com.example.stock_api.model.dto;

import com.example.stock_api.model.Chart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalStockPriceDto {
  private Chart chart;
}
