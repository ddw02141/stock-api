package com.example.stock_api.service;

import com.example.stock_api.model.dto.ExternalStockPriceDto;
import com.example.stock_api.model.dto.StockPriceDto;
import com.example.stock_api.model.entity.StockEntity;
import com.example.stock_api.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@Transactional(readOnly = true)
public class StockPriceService {

  private final WebClient webClient;
  private final StockRepository stockRepository;
  private static final String EXTERNAL_SAMSUNG_STOCK_PRICE_URL =
      "https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d";

  public StockPriceService(final StockRepository stockRepository) {
    this.webClient = WebClient.create(EXTERNAL_SAMSUNG_STOCK_PRICE_URL);
    this.stockRepository = stockRepository;
  }

  @Transactional
  public Mono<StockPriceDto> getAndUpdateStockPrice() {
    final URI url =
        UriComponentsBuilder.fromHttpUrl(EXTERNAL_SAMSUNG_STOCK_PRICE_URL).build().toUri();

    return webClient
        .get()
        .uri(url)
        .exchangeToMono(
            response -> {
              if (response.statusCode().equals(HttpStatus.OK)) {
                return response
                    .bodyToMono(ExternalStockPriceDto.class)
                    .mapNotNull(
                        externalStockPriceDto -> {
                          StockPriceDto stockPriceDto = StockPriceDto.from(externalStockPriceDto);
                          StockEntity.from(stockPriceDto).forEach(stockRepository::save);
                          return stockPriceDto;
                        });
              } else {
                throw new RuntimeException("Error getting samsung stock price");
              }
            });
  }
}
