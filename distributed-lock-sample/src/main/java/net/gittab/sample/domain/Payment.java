package net.gittab.sample.domain;

import lombok.Data;
import lombok.ToString;

/**
 * Transaction.
 *
 * @author rookiedev 2020/10/27 15:38
 **/
@Data
@ToString
public class Payment {

    private String id;

    private String userId;

    private String txnId;
}
