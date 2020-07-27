package net.gittab.fsmsample.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.domain.StateMachine;
import net.gittab.fsmsample.domain.StateMachineNode;
import net.gittab.fsmsample.domain.StateMachineTransform;
import net.gittab.fsmsample.dto.StateMachineDTO;
import net.gittab.fsmsample.dto.StateMachineNodeDTO;
import net.gittab.fsmsample.dto.StateMachineTransformDTO;
import net.gittab.fsmsample.mapper.StateMachineMapper;
import net.gittab.fsmsample.mapper.StateMachineNodeMapper;
import net.gittab.fsmsample.mapper.StateMachineTransformMapper;
import net.gittab.fsmsample.repository.StateMachineNodeRepository;
import net.gittab.fsmsample.repository.StateMachineRepository;
import net.gittab.fsmsample.repository.StateMachineTransformRepository;
import net.gittab.fsmsample.service.StateMachineService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class StateMachineServiceImpl implements StateMachineService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StateMachineMapper stateMachineMapper;

    @Autowired
    private StateMachineNodeMapper nodeMapper;

    @Autowired
    private StateMachineTransformMapper transformMapper;

    @Autowired
    private StateMachineRepository stateMachineRepository;

    @Autowired
    private StateMachineNodeRepository nodeRepository;

    @Autowired
    private StateMachineTransformRepository transformRepository;


    @Override
    public StateMachineDTO findById(Long stateMachineId) {
        StateMachine stateMachine = this.stateMachineMapper.selectByPrimaryKey(stateMachineId);
        Assert.notNull(stateMachine, "state machine not found");
        StateMachineDTO stateMachineDTO = this.modelMapper.map(stateMachine, StateMachineDTO.class);

        List<StateMachineNode> nodes = this.nodeRepository.findByStateMachineId(stateMachineId);
        if(!CollectionUtils.isEmpty(nodes)){
            List<StateMachineNodeDTO> nodeDTOS = this.modelMapper.map(nodes, new TypeToken<List<StateMachineNodeDTO>>() {
            }.getType());
            stateMachineDTO.setNodes(nodeDTOS);
        }

        List<StateMachineTransform> transforms = this.transformRepository.findByStateMachineId(stateMachineId);
        if(!CollectionUtils.isEmpty(transforms)){
            List<StateMachineTransformDTO> transformDTOS = this.modelMapper.map(transforms, new TypeToken<List<StateMachineTransformDTO>>() {
            }.getType());
            stateMachineDTO.setTransforms(transformDTOS);
        }

        return stateMachineDTO;
    }
}
