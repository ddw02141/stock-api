package com.example.stock_api.repository;

import com.example.stock_api.model.entity.StockEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockRepository {
  private final EntityManager em;

  public void save(final StockEntity stockEntity) {
    // TODO: 모든 값이 같으면 넘어가거나 overwrite 하는 로직
    em.persist(stockEntity);
  }
}
