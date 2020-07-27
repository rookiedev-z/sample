package net.gittab.fsmsample.enums;

import lombok.Getter;

@Getter
public enum TransformType {

    /**
     * transform init.
     */
    INIT(0, "transform_init"),

    /**
     * transform all.
     */
    ALL(1, "transform_all"),

    /**
     * transform custom.
     */
    CUSTOM(2, "transform_custom");

    private Integer value;

    private String code;

    TransformType(Integer value, String code){
        this.value = value;
        this.code = code;
    }
}
