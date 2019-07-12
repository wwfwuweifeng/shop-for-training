package top.wwf.common.base;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @Description:    用于载入Properties配置文件，后面载入的同名属性会覆盖先前载入的，System的Property拥有最高优先级
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/17 11:31
*/
public class PropertiesLoader {
    private static Logger logger= LoggerFactory.getLogger(PropertiesLoader.class);
    private static ResourceLoader resourceLoader=new DefaultResourceLoader();
    private final Properties properties;

    public PropertiesLoader(String... filePaths) {
        this.properties = loadPropertiesFile(filePaths);
    }

    /**
     * 获取属性值，以System的Property优先
     * @param key 属性名
     * @return  属性值，若不存在，则返回空字符串
     */
    public String getConfigValue(String key){
        String value=System.getProperty(key);
        if (value==null){
            value=properties.getProperty(key);

        }
        if (value==null){
            logger.debug("属性："+key+" 不存在");
            value= StringUtils.EMPTY;
        }
        return value;
    }


    private Properties loadPropertiesFile(String... filePaths){
        Properties props=new Properties();
        if (filePaths!=null){
            InputStream in = null;
            for (String filePath:filePaths){
                try {
                    Resource resource = resourceLoader.getResource(filePath);
                    in = resource.getInputStream();
                    props.load(in);
                } catch (IOException ex) {
                    logger.info("Could not load properties:" + filePath + ", " + ex.getMessage());
                } finally {
                    IOUtils.closeQuietly(in);
                }

            }
        }
        return props;
    }

}
