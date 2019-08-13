package com.capgemini.mip.catalog.controller;

import com.capgemini.mip.catalog.service.Item;
import com.capgemini.mip.catalog.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

  @Autowired
  private ItemService itemService;

  @GetMapping(path = "/{code}")
  public ResponseEntity<Item> getItem(@PathVariable String code) {
    return Optional.ofNullable(itemService.findByCode(code))
      .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping()
  public ResponseEntity<List<Item>> getItems() {
    return Optional.ofNullable(itemService.findAll())
      .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping()
  public ResponseEntity<Item> saveOrUpdate(@Validated  @RequestBody Item item) {
    return Optional.ofNullable(itemService.saveItem(item))
      .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
