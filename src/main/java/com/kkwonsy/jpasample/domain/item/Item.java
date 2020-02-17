package com.kkwonsy.jpasample.domain.item;

import com.kkwonsy.jpasample.domain.Category;
import com.kkwonsy.jpasample.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 걍 글로벌하게 default_batch_fetch_size 쓰는게 편함
//@BatchSize(size = 100)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter // tip 변경이 필요하면 아래처럼 비지니스 메서드를 만들어야 한다
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // tip 비지니스 로직
    // 엔티티 안에 넣음으로써 좀더 객체지향적이다 = 응집도

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) throws NotEnoughStockException {
        int restStock = this.stockQuantity -= quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
