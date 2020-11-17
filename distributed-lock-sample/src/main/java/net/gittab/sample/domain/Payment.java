package net.gittab.sample.domain;

import lombok.Data;

/**
 * Transaction.
 *
 * @author rookiedev 2020/10/27 15:38
 **/
@Data
public class Transaction {

    private String id;

    private String userId;

    private String txnId;
}
