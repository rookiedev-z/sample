package net.gittab.fsmsample.enums;

import lombok.Getter;

/**
 * @author rookiedev
 */

@Getter
public enum NodeType {

    /**
     * start node.
     */
    START(0, "start"),

    /**
     * init node.
     */
    INIT(1, "init"),

    /**
     * custom node.
     */
    CUSTOM(2, "custom");

    private Integer value;

    private String code;

    NodeType(Integer value, String code){
        this.value = value;
        this.code = code;
    }
}
