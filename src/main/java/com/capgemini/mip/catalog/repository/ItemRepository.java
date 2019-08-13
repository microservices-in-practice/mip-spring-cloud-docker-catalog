package com.capgemini.mip.catalog.repository;

import com.capgemini.mip.catalog.domain.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

  public ItemEntity findByCode(String code);

}
