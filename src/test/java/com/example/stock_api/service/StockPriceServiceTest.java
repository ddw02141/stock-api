package com.example.stock_api.service;

import com.example.stock_api.model.dto.ExternalStockPriceDto;
import com.example.stock_api.model.dto.StockPriceDto;
import com.example.stock_api.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

import static com.example.stock_api.TestUtil.toObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class StockPriceServiceTest {

  @Mock private RestTemplate restTemplate;
  @Mock private StockRepository stockRepository;
  @InjectMocks private StockPriceService stockPriceService;

  @Test
  void getAndUpdateStockPriceShouldThrowException() {
    // given
    when(restTemplate.exchange(any(), any(), any(), any(ParameterizedTypeReference.class)))
        .thenReturn(ResponseEntity.badRequest().build());

    // when, then
    assertThrows(NoSuchElementException.class, () -> stockPriceService.getAndUpdateStockPrice());
  }

  @Test
  void getAndUpdateStockPriceShouldSuccess() {
    // given
    ClassLoader cl = this.getClass().getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
    Resource resource = resolver.getResource("classpath:external_stock_price_dto.json");
    ExternalStockPriceDto externalStockPriceDto = toObject(resource, ExternalStockPriceDto.class);
    resource = resolver.getResource("classpath:stock_price_dto.json");
    StockPriceDto expected = toObject(resource, StockPriceDto.class);
    when(restTemplate.exchange(any(), any(), any(), any(ParameterizedTypeReference.class)))
        .thenReturn(ResponseEntity.ok(externalStockPriceDto));
    doNothing().when(stockRepository).save(any());

    // when
    StockPriceDto actual = stockPriceService.getAndUpdateStockPrice();

    // then
    assertEquals(expected, actual);
  }
}
