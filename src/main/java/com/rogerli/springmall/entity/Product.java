package com.rogerli.springmall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String category;
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private Date createdDate;
    private Date lastModifiedDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItem;

}
