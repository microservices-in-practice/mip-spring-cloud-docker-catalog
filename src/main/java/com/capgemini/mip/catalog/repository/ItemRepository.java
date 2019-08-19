package com.capgemini.mip.catalog.repository;

import com.capgemini.mip.catalog.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  public Item findByCode(String code);

}
