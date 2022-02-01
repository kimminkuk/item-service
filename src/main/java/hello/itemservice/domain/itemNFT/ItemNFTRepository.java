package hello.itemservice.domain.itemNFT;

public interface ItemNFTRepository {
    ItemNFT save(ItemNFT itemNFT);
    ItemNFT findById(Long itemId);
    ItemNFT findAll();
    ItemNFT update(Long itemId, ItemNFT updateParam);

    void clearStore();
}
