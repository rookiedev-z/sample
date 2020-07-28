package net.gittab.fsmsample.service;

import java.util.List;

import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.enums.NodeType;

/**
 * @author xiaohua zhou
 */
public interface StateMachineNodeService {

    StateMachineNode getInitNodeId(Long stateMachineId, NodeType nodeType);

    List<StateMachineNode> getStateMachineNode(Long stateMachineId, NodeType nodeType);

}
