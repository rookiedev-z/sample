package net.gittab.fsmsample.dto;

import lombok.Data;

/**
 * IssueDTO.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 2:12 下午
 **/
@Data
public class IssueDTO {

    private Long id;

    private String name;

    private Long statusId;

    private Long projectId;

    private String description;
}
