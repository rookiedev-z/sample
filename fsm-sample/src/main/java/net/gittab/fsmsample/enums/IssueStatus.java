package net.gittab.fsmsample.enums;

import lombok.Getter;

/**
 * IssueStatus.
 *
 * @author rookiedev
 **/
@Getter
public enum IssueStatus {

    /**
     * to do status.
     */
    TODO(StatusCode.TODO, 0),

    /**
     * groomed.
     */
    GROOMED(StatusCode.GROOMED, 1),

    /**
     * in progress.
     */
    IN_PROGRESS(StatusCode.IN_PROGRESS, 1),

    /**
     * blocked.
     */
    BLOCKED(StatusCode.BLOCKED, 1),

    /**
     * needs qa.
     */
    NEEDS_QA(StatusCode.NEEDS_QA, 2),

    /**
     * qa approved.
     */
    QA_APPROVED(StatusCode.QA_APPROVED, 3),

    /**
     * ready for production.
     */
    READY_FOR_PRODUCTION(StatusCode.READY_FOR_PRODUCTION, 3),

    /**
     * done.
     */
    DONE(StatusCode.DONE, 4);

    private final String code;

    private final Integer type;

    IssueStatus(String code, Integer type){
        this.code = code;
        this.type = type;
    }




}
