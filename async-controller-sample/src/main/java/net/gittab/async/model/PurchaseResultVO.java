package net.gittab.async.model;

import lombok.Data;

/**
 * PurchaseResultVO.
 *
 * @author rookiedev
 * @date 2020/8/23 02:59
 **/
@Data
public class PurchaseResultVO {

    private Long productId;

    private Boolean success;

    private String errorMsg;

}
