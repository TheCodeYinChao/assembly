package cn.bainuo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonUtil {
	private static JacksonUtil _instance = new JacksonUtil();
	public static ObjectMapper objectMapper = null;

	static {
		log.info("JacksonUtil init");
		try {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		} catch (Exception e) {
			log.error("JacksonUtil init error,cause:", e);
		}
	}

	synchronized public static JacksonUtil getInstance() {
		return _instance;
	}

	/**
	 * 对象转换为JSON串. <br/>
	 * toJson:(obj). <br/>
	 *
	 * @author WangLZ
	 * @param obj
	 * @return
	 * @since JDK 1.8
	 */
	public static String toJson(Object obj) throws Exception {
		String rs = null;
		try {
			rs = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error("obj to json error {}", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return rs;
	}

	/**
	 * json串转换为JSON对象. <br/>
	 * toJson:(json串). <br/>
	 *
	 * @author WangLZ
	 * @param json
	 * @return
	 * @since JDK 1.8
	 */
	public static JsonNode toJson(String json) throws Exception {
		JsonNode node = null;
		try {
			node = objectMapper.readTree(json);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("{} to json exception {}", json, e.getMessage());
			throw new Exception(e.getMessage());
		}
		return node;
	}

	/**
	 * 根据json串转换java对象. <br/>
	 * toObj:(json,Obj). <br/>
	 *
	 * @author lm
	 * @param jsonString
	 * @param prototype
	 * @return
	 * @since JDK 1.8
	 */
	public static <T> T toObj(String jsonString, Class<T> prototype) throws Exception {
		try {
			return (T) objectMapper.readValue(jsonString, prototype);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("json to obj error {}", e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * json数组转换为java对象列表. <br/>
	 * toList:(jsonArray,对象类型). <br/>
	 *
	 * @author WangLZ
	 * @param javaType
	 * @return
	 * @since JDK 1.8
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toList(String jsonArray, JavaType javaType) throws Exception {
		try {
			return (T) objectMapper.readValue(jsonArray, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("json {} to List error {}", jsonArray, e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * json数组转换为java对象列表. <br/>
	 * 
	 * @author WangLZ
	 * @param jsonArray
	 * @param beanType
	 * @return
	 * @since JDK 1.8
	 */
	public static <T> List<T> toList(String jsonArray, Class<T> beanType) throws Exception {
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = objectMapper.readValue(jsonArray, javaType);
			return list;
		} catch (Exception e) {
			log.info("json {} to list Exception, cause : {}", jsonArray, e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 生成Jackson对象 <br/>
	 *
	 * @author WangLZ
	 * @return
	 * @since JDK 1.8
	 */
	public static ObjectNode createNode() {
		return objectMapper.createObjectNode();
	}

	/**
	 * 
	 * getCollectionType:获取泛型的Collection Type <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author lm
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 * @since JDK 1.8
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * map转化为指定对象 toObj:(beanType). <br/>
	 *
	 * @author MengBing
	 * @param map
	 * @param beanType
	 * @return
	 * @since JDK 1.8
	 */
	public static <T> T toObj(Map<String, Object> map, Class<T> beanType) throws Exception {
		try {
			return objectMapper.convertValue(map, beanType);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.error("map to obj error {}", e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

}
