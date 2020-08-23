package net.gittab.fsmsample.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author rookiedev
 */
@Data
public class StateMachineDTO {

    private Long id;

    private String name;

    private Integer status;

    private String description;

    private List<StateMachineNodeDTO> nodes;

    private List<StateMachineTransformDTO> transforms;
}
