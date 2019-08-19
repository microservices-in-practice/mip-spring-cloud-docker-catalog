package com.capgemini.mip.catalog.service;

import com.capgemini.mip.catalog.domain.Item;
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


  public ItemTO saveItem(ItemTO itemTO) {
    Item item = beanMapper.map(itemTO, Item.class);
    item = itemRepository.save(item);
    return beanMapper.map(item, ItemTO.class);
  }

  public void deleteItem(long id) {
    itemRepository.deleteById(id);
  }

  public ItemTO findById(long id) {
    return Optional.of(itemRepository.getOne(id))
      .map(item -> beanMapper.map(item, ItemTO.class))
      .get();
  }

  public ItemTO findByCode(String code) {
    return Optional.ofNullable(itemRepository.findByCode(code))
      .map(item -> beanMapper.map(item, ItemTO.class))
      .orElse(null);
  }

  public List<ItemTO> findAll() {
    return itemRepository.findAll().stream()
      .map(item -> beanMapper.map(item, ItemTO.class))
      .collect(Collectors.toList());
  }


}
