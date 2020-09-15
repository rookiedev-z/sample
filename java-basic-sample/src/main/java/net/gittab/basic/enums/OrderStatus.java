package net.gittab.basic.enums;

/**
 * OrderStatus.
 *
 * @author rookiedev
 * @date 2020/9/13 16:54
 **/
public enum OrderStatus {

    TO_PAY(0),

    PAID(1),

    REFUNDED(2);

    private int code;

    OrderStatus(int code){
        this.code = code;
    }
}
