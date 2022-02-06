package hello.itemservice.web.basic;

import hello.itemservice.domain.itemV2.ItemV2;
import hello.itemservice.domain.itemV2.ItemV2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/basicv2/items")
@RequiredArgsConstructor //생성자 생성 생략
public class BasicItemV2Controller {
    private final ItemV2Service itemV2Service;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/itemImageBox";
    //public static String uploadDirectory = System.getProperty("user.dir") + "/Users/mkkim/Desktop/webImg";
    //public static String uploadDirectory = "/Users/mkkim/Desktop/webImg";
    public static String uploadDirectoryTemp = "itemImageBox";

    @GetMapping
    public String items(Model model) {
        List<ItemV2> items = itemV2Service.findItems();
        model.addAttribute("items", items);
        return "basicv2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemV2 item = itemV2Service.findOne(itemId).get();
        model.addAttribute("item", item);
        return "basicv2/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basicv2/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") ItemV2 item,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            @RequestParam("files") MultipartFile[] files) {
        System.out.println("BasicItemV2Controller.addItem");
        String temp = uploadFile(model, files);
        item.setStoFileName(temp);
        item.setFilePath(uploadDirectoryTemp);
        System.out.println("fileName:" + temp);

        ItemV2 savedItem = itemV2Service.saveItemService(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicv2/items/{itemId}";
    }
//
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long itemId, Model model) {
//        Item item = itemService.findOne(itemId).get();
//        model.addAttribute("item", item);
//        return "basicV2/editForm";
//    }
//
//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
//        itemService.updateItemService(itemId, item);
//        return "redirect:/basicV2/items/{itemId}";
//    }
//
//    @PostConstruct
//    public void init() {
//        //DB에 저장된 데이터들 불러오기.
//    }
//
    /**
     * Image Upload Test
     */
    public String uploadFile(Model model, @RequestParam("files") MultipartFile[] files) {
        StringBuilder fileNames = new StringBuilder();
        String fileNameTest = "";
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename() + " ");
            fileNameTest = file.getOriginalFilename();
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNameTest;
    }
}
