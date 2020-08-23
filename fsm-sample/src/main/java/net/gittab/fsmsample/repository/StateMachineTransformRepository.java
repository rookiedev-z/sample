package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.StateMachineTransform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author rookiedev
 */
public interface StateMachineTransformRepository extends JpaRepository<StateMachineTransform, Long> {

    StateMachineTransform getById(Long id);

    List<StateMachineTransform> findByStateMachineId(Long stateMachineId);

    List<StateMachineTransform> findByStateMachineIdAndStartNodeId(Long stateMachineId, Long startNodeId);
}
