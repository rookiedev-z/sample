package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.StateMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateMachineRepository extends JpaRepository<StateMachine, Long> {
}
