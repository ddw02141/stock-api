package com.example.stock_api.model.entity;

import com.example.stock_api.model.dto.StockPriceDto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Entity(name = "stock")
@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {
  @Id @GeneratedValue private Long id;

  private Integer high;
  private Integer low;
  private Integer open;
  private Integer close;
  private Integer volume;
  private Integer timestamp;

  public static List<StockEntity> from(final StockPriceDto stockPriceDto) {
    if ( Objects.isNull(stockPriceDto) ) {
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

  private static Integer getElement(List<Integer> list, int idx) {
    return list.size() > idx ? list.get(idx) : null;
  }
}
