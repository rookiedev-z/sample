package net.gittab.fsmsample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.domain.Issue;
import net.gittab.fsmsample.domain.StateMachine;
import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.enums.NodeType;
import net.gittab.fsmsample.repository.IssueRepository;
import net.gittab.fsmsample.repository.StateMachineRepository;
import net.gittab.fsmsample.service.IssueService;
import net.gittab.fsmsample.service.StateMachineClientService;
import net.gittab.fsmsample.service.StateMachineNodeService;

/**
 * @author xiaohua zhou
 */
@Slf4j
@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private StateMachineRepository stateMachineRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private StateMachineNodeService nodeService;

    @Autowired
    private StateMachineClientService stateMachineClientService;


    @Override
    public void createIssue(Long projectId, Issue issue) {
        issue.setProjectId(projectId);

        // 通过项目 id 查询状态机
        StateMachine stateMachine = this.stateMachineRepository.findByProjectId(projectId);

        // 通过状态机查询初始化状态节点
        StateMachineNode stateMachineNode = this.nodeService.getInitNodeId(stateMachine.getId(), NodeType.INIT);

        // 初始化状态
        issue.setStatusId(stateMachineNode.getStatusId());

        this.issueRepository.save(issue);
    }



    @Override
    public void updateIssueStatus(Long projectId, Long issueId, Long transformId) {
        Issue issue = this.issueRepository.getById(issueId);
        Assert.notNull(issue,"issue not found");

        // 通过项目 id 查询状态机
        StateMachine stateMachine = this.stateMachineRepository.findByProjectId(projectId);

        // 执行状态转换
        stateMachineClientService.executeTransform(issueId, issue.getStatusId(), stateMachine.getId(), transformId);
    }
}
