package net.gittab.fsmsample.service;

import net.gittab.fsmsample.dto.StateMachineDTO;

public interface StateMachineService {

    StateMachineDTO findById(Long stateMachineId);

}
