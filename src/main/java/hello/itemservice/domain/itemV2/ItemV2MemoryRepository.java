package hello.itemservice.domain.itemV2;

import java.util.*;

public class ItemV2MemoryRepository implements ItemV2Repository {
    private static final Map<Long, ItemV2> store = new HashMap<>(); //static 여러 개 일때는 해쉬맵 쓰면 안된다.
    //ConcurrentHashMap;

    private static long sequence = 0L; //static

    @Override
    public ItemV2 save(ItemV2 item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<ItemV2> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<ItemV2> findByName(String itemName) {
        return store.values().stream()
                .filter(findItem -> findItem.getItemName().equals(itemName))
                .findAny();
    }
//    @Override
//    public Item findById(Long id) {
//        return store.get(id);
//    }

    @Override
    public List<ItemV2> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long itemId, ItemV2 updateParam) {
        ItemV2 findItem = findById(itemId).get();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
