package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //생성자 생성 생략
public class BasicItemController {
    private final ItemService itemService;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemService.findOne(itemId).get();
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV6(@ModelAttribute("item") Item item,
//                            RedirectAttributes redirectAttributes) {
//
//        Item savedItem = itemService.saveItemService(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/basic/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV7(@ModelAttribute("item") Item item,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            @RequestParam("files") MultipartFile[] files) {
        uploadFile(model, files);
        Item savedItem = itemService.saveItemService(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findOne(itemId).get();
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemService.updateItemService(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        //DB에 저장된 데이터들 불러오기.
    }

    /**
     * Image Upload Test
     */
    //public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    //public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/templates/uploads";
    //public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/templates/itemImageBox";
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/itemImageBox";

    @GetMapping("/view")
    public String uploadPage(Model model) {
        return "basic/uploadview";
    }

    @RequestMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename() + " ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "Successfully uploads files " + fileNames.toString());
        return "basic/uploadstatusview";
    }

    public void uploadFile(Model model, @RequestParam("files") MultipartFile[] files) {
        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename() + " ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
