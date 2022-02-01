package hello.itemservice.domain.itemNFT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemNFTMemoryRepository implements ItemNFTRepository {
    private static Map<Long, ItemNFT> store = new ConcurrentHashMap<>(); //Case by Multi-thread
    //private static Map<Long, ItemNFT> storeTest = new HashMap<>(); //Case by Test

    private static Long sequence = 0L;

    @Override
    public ItemNFT save(ItemNFT itemNFT) {
        itemNFT.setId(++sequence);
        store.put(sequence, itemNFT);
        return itemNFT;
    }

    @Override
    public ItemNFT findById(Long itemId) {
        ItemNFT findItem = store.get(itemId);
        return findItem;
    }

    @Override
    public ItemNFT findAll() {
        return null;
    }

    @Override
    public ItemNFT update(Long itemId, ItemNFT updateParam) {
        return null;
    }

    @Override
    public void clearStore() {

    }
}
