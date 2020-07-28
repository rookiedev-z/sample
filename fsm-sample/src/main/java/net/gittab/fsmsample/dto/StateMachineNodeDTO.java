package net.gittab.fsmsample.dto;

import lombok.Data;

@Data
public class StateMachineNodeDTO {

    private Long id;

    private Long stateMachineId;

    private Long statusId;

    private Integer type;

    private Long allTransformId;

    private String description;
}
