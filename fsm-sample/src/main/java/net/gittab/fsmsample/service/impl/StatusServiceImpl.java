package net.gittab.fsmsample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.dto.StatusDTO;
import net.gittab.fsmsample.mapper.StatusMapper;
import net.gittab.fsmsample.service.StatusService;

/**
 * @author rookiedev
 */
@Slf4j
@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusMapper statusMapper;


    @Override
    public StatusDTO findById(Long statusId) {
        return this.statusMapper.findById(statusId);
    }
}
