package hello.itemservice.domain.itemNFT;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemNFT {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemNFT() {
    }

    public ItemNFT(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
