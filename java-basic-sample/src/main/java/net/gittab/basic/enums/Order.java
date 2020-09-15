package net.gittab.basic.enums;

import lombok.Data;

/**
 * Order.
 *
 * @author rookiedev
 * @date 2020/9/13 16:54
 **/
@Data
public class Order {

    private Long id;

    private Long userId;

    private Integer amount;

    /**
     * 0: 待支付，1:已支付，2:已退款.
     */
    // private Integer status;

    private OrderStatus status;

}
