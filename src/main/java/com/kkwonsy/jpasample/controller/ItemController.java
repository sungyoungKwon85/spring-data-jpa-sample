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
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {
        // tip Id가 조작되서 넘어오는 경우 위험함, 취약점, 따라서 뒷단에서 유저가 이 아이템에 권한이 있는지 체크를 해줘야 한다
        // tip Controller에서 만들지 말자
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());


        // tip saveItem은 repository의 save로 넘어감
        // tip id가 있기 때문에 merge를 탄다. merge가 뭐징?? (실무에서 쓸일이 별로 없다는데?)

        // 해당 객체는 이미 DB에 한번 저장되어서 식별자가 존재한다. -> 준영속 엔티티다
        // 준영속 엔티티는 영속성 컨텍스트가 더는 '관리하지 않는' 엔티티를 말한다
        // book 객체는 임의로 만들어낸 엔티티인데 기존 식별자를 가지고 있어서 준영속 엔티티이다

        // 준영속 엔티티를 수정하는 방법은 두가지.
        // 1. 변경 감지기능(그래서 set*("....") 로 했다고해서 dirty checking이 되지 않는다. 따로 해줄게 있음)
        //     ItemService.updateItem 참고
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        // 2. merge 사용
        // 1차캐시에서 찾음 -> 없으면 DB에서 찾음 -> 1차캐시에 넣음 -> 값 바꿈 -> 반환
        // 주의점: 반환된 객체가 영속성 컨텍스트로 안들어옴.
//        itemService.saveItem(book);

        // 변경감지를 사용하면 원하는거만 바꿀 수 있는데
        // 병합은 모든 속성이 바뀜. 'null'로 바뀔 위험이 있음
        // -> dirty checking을 사용하자~~~


        return "redirect:/items";
    }
}
