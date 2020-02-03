package com.kkwonsy.jpasample.service;

import com.kkwonsy.jpasample.domain.item.Item;
import com.kkwonsy.jpasample.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }


    // tip 준영속성 엔티티를 update하는 방법! dirty checking
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);

        // tip 사실 set을 막 깔지말고 의미있는 메서드를 만들어서 쓰자 (entity에다가)
        // 너무 많으면 DTO 하나 만들자~
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }
}
