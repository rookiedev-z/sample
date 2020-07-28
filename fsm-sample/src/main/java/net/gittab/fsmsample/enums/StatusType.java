package net.gittab.fsmsample.enums;

/**
 * @author xiaohua zhou
 */

public enum StatusType {

    /**
     * 无类型
     */
    NONE(-1,"none"),

    /**
     * 待处理
     */
    TODO(0, "todo"),

    /**
     * 处理中
     */
    DOING(1, "doing"),

    /**
     * 待测试
     */
    TO_QA(2, "to qa"),

    /**
     * 待发布
     */
    TO_DEPLOY(3, "to deploy"),

    /**
     * 已完成
     */
    DONE(4, "done");

    private Integer value;

    private String code;

    StatusType(Integer value, String code){
        this.value = value;
        this.code = code;
    }
}