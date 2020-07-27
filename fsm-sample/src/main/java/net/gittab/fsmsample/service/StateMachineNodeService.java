package net.gittab.fsmsample.service;

import net.gittab.fsmsample.enums.NodeType;

public interface StateMachineNodeService {

    Long getNodeId(Long stateMachineId, NodeType nodeType);

}
