package net.gittab.async.model;

import lombok.Data;

/**
 * PayRequest.
 *
 * @author rookiedev
 * @date 2020/8/23 02:21
 **/
@Data
public class PayRequest {

    private Long amount;

    private String gateway;

    private Long profileId;

    private OrderDTO orderDTO;
}
