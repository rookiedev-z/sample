package net.gittab.basic.enums;

/**
 * IssueType.
 *
 * @author rookiedev
 * @date 2020/9/14 22:05
 **/
public enum IssueType {

    EPIC(0),

    STORY(1),

    TASK(2),

    BUG(3);

    private int code;

    IssueType(int code){
        this.code = code;
    }
}
