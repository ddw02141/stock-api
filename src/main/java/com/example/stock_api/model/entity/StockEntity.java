package com.example.stock_api.model.entity;

import com.example.stock_api.model.dto.StockPriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Entity
@Table(name = "stock")
@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {
  @Id private Integer timestamp;
  private Integer high;
  private Integer low;
  private Integer open;
  private Integer close;
  private Integer volume;

  public static List<StockEntity> from(final StockPriceDto stockPriceDto) {
    if (Objects.isNull(stockPriceDto)) {
      return Collections.emptyList();
    }

    return IntStream.range(0, stockPriceDto.getMaxLen())
        .mapToObj(
            idx ->
                StockEntity.builder()
                    .high(getElement(stockPriceDto.getHigh(), idx))
                    .low(getElement(stockPriceDto.getLow(), idx))
                    .open(getElement(stockPriceDto.getOpen(), idx))
                    .close(getElement(stockPriceDto.getClose(), idx))
                    .volume(getElement(stockPriceDto.getVolume(), idx))
                    .timestamp(getElement(stockPriceDto.getTimestamp(), idx))
                    .build())
        .toList();
  }

  private static <T> T getElement(List<T> list, int idx) {
    return list.size() > idx ? list.get(idx) : null;
  }
}
