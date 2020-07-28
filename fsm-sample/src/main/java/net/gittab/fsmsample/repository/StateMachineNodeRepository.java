package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.StateMachineNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateMachineNodeRepository extends JpaRepository<StateMachineNode, Long> {

    List<StateMachineNode> findByStateMachineId(Long stateMachineId);

    List<StateMachineNode> findByStateMachineIdAndType(Long stateMachineId, Integer type);
}
