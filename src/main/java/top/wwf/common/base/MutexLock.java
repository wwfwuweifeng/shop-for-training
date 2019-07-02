package top.wwf.common.base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;
import top.wwf.common.utils.JedisUtils;

/**
* @Description:    互斥锁，通过redis实现
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/20 21:26
*/
public class MutexLock {

    private static final String LOCK_PREFIX_KEY="mutexLock_";
    /**
     * 单次持有互斥锁的最长时间，一分钟
     */
    private static final int LOCK_MAX_EXIST_TIME=60000;
    /**
     * 尝试间隔，每秒重试一次
     */
    private static final int RETRY_INTERVAL=1000;

    private static Logger logger= LoggerFactory.getLogger(MutexLock.class);
    /**
     * lock的唯一身份标识,不会存在两个身份标识相同的锁住的lock
     */
    private String lockIdentification;

    private MutexLock(String lockIdentification){
        this.lockIdentification=LOCK_PREFIX_KEY+lockIdentification;
    }

    public static MutexLock create(String lockIdentification){
        return new MutexLock(lockIdentification);
    }

    /**
     * 加锁
     */
    public void lock(){
        if (!JedisUtils.setnx(this.lockIdentification,"1")){
            throw new MyException(HttpResponseEnum.PROHIBIT,"获取互斥锁失败");
        }
    }

    /**
     * 同步阻塞方法
     * 尝试获取锁，如果超过预计时间，仍未获取锁，则抛出异常
     * @param times 最长等待时间，单位：毫秒
     */
    public void tryLock(int times){
        try {
            while (!JedisUtils.setnx(this.lockIdentification,"1")){
                if (times>RETRY_INTERVAL){
                    Thread.sleep(RETRY_INTERVAL);
                    times-=RETRY_INTERVAL;
                }else {
                    throw new MyException(HttpResponseEnum.PROHIBIT,"等待超时");
                }
            }
            //获取锁成功，设置最长持有时间，防止当特殊原因，导致锁没被释放，然后一直存在于内存中
            JedisUtils.expire(this.lockIdentification,LOCK_MAX_EXIST_TIME);
        }catch (MyException e){
            logger.warn("获取互斥锁超时，互斥锁Id：{}",lockIdentification);
            throw e;
        }catch (Exception e){
            logger.error("获取互斥锁发生异常，互斥锁Id：{}，异常信息：",lockIdentification,e);
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 同步阻塞方法
     * 尝试获取锁，如果等待超过一个锁的最长持有时间，仍未获得到锁，则抛出异常
     */
    public void tryLock(){
        tryLock(LOCK_MAX_EXIST_TIME);
    }

    /**
     * 解锁
     * @return 返回是否解锁成功
     */
    public boolean unLock(){
        return JedisUtils.del(this.lockIdentification);
    }
}
