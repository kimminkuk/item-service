package hello.itemservice.domain.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long id);
    List<Item> findAll();
    Optional<Item> findByName(String itemName);
    void update(Long itemId, Item updateParam);
}
