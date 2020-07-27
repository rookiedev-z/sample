package net.gittab.fsmsample.service;


import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.FsmSampleApplicationTests;
import net.gittab.fsmsample.dto.StatusDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class StatusServiceTest extends FsmSampleApplicationTests {

    @Autowired
    private StatusService statusService;

    @Test
    public void testFindById(){
        Long id = 1L;
        StatusDTO statusDTO = this.statusService.findById(id);
        log.info("find result {}", statusDTO.toString());
    }


}
