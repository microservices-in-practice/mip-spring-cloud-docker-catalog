package com.capgemini.mip.catalog.service;

import com.capgemini.mip.catalog.config.ConfigProperties;
import com.capgemini.mip.catalog.domain.ItemEntity;
import com.capgemini.mip.catalog.repository.ItemRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private DozerBeanMapper beanMapper;

  @Autowired
  private ConfigProperties properties;

  public Item saveItem(Item item) {
    ItemEntity itemEntity = beanMapper.map(item, ItemEntity.class);
    itemEntity = itemRepository.save(itemEntity);
    return beanMapper.map(itemEntity, Item.class);
  }

  public void deleteItem(long id) {
    itemRepository.deleteById(id);
  }

  public Item findById(long id) {
    return Optional.of(itemRepository.getOne(id))
      .map(itemEntity -> beanMapper.map(itemEntity, Item.class))
      .map(this::applyDiscountRate)
      .get();
  }

  public Item findByCode(String code) {
    return Optional.ofNullable(itemRepository.findByCode(code))
      .map(itemEntity -> beanMapper.map(itemEntity, Item.class))
      .map(this::applyDiscountRate)
      .orElse(null);
  }

  public List<Item> findAll() {
    return itemRepository.findAll().stream()
      .map(itemEntity -> beanMapper.map(itemEntity, Item.class))
      .map(this::applyDiscountRate)
      .collect(Collectors.toList());
  }

  private Item applyDiscountRate(Item item) {
    item.setPrice(item.getPrice() * (1.0 - properties.getDiscount()));
    return item;
  }

}
