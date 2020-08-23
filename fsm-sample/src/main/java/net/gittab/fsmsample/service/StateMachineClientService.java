package net.gittab.fsmsample.service;

import net.gittab.fsmsample.model.TransformMessage;

/**
 * StateMachineInstanceService.
 *
 * @author rookiedev
 * @date 2020/7/28 2:41 下午
 **/
public interface StateMachineClientService {

    void executeTransform(Long issueId, Long currentStatusId, Long stateMachineId, Long transformId);

    void action(Long transformId, Long targetNodeId, TransformMessage message);

    boolean guard(Long transformId, Long targetNodeId, TransformMessage message);
}
