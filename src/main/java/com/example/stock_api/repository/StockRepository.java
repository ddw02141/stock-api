package com.example.stock_api.repository;

import com.example.stock_api.model.entity.StockEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class StockRepository {
  private final EntityManager em;

  public void save(final StockEntity stockEntity) {
    StockEntity existing = findOne(stockEntity.getTimestamp());
    if (Objects.isNull(existing)) {
      em.persist(stockEntity);
    } else {
      em.merge(stockEntity);
    }
  }

  private StockEntity findOne(final Integer timestamp) {
    return em.find(StockEntity.class, timestamp);
  }
}
