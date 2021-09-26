package sx.nine.util;

import javax.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author shaoxia.peng
 * @date 2021/9/26 16:18
 * @description
 * @since 1.0
 * @version 1.0
 */
@Component
public class TestUtil implements InstantiationAwareBeanPostProcessor {

  @Resource
  private DateUtil dateUtil;

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return false;
  }

  public static void main(String[] args) {


  }
}
