package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        Item item = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        Item item = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updatedParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId, updatedParam);

        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updatedParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updatedParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updatedParam.getQuantity());
    }

}