package net.gittab.async.model;

import lombok.Data;

/**
 * PayResponse.
 *
 * @author rookiedev
 * @date 2020/8/23 02:21
 **/
@Data
public class PayResponse {

    private Boolean success;

    private Long payId;

    private OrderDTO orderDTO;

    private String errorMsg;
}
