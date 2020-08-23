package net.gittab.fsmsample.mapper;

import net.gittab.fsmsample.domain.StateMachineTransform;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author rookiedev
 */
public interface StateMachineTransformMapper extends Mapper<StateMachineTransform> {

    List<StateMachineTransform> queryByStartNodeIdOrType(@Param("stateMachineId") Long stateMachineId, @Param("startNodeId") Long startNodeId, @Param("transformType") Integer transformType);

}
