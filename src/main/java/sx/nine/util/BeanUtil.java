package sx.nine.util;

import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: psx
 * @Date: 2019/09/08 17:00
 * @Description: Bean 转换工具类
 */
public class BeanUtil {

    /**
     * 将List中的Bean对象转换成其他Bean对象
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            source.stream().filter(Objects::nonNull).forEach(e -> target.add(copyBean(e, clazz)));
        }
        return target;
    }

    /**
     * 将一个Bean对象转换成另一个Bean对象
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }
}
