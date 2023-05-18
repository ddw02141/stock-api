package com.example.stock_api.model.dto;

import com.example.stock_api.model.Chart;
import com.example.stock_api.model.Indicators;
import com.example.stock_api.model.Quote;
import com.example.stock_api.model.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceDto {
  private List<Integer> high;
  private List<Integer> low;
  private List<Integer> open;
  private List<Integer> close;
  private List<Integer> volume;
  private List<Integer> timestamp;
  @JsonIgnore @EqualsAndHashCode.Exclude private int maxLen;

  public static StockPriceDto from(final ExternalStockPriceDto externalStockPriceDto) {
    Optional<Result> result =
        Optional.ofNullable(externalStockPriceDto)
            .map(ExternalStockPriceDto::getChart)
            .map(Chart::getResult)
            .filter(CollectionUtils::isNotEmpty)
            .map(results -> results.get(0));

    if (result.isEmpty()) {
      return null;
    }

    Optional<Quote> quote =
        result
            .map(Result::getIndicators)
            .map(Indicators::getQuote)
            .filter(CollectionUtils::isNotEmpty)
            .map(quotes -> quotes.get(0));

    if (quote.isEmpty()) {
      return null;
    }

    return StockPriceDto.builder()
        .high(quote.get().getHigh())
        .low(quote.get().getLow())
        .open(quote.get().getOpen())
        .close(quote.get().getClose())
        .volume(quote.get().getVolume())
        .timestamp(result.get().getTimestamp())
        .maxLen(getMaxLen(quote.get(), result.get().getTimestamp()))
        .build();
  }

  private static int getMaxLen(Quote quote, List<Integer> timestamp) {
    return Collections.max(
        Arrays.asList(
            quote.getHigh().size(),
            quote.getLow().size(),
            quote.getOpen().size(),
            quote.getClose().size(),
            quote.getVolume().size(),
            timestamp.size()));
  }
}
