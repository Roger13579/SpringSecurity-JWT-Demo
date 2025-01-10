package com.rogerli.springmall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderRequest {

    @NotEmpty
    private List<BuyItem> buyItemList;

}
