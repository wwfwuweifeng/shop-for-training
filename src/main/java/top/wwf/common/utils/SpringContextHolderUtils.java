package top.wwf.common.utils;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
* @Description:    与spring容器进行交互
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/16 18:21
*/
@Component
@Lazy(false)
public class SpringContextHolderUtils implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext=null;
    private static Logger logger = LoggerFactory.getLogger(SpringContextHolderUtils.class);

    /**
     *实现ApplicationContextAware接口，当spring扫描到该bean时，会自动将applicationContext对象注入
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (logger.isDebugEnabled()){
            logger.debug("将ApplicationContext对象注入到SpringContextHolder中");
        }
        SpringContextHolderUtils.applicationContext=applicationContext;
    }


    public static <T> T getBeanByType(Class<T> classType){
        assertIsInjected();
        return (T)applicationContext.getBean(classType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanByName(String beanName){
        assertIsInjected();
        return (T)applicationContext.getBean(beanName);
    }


    private void clearHolder(){
        if (logger.isDebugEnabled()){
            logger.debug("清除SpringContextHolder中的ApplicationContext对象");
        }
        applicationContext=null;
    }

    /**
     * 实现DisposableBean接口，当该bean被销毁时，会自动调用该方法
     */
    @Override
    public void destroy() throws Exception {
        clearHolder();
    }


    /**
     * 检查ApplicationContext是否已经注入
     */
    private static void assertIsInjected() {
        Validate.validState(applicationContext != null, "applicaitonContext未注入, 请检查是否已将SpringContextHold"
                + "erUtils定义到Spring容器中");
    }
}
