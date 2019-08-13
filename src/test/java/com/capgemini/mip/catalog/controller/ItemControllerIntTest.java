package com.capgemini.mip.catalog.controller;


import com.capgemini.mip.catalog.service.Item;
import com.capgemini.mip.catalog.service.ItemBuilder;
import com.capgemini.mip.catalog.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class ItemControllerIntTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  private ItemService itemService;

  @Test
  public void shouldGetItem() throws Exception {
    String itemJson = "{\"id\":100,\"version\":0,\"code\":\"TE01\",\"name\":\"Test Training (TE01)\",\"price\":1000.0,\"description\":\"Very interesting training\"}";

    Item item = ItemBuilder.item()
      .withCode("TE01")
      .withName("Test Training (TE01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();

    Item savedItem = itemService.saveItem(item);

    this.mvc.perform(get("/items/" + savedItem.getCode()).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("id", is(savedItem.getId().intValue())))
      .andExpect(jsonPath("code", is(savedItem.getCode())))
      .andExpect(jsonPath("name", is(savedItem.getName())))
      .andExpect(jsonPath("description", is(savedItem.getDescription())))
      .andExpect(jsonPath("price", is(savedItem.getPrice())))
      .andExpect(content().json(toJson(savedItem)));
  }

  @Test
  public void shouldCreateItem() throws Exception {
    Item item = ItemBuilder.item()
      .withCode("TE02")
      .withName("Test Training (TE02)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();

    this.mvc.perform(post("/items/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(toJson(item)))
      .andExpect(status().isOk());
  }

  private static String toJson(Object object) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(object);
  }

}



