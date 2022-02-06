package hello.itemservice.domain.itemV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ItemV2Service {
    private final ItemV2Repository itemV2Repository;

    @Autowired
    public ItemV2Service(ItemV2Repository itemV2Repository) {
        this.itemV2Repository = itemV2Repository;
    }

    public ItemV2 saveItemService(ItemV2 item) {
        validateDuplicateItem(item);
        return itemV2Repository.save(item);
    }

    public Long join(ItemV2 item) {
        validateDuplicateItem(item);
        itemV2Repository.save(item);
        return item.getId();
    }

    private void validateDuplicateItem(ItemV2 item) {
        itemV2Repository.findByName(item.getItemName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이템입니다.");
                });
    }

    public List<ItemV2> findItems() {
        return itemV2Repository.findAll();
    }

    public Optional<ItemV2> findOne(Long memberId) {
        return itemV2Repository.findById(memberId);
    }

    public void updateItemService(Long id, ItemV2 updateParam) {
        itemV2Repository.update(id, updateParam);
        return;
    }
}
