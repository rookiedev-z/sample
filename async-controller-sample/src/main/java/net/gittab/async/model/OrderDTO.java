package net.gittab.async.model;

import lombok.Data;

/**
 * OrderDTO.
 *
 * @author rookiedev
 * @date 2020/8/22 15:01
 **/
@Data
public class OrderDTO {

    private Long id;

    private Long userId;

    private Long productId;

    private Long amount;

}
