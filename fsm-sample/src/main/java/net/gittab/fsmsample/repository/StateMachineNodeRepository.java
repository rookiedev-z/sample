package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.StateMachineNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author rookiedev
 */
public interface StateMachineNodeRepository extends JpaRepository<StateMachineNode, Long> {

    StateMachineNode getById(Long id);

    List<StateMachineNode> findByStateMachineId(Long stateMachineId);

    List<StateMachineNode> findByStateMachineIdAndType(Long stateMachineId, Integer type);

    StateMachineNode findByStateMachineIdAndStatusId(Long stateMachineId, Long statusId);
}
