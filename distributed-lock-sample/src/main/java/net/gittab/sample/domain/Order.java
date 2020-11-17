package net.gittab.sample.domain;

import lombok.Data;
import lombok.ToString;

/**
 * Order.
 *
 * @author rookiedev 2020/10/27 15:36
 **/
@Data
@ToString
public class Order {

    private String id;

    private String userId;

    private String payId;

}
