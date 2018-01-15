package ewing.application.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Jackson简单封装。
 *
 * @author Ewing
 */
public class JsonConverter {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
    }

    /**
     * 把Json字符串转换为相应的Java对象。
     *
     * @param json 要转换的Java对象类型
     * @param type 原始Json字符串数据
     * @return Java对象实例
     */
    public static <T> T toObject(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把Java对象实例转换为Json字符串。
     *
     * @param object Java对象实例
     * @return Json字符串
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}