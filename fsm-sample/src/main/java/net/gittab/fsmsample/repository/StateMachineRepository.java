package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.StateMachine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rookiedev
 */
public interface StateMachineRepository extends JpaRepository<StateMachine, Long> {

    StateMachine getById(Long id);

    StateMachine findByProjectId(Long projectId);
}
