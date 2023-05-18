package com.example.stock_api.controller;

import com.example.stock_api.model.dto.StockPriceDto;
import com.example.stock_api.service.StockPriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.stock_api.TestUtil.toObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StockPriceController.class)
class StockPriceControllerTest {

  private static final String STOCK_PRICE_URL = "/stock-price";

  @Autowired private MockMvc mockMvc;
  @MockBean private StockPriceService stockPriceService;

  @Test
  void getStockPriceShouldSuccess() throws Exception {
    // given
    ClassLoader cl = this.getClass().getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
    Resource resource = resolver.getResource("classpath:stock_price_dto.json");
    StockPriceDto stockPriceDto = toObject(resource, StockPriceDto.class);
    when(stockPriceService.getAndUpdateStockPrice()).thenReturn(stockPriceDto);

    // when
    mockMvc
        .perform(get(STOCK_PRICE_URL))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.timestamp[0]").value(1683849600))
        .andExpect(jsonPath("$.close[0]").value(64100))
        .andExpect(jsonPath("$.high[0]").value(64600))
        .andExpect(jsonPath("$.open[0]").value(63700))
        .andExpect(jsonPath("$.low[0]").value(63600))
        .andExpect(jsonPath("$.volume[0]").value(8693913));
  }
}
