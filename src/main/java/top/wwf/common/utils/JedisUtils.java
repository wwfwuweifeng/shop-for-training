package top.wwf.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import top.wwf.common.base.GlobalConfig;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

/**
* @Description:    Java对于redis操作的工具类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/1/17 9:58
*/
public class JedisUtils {
    private static Logger logger= LoggerFactory.getLogger(JedisUtils.class);
    private static JedisPool jedisPool=SpringContextHolderUtils.getBeanByType(JedisPool.class);
    private static final String PREFIX_KEY= GlobalConfig.REDIS_PREFIX_KEY;

    /**
     * 获取缓存值，若存在，返回字符串，若不存在，返回null
     * @param key
     * @return
     */
    public static String get(String key){
        String completeKey=PREFIX_KEY+key;
        String value = null;
        Jedis jedis = null;
        try {
            jedis=getResource();
            value=jedis.get(completeKey);
            value= StringUtils.isNotBlank(value)&&!"nil".equalsIgnoreCase(value)?value:null;
        }catch (Exception e){
            logger.error("get {} = {} is fail", key, value);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存值，若存在，返回字符串，若不存在，返回null
     * @param outKey
     * @param innerKey
     * @return
     */
    public static String hget(String outKey,String innerKey){
        String completeKey=PREFIX_KEY+outKey;
        String value = null;
        Jedis jedis = null;
        try {
            jedis=getResource();
            value=jedis.hget(completeKey,innerKey);
            value= StringUtils.isNotBlank(value)&&!"nil".equalsIgnoreCase(value)?value:null;
        }catch (Exception e){
            logger.error("get map:{} field:{} fail, fail reason is ：",outKey,innerKey,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置hset的值
     * @param outKey
     * @param innerKey
     * @param value
     */
    public static void hset(String outKey,String innerKey,Object value){
        String completeKey=PREFIX_KEY+outKey;
        Jedis jedis=null;
        try {
            jedis=getResource();
            jedis.hset(completeKey,innerKey,value.toString());
        }catch (Exception e){
            logger.error("hset map:{} field:{} = {} fail, fail reason is : ",outKey,innerKey,value,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置缓存值
     * @param key
     * @param value
     */
    public static void set(String key,String value){
        String completeKey=PREFIX_KEY+key;
        Jedis jedis=null;
        try {
            jedis=getResource();
            jedis.set(completeKey, value);
        }catch (Exception e){
            logger.error("set {} = {} is fail",key,value);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置缓存值，及其过期时间
     * @param key
     * @param value
     * @param seconds 单位s
     */
    public static void setex(String key,int seconds,String value){
        String completeKey=PREFIX_KEY+key;
        Jedis jedis=null;
        try {
            jedis=getResource();
            jedis.setex(completeKey,seconds,value);
        }catch (Exception e){
            logger.error("setex {} = {} is fail",key,value);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除key，返回是否删除成功
     * @param key
     * @return
     */
    public static boolean del(String key){
        String completeKey=PREFIX_KEY+key;
        Jedis jedis=null;
        boolean result=false;
        try {
            jedis=getResource();
            if (jedis.del(completeKey)==1){
                result=true;
            }
        }catch (Exception e){
            logger.error("del：{} is fail",key);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
        return result;
    }
    /**
     * 设置缓存值，若原先key不存在，且设置成功，返回true，其余返回false
     * @param key
     * @param value
     * @return
     */
    public static boolean setnx(String key,String value){
        String completeKey=PREFIX_KEY+key;
        Jedis jedis=null;
        boolean result=false;
        try {
            jedis=getResource();
            if (jedis.setnx(completeKey, value)==1){
                result=true;
            }
        }catch (Exception e){
            logger.error("set {} = {} is fail",key,value);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param key
     * @param seconds 时间，单位秒
     */
    public static void expire(String key,int seconds){
        Jedis jedis=null;
        String completeKey=PREFIX_KEY+key;
        Long result=null;
        try {
            jedis=getResource();
            result=jedis.expire(completeKey,seconds);
        }catch (Exception e){
            logger.error("设置key：{} 的过期时间失败，失败原因：",key,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
        if (result!=1){
            logger.error("设置key：{} 已经有过期时间",key);
//            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public static Long ttl(String key){
        String completeKey=PREFIX_KEY+key;
        Jedis jedis=null;
        Long result=-1L;
        try {
            jedis=getResource();
            result=jedis.ttl(completeKey);
        }catch (Exception e){
            logger.error("获取key：{} 的剩余时间失败，失败原因：",key,e);
        }finally {
            returnResource(jedis);
        }
        return result;
    }


    /**
     * 判断某个key在缓存中是否存在
     * @param key
     * @return
     */
    public static boolean exists(String key){
        boolean result=false;
        Jedis jedis=null;
        try {
            jedis=getResource();
            result=jedis.exists(PREFIX_KEY+key);
        }catch (Exception e){
            logger.error("judge exists {} fail",key);
        }finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * redis是单线程，线程安全的，所以此处不用增加任何关于锁的处理
     * 自增某个key值，并返回自增后的结果
     * @param key
     * @return 此处要么抛出异常，要么返回的就是正确结果，所以外层不需要再判断value的值正确与否
     */
    public static Long incr(String key){
        String completeKey=PREFIX_KEY+key;
        Jedis jedis=null;
        Long value=null;
        try {
            jedis=getResource();
            value=jedis.incr(completeKey);
        }catch (Exception e){
            logger.error("incr {} fail，reason is ：",key,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 判断map缓存中，某个key是否存在
     * @param outKey    外层key，即这个map对应的key
     * @param innerKey  内层key，即map中的key
     * @return
     */
    public static boolean mapExists(String outKey,String innerKey){
        boolean result=false;
        Jedis jedis=null;
        try {
            jedis=getResource();
            result=jedis.hexists(PREFIX_KEY+outKey,PREFIX_KEY+innerKey);
        }catch (Exception e){
            logger.warn("judge mapExists {} {} fail",outKey,innerKey);
        }finally {
            returnResource(jedis);
        }
        return result;
    }



    /**
     * 获取一个redis连接
     * @return Jedis
     * @throws JedisException
     */
    public static Jedis getResource() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            logger.warn("getResource.", e);
            returnBrokenResource(jedis);
            throw e;
        }
        return jedis;
    }

    /**
     * 归还redis连接
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
