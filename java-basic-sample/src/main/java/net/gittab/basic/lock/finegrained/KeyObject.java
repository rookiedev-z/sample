package net.gittab.basic.lock.finegrained;

import lombok.Data;

/**
 * KeyObject.
 *
 * @author rookiedev 2020/11/15 16:29
 **/
@Data
public class KeyObject {

    private String userId;

    public KeyObject(String userId){
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
