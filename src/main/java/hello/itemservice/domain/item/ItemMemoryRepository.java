package hello.itemservice.domain.item;
import java.util.*;

public class ItemMemoryRepository implements ItemRepository{
    private static final Map<Long, Item> store = new HashMap<>(); //static 여러 개 일때는 해쉬맵 쓰면 안된다.
    //ConcurrentHashMap;

    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Item> findByName(String itemName) {
        return store.values().stream()
                .filter(findItem -> findItem.getItemName().equals(itemName))
                .findAny();
    }
//    @Override
//    public Item findById(Long id) {
//        return store.get(id);
//    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId).get();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
