package net.gittab.fsmsample.service.impl;

import java.util.List;

import net.gittab.fsmsample.mapper.StateMachineTransformMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.domain.Issue;
import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.domain.StateMachineTransform;
import net.gittab.fsmsample.enums.ActionCode;
import net.gittab.fsmsample.enums.TransformType;
import net.gittab.fsmsample.factory.StateMachineInstance;
import net.gittab.fsmsample.model.TransformMessage;
import net.gittab.fsmsample.processor.StateMachineManagement;
import net.gittab.fsmsample.repository.IssueRepository;
import net.gittab.fsmsample.repository.StateMachineNodeRepository;
import net.gittab.fsmsample.repository.StateMachineTransformRepository;
import net.gittab.fsmsample.service.StateMachineClientService;
import org.aspectj.apache.bcel.classfile.Code;

/**
 * StateMachineInstanceService.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 2:41 下午
 **/
@Slf4j
@Service
public class StateMachineClientServiceImpl implements StateMachineClientService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private StateMachineNodeRepository nodeRepository;

    @Autowired
    private StateMachineTransformRepository transformRepository;

    @Autowired
    private StateMachineTransformMapper transformMapper;

    @Autowired
    private StateMachineInstance stateMachineInstance;

    @Autowired
    private StateMachineManagement stateMachineManagement;

    @Override
    public void executeTransform(Long issueId, Long currentStatusId, Long stateMachineId, Long transformId) {

        // 获取当前状态节点
        StateMachineNode stateMachineNode = this.nodeRepository.findByStateMachineIdAndStatusId(stateMachineId, currentStatusId);
        Assert.notNull(stateMachineNode, "state machine node not found");

        // 查询当前状态节点可进行的转换，校验该 transform 是否合法
        List<StateMachineTransform> transforms = this.transformMapper
                .queryByStartNodeIdOrType(stateMachineId, stateMachineNode.getId(), TransformType.ALL.getValue());

        if(transforms.stream().noneMatch(item -> item.getId().equals(transformId))){
            throw new IllegalStateException("this transform is not exist");
        }

        TransformMessage transformMessage = new TransformMessage();
        transformMessage.setIssueId(issueId);
        transformMessage.setInvokeCode(ActionCode.ASSIGN_USER);

        // 执行转换
//        this.stateMachineInstance.executeTransform(stateMachineId, transformId, currentStatusId, transformMessage);
        this.stateMachineInstance.executeSquirrelTransform(stateMachineId, transformId, stateMachineNode.getId(), transformMessage);

    }

    @Override
    public void action(Long transformId, Long targetNodeId, TransformMessage message) {

        StateMachineTransform transform = this.transformRepository.getById(transformId);

        // 如果是初始化，直接返回
        if(TransformType.INIT.getValue().equals(transform.getType())){
            return;
        }

        StateMachineNode node = this.nodeRepository.getById(targetNodeId);

        Issue issue = this.issueRepository.getById(message.getIssueId());

        // 更新 issue 状态
        if(!issue.getStatusId().equals(node.getStatusId())){
            issue.setStatusId(node.getStatusId());
           this.issueRepository.save(issue);
        }

        // 执行定义的后置动作, 比如说将 issue assign 给指定的人
        message.setAssigneeId(1L);
        stateMachineManagement.postAction(message);

    }

    @Override
    public boolean guard(Long transformId, Long targetNodeId, TransformMessage message) {

        // 校验是否能够执行转换

        return true;
    }


}
