package net.gittab.fsmsample.dto;

import lombok.Data;

@Data
public class StatusDTO {

    private Long id;

    private String name;

    private Integer type;

    private String description;
}
