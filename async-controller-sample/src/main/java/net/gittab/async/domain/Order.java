package net.gittab.async.domain;

import lombok.Data;

/**
 * Order.
 *
 * @author rookiedev
 * @date 2020/8/23 02:32
 **/
@Data
public class Order {

    private Long id;

    private Long productId;

    private Long amount;

    private Integer status;

    private Long userId;

    private Long payId;

    private String errorMsg;
}
