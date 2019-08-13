package com.capgemini.mip.catalog.repository;

import com.capgemini.mip.catalog.domain.ItemEntity;
import com.capgemini.mip.catalog.domain.ItemEntityBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.capgemini.mip.catalog.testdata.TestdataProvider.provideItemEntity;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ItemRepository itemRepository;


  @Test
  public void shouldFindItem() {

    // given
    ItemEntity savedItem = entityManager.persist(provideItemEntity());

    // when
    ItemEntity foundItem = itemRepository.getOne(savedItem.getId());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }


  @Test
  public void shouldSaveItem() {

    // given

    // when
    ItemEntity savedItem = itemRepository.save(provideItemEntity());

    // then
    ItemEntity foundItem = entityManager.find(ItemEntity.class, savedItem.getId());
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

  @Test
  public void shouldUpdateItem() {

    // given
    ItemEntity savedItem = itemRepository.save(provideItemEntity());
    ItemEntity itemToUpdate = ItemEntityBuilder.itemEntity()
      .withCode(savedItem.getCode())
      .withDescription(savedItem.getDescription())
      .withName(savedItem.getName())
      .withPrice(savedItem.getPrice() + 100)
      .withId(savedItem.getId())
      .withVersion(savedItem.getVersion())
      .build();

    // when
    ItemEntity updatedItem = itemRepository.save(itemToUpdate);

    // then
    ItemEntity foundItem = entityManager.find(ItemEntity.class, savedItem.getId());
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(itemToUpdate.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(itemToUpdate.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(itemToUpdate.getPrice());
    assertThat(foundItem.getName()).isEqualTo(itemToUpdate.getName());
  }

  @Test
  public void shouldFindByCode() {

    // given
    ItemEntity savedItem = entityManager.persist(provideItemEntity());

    // when
    ItemEntity foundItem = itemRepository.findByCode(savedItem.getCode());

    // then
    assertThat(foundItem).isNotNull();
    assertThat(foundItem.getCode()).isEqualTo(savedItem.getCode());
    assertThat(foundItem.getDescription()).isEqualTo(savedItem.getDescription());
    assertThat(foundItem.getPrice()).isEqualTo(savedItem.getPrice());
    assertThat(foundItem.getName()).isEqualTo(savedItem.getName());
  }

}
