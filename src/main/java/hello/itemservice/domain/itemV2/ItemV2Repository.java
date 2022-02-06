package hello.itemservice.domain.itemV2;

import java.util.List;
import java.util.Optional;

public interface ItemV2Repository {
    ItemV2 save(ItemV2 item);
    Optional<ItemV2> findById(Long id);
    List<ItemV2> findAll();

    Optional<ItemV2> findByName(String itemName);
    void update(Long itemId, ItemV2 updateParam);
}
