package com.rogerli.springmall.dto;

import com.rogerli.springmall.model.OrderView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateOrderRequest {
    @NotEmpty
    private Integer orderId;
    @NotEmpty
    private Integer quantity;
    @NotEmpty
    private Integer productId;

}
