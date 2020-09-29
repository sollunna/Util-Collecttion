package sx.nine.util;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author psx
 * @date 2020/9/29 14:35
 * @description: Bean对象转Map
 * @version: V1.0
 * @since 1.0
 **/

public class BeanToMapUtil {
  /**
   * 将class对象转为map
   * @author psx
   * @date 2020/9/18 10:59
   * @param object
   * @return java.util.TreeMap<java.lang.String,java.lang.Object>
   * @throws
   */
  public static Map<String, Object> objToMap(Object object) throws IllegalAccessException {

    Class clazz = object.getClass();
    TreeMap<String, Object> treeMap = new TreeMap<String, Object>();

    while (null != clazz.getSuperclass()) {
      Field[] declaredFields1 = clazz.getDeclaredFields();

      for (Field field : declaredFields1) {
        String name = field.getName();

        // 获取原来的访问控制权限
        boolean accessFlag = field.isAccessible();
        // 修改访问控制权限
        field.setAccessible(true);
        Object value = field.get(object);
        // 恢复访问控制权限
        field.setAccessible(accessFlag);

        if (null != value && StringUtils.isNotBlank(value.toString())) {
          //如果是List,将List转换为json字符串
          if (value instanceof List) {
            value = JSON.toJSONString(value);
          }
          treeMap.put(name, value);
        }
      }

      clazz = clazz.getSuperclass();
    }
    return treeMap;
  }

 /***
  *  BeanTOMap
  * @author zyb
  * @date 2020/9/29 14:44
  * @param object
  * @return java.util.Map<java.lang.String,java.lang.Object>
  * @throws
 */
  public static Map<String, Object> ObjectConvertMap(Object object) {
    return Arrays.stream(BeanUtils.getPropertyDescriptors(object.getClass()))
        .filter(obj -> !"class".equals(obj.getName()))
        .collect(HashMap::new, (map, obj) -> map.put(obj.getName(), ReflectionUtils.invokeMethod(obj.getReadMethod(), object)), HashMap::putAll);
  }
}
