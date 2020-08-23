package net.gittab.fsmsample.model;

import lombok.Data;

/**
 * TransformMessage.
 *
 * @author rookiedev
 * @date 2020/7/28 3:13 下午
 **/
@Data
public class TransformMessage {

    private Long issueId;

    private String invokeCode;

    private Long assigneeId;

}
