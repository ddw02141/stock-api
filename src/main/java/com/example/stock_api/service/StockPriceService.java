package com.example.stock_api.service;

import com.example.stock_api.model.dto.ExternalStockPriceDto;
import com.example.stock_api.model.dto.StockPriceDto;
import com.example.stock_api.model.entity.StockEntity;
import com.example.stock_api.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockPriceService {

  private static final String EXTERNAL_SAMSUNG_STOCK_PRICE_URL =
      "https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d";

  private final RestTemplate restTemplate;
  private final StockRepository stockRepository;

  @Transactional
  public StockPriceDto getAndUpdateStockPrice() {
    final URI uri =
        UriComponentsBuilder.fromHttpUrl(EXTERNAL_SAMSUNG_STOCK_PRICE_URL).build().toUri();

    ResponseEntity<ExternalStockPriceDto> response =
        restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    if (!HttpStatus.OK.equals(response.getStatusCode())) {
      throw new NoSuchElementException("Error getting SAMSUNG stock price");
    }

    StockPriceDto stockPriceDto = StockPriceDto.from(response.getBody());
    StockEntity.from(stockPriceDto).forEach(stockRepository::save);
    return stockPriceDto;
  }
}
