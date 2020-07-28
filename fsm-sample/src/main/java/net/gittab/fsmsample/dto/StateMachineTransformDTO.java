package net.gittab.fsmsample.dto;

import lombok.Data;

@Data
public class StateMachineTransformDTO {

    private Long id;

    private String name;

    private Long stateMachineId;

    private Long startNodeId;

    private Long endNodeId;

    private Integer type;

    private String description;
}
