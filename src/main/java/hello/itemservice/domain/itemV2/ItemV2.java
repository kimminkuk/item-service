package hello.itemservice.domain.itemV2;

import lombok.Data;

@Data
public class ItemV2 {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private String oriFileName;
    private String stoFileName;
    private String filePath;
    private long fileSize;

    public ItemV2() {
    }

    public ItemV2(String itemName, Integer price, Integer quantity,String filePath, String stoFileName) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.filePath = filePath;
        this.stoFileName = stoFileName;
    }
}
