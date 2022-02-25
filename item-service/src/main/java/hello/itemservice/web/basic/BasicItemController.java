package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * ModelAttribute 속성으로 Model에 담는다
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능
        return "basic/item";
    }

    /**
     * ModelAttribute 클래스의 첫 글자만 소문자로 바뀌어서 Model에 담는다
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능
        return "basic/item";
    }

    /**
     * ModelAttribute 생략 가능
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능
        return "basic/item";
    }

    /**
     * PRG 패턴 적용
     */
//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item) {

        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * PRG 패턴 적용
     * RedirectAttributes 사용
     * URL 인코딩 처리
     * 경로 변수, 쿼리 파라미터 설정 가능
     * /basic/items/1?status=true
     */
    @PostMapping("/add")
    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute Item item) {

        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
