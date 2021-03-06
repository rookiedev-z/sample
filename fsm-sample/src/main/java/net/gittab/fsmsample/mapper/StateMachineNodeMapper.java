package net.gittab.fsmsample.mapper;

import net.gittab.fsmsample.domain.StateMachineNode;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author rookiedev
 */
public interface StateMachineNodeMapper extends Mapper<StateMachineNode> {

    List<StateMachineNode> findByStateMachineId(Long stateMachineId);

}
