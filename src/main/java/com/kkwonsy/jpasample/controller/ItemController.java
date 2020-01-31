package com.kkwonsy.jpasample.controller;

import com.kkwonsy.jpasample.domain.item.Book;
import com.kkwonsy.jpasample.domain.item.Item;
import com.kkwonsy.jpasample.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(BookForm form) {
        Book book = new Book();
        // tip createBook 처럼 만드는게 나음 Setter는 날릴 것
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        // tip 실무에서는 Member entity를 그대로 뿌리기보다 MemberForm 같은걸 쓰는게 맞다
        // tip API를 만들때에는 절대 엔티티를 외부로 반환해서는 안된다. (내부 스펙이기 때문에, 그리고 password가 포함되어 있을 수도 있다)
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }


    // tip 일단 form을 먼저 보여줘야 하니까~
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        // tip 다 입력하기 귀찮음... ModelMapper같은 라이브러리들이 있음
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) { // tip form에서 id가 와서 PathVariable을 없앰
        Book book = new Book();
        // tip Id가 조작되서 넘어오는 경우 위험함, 취약점, 따라서 뒷단에서 유저가 이 아이템에 권한이 있는지 체크를 해줘야 한다
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        // tip saveItem은 repository의 save로 넘어감
        // tip id가 있기 때문에 merge를 탄다. merge가 뭐징?? (실무에서 쓸일이 별로 없다는데?)

        itemService.saveItem(book);
        return "redirect:/items";
    }
}
