package net.gittab.async.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JsonUtil.
 *
 * @author rookiedev
 * @date 2020/8/23 03:52
 **/
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper STD_OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper ignoreUnknownFieldObjectMapper;

    private JsonUtil() {
    }

    public static String encode(Object obj, boolean ignoreUnknownField) {
        try {
            if (ignoreUnknownField) {
                return ignoreUnknownFieldObjectMapper.writeValueAsString(obj);
            }
            return STD_OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("encode(Object)", e);
        }

        return null;
    }

    public static <T> T decode(String json, Class<T> valueType, boolean ignoreUnknownField) {
        try {
            if (ignoreUnknownField) {
                return ignoreUnknownFieldObjectMapper.readValue(json, valueType);
            }
            return STD_OBJECT_MAPPER.readValue(json, valueType);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }

        return null;
    }

    static {
        STD_OBJECT_MAPPER.registerModule(new JavaTimeModule());
        STD_OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        ignoreUnknownFieldObjectMapper = new ObjectMapper();
        ignoreUnknownFieldObjectMapper.registerModule(new JavaTimeModule());
        ignoreUnknownFieldObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        ignoreUnknownFieldObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
