package com.example.stock_api.controller;

import com.example.stock_api.model.dto.StockPriceDto;
import com.example.stock_api.service.StockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockPriceController {

  private final StockPriceService stockPriceService;

  @GetMapping("/stock-price")
  public StockPriceDto getStockPrice() {
    return stockPriceService.getAndUpdateStockPrice();
  }
}
