package com.rogerli.springmall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderRequest {

    @NotEmpty
    private List<BuyItem> buyItemList;

}
