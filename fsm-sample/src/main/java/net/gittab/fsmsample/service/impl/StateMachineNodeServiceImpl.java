package net.gittab.fsmsample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.enums.NodeType;
import net.gittab.fsmsample.repository.StateMachineNodeRepository;
import net.gittab.fsmsample.service.StateMachineNodeService;

/**
 * @author rookiedev
 */
@Slf4j
@Service
public class StateMachineNodeServiceImpl implements StateMachineNodeService {

    @Autowired
    private StateMachineNodeRepository nodeRepository;

    @Override
    public StateMachineNode getInitNodeId(Long stateMachineId, NodeType nodeType) {
        List<StateMachineNode> stateMachineNodes = this.nodeRepository.findByStateMachineIdAndType(stateMachineId, nodeType.getValue());
        Assert.notEmpty(stateMachineNodes, "init node not found");
        Assert.isTrue(stateMachineNodes.size() == 1, "init node size more than 1");
        return stateMachineNodes.get(0);
    }

    @Override
    public List<StateMachineNode> getStateMachineNode(Long stateMachineId, NodeType nodeType) {
        List<StateMachineNode> stateMachineNodes = this.nodeRepository.findByStateMachineIdAndType(stateMachineId, nodeType.getValue());
        Assert.notEmpty(stateMachineNodes, "init node not found");
        return stateMachineNodes;
    }
}
