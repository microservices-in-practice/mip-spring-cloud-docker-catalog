package com.capgemini.mip.catalog.controller;


import com.capgemini.mip.catalog.service.ItemTO;
import com.capgemini.mip.catalog.service.ItemTOBuilder;
import com.capgemini.mip.catalog.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ItemController.class)
public class ItemControllerUnitTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  ItemService itemService;

  @Test
  public void shouldGetItem() throws Exception {
    ItemTO mockItem = ItemTOBuilder.item()
      .withCode("TE01")
      .withName("Test Training (TE01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .withId(9001L)
      .withVersion(1)
      .build();

    given(itemService.findByCode(mockItem.getCode())).willReturn(mockItem);

    this.mvc.perform(get("/items/" + mockItem.getCode()).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("id", is(mockItem.getId().intValue())))
      .andExpect(jsonPath("code", is(mockItem.getCode())))
      .andExpect(jsonPath("name", is(mockItem.getName())))
      .andExpect(jsonPath("description", is(mockItem.getDescription())))
      .andExpect(jsonPath("price", is(mockItem.getPrice())))
      .andExpect(content().json(toJson(mockItem)));

    verify(this.itemService, times(1)).findByCode(mockItem.getCode());
    verifyNoMoreInteractions(this.itemService);
  }

  @Test
  public void shouldGetItems() throws Exception {
    ItemTO mockItem = ItemTOBuilder.item()
      .withCode("TE01")
      .withName("Test Training (TE01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .withId(9001L)
      .withVersion(1)
      .build();

    given(itemService.findAll()).willReturn(Arrays.asList(mockItem));

    this.mvc.perform(get("/items/").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id", is(mockItem.getId().intValue())))
      .andExpect(jsonPath("$[0].code", is(mockItem.getCode())))
      .andExpect(jsonPath("$[0].name", is(mockItem.getName())))
      .andExpect(jsonPath("$[0].description", is(mockItem.getDescription())))
      .andExpect(jsonPath("$[0].price", is(mockItem.getPrice())))
      .andExpect(content().json(toJson(Arrays.asList(mockItem))));

    verify(this.itemService, times(1)).findAll();
    verifyNoMoreInteractions(this.itemService);
  }

  @Test
  public void shouldCreateItem() throws Exception {
    ItemTO item = ItemTOBuilder.item()
      .withCode("TE01")
      .withName("Test Training (TE01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();

    ItemTO updatedItem = ItemTOBuilder.item()
      .withCode(item.getCode())
      .withName(item.getName())
      .withDescription(item.getDescription())
      .withPrice(item.getPrice())
      .withId(9001L)
      .withVersion(1)
      .build();

    given(this.itemService.saveItem(any(ItemTO.class))).willReturn(updatedItem);

    this.mvc.perform(post("/items/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(toJson(item)))
      .andExpect(status().isOk())
      .andExpect(content().json(toJson(updatedItem)));
  }

  private static String toJson(Object object) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(object);
  }

}



