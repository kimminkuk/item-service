package hello.itemservice.domain.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItemService(Item item) {
        validateDuplicateItem(item);
        return itemRepository.save(item);
    }

    public Long join(Item item) {
        validateDuplicateItem(item);
        itemRepository.save(item);
        return item.getId();
    }

    private void validateDuplicateItem(Item item) {
        itemRepository.findByName(item.getItemName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이템입니다.");
                });
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findOne(Long memberId) {
        return itemRepository.findById(memberId);
    }

    public void updateItemService(Long id, Item updateParam) {
        itemRepository.update(id, updateParam);
        return;
    }
}
