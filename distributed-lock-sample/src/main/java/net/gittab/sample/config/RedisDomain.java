package net.gittab.sample.config;

import lombok.Data;

/**
 * RedisDomain.
 *
 * @author rookiedev 2020/10/27 14:58
 **/
@Data
public class RedisDomain {

    private String host;

    private int port;

    private int database;
}
