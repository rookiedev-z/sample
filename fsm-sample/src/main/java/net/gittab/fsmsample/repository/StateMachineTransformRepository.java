package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.StateMachineTransform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateMachineTransformRepository extends JpaRepository<StateMachineTransform, Long> {

    List<StateMachineTransform> findByStateMachineId(Long stateMachineId);
}
