package net.gittab.fsmsample.service;

import net.gittab.fsmsample.dto.StatusDTO;

public interface StatusService {

    StatusDTO findById(Long statusId);
}
