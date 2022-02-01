package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemMemoryRepository;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemService;
import hello.itemservice.repository.JdbcItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //생성자 생성 생략
public class BasicItemController {
    private final ItemService itemService;
    //private final ItemMemoryRepository itemMemoryRepository;
    //private final JdbcItemRepository jdbcItemRepository;
    //private final JdbcItemRepository itemMemoryRepository;

    @GetMapping
    public String items(Model model) {
        //List<Item> items = itemMemoryRepository.findAll();
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        //Item item = itemMemoryRepository.findById(itemId).get();
        Item item = itemService.findOne(itemId).get();
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    //@PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemMemoryRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }

//    //@PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item) {
//        itemMemoryRepository.save(item);
//        return "basic/item";
//    }
//
//    //@PostMapping("/add")
//    public String addItemV5(@ModelAttribute("item") Item item) {
//        itemMemoryRepository.save(item);
//        return "redirect:/basic/items/" + item.getId();
//    }

    @PostMapping("/add")
    public String addItemV6(@ModelAttribute("item") Item item,
                            RedirectAttributes redirectAttributes) {
        //Item savedItem = itemMemoryRepository.save(item);
        Item savedItem = itemService.saveItemService(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
//
//        Item savedItem2 = jdbcItemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem2.getId());
//        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long itemId, Model model) {
//        Item item = itemMemoryRepository.findById(itemId).get();
//        model.addAttribute("item", item);
//        return "basic/editForm";
//    }
//
//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
//        itemMemoryRepository.update(itemId, item);
//        return "redirect:/basic/items/{itemId}";
//    }

    @PostConstruct
    public void init() {

        //itemMemoryRepository.save(new Item("itemA", 10000, 10));
        //itemMemoryRepository.save(new Item("itemB", 20000, 20));

        //DB에 저장된 데이터들 불러오기.
    }

//    @Autowired //생략 가능 생성자 하나만 있으면
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

}
