package com.capgemini.mip.catalog.controller;

import com.capgemini.mip.catalog.service.ItemTO;
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
  public ResponseEntity<ItemTO> getItem(@PathVariable String code) {
    return Optional.ofNullable(itemService.findByCode(code))
      .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping()
  public ResponseEntity<List<ItemTO>> getItems() {
    return Optional.ofNullable(itemService.findAll())
      .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping()
  public ResponseEntity<ItemTO> saveOrUpdate(@Validated  @RequestBody ItemTO item) {
    return Optional.ofNullable(itemService.saveItem(item))
      .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
