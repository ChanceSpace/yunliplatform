package com.yajun.green.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("redisCacheManager")
public class RedisCacheManager<T> {

    @Autowired
    RedisTemplate<String, T> redisTemplate ;
	
	/**
	 * 
	 * @Title: cache
	 * @Description: 缓存对象
	 * @param @param key
	 * @param @param obj    设定文件
	 * @param timeout   过期时间
	 * @param timeunit  时间单位 默认秒
	 * @return void    返回类型
	 * @throws
	 */
	public void cache(String key, T obj, int timeout, TimeUnit timeunit){
		try{
			BoundValueOperations<String, T> valOpr = redisTemplate.boundValueOps(key);
		
			if(null == timeunit) timeunit = TimeUnit.SECONDS;
			valOpr.set(obj, timeout, timeunit);
		}catch(Exception e){
            e.printStackTrace();
			// do nothing 放不进去就不放，不影响后续进程
		}
	}
	
	/**
	 * 缓存一个对象，默认是无限时间
	 */
	public void cache(String key, T obj){
		try{
			ValueOperations<String, T> opsForValue = redisTemplate.opsForValue();
			opsForValue.set(key, obj);
		}catch(Exception e){
            e.printStackTrace();
			// do nothing
		}
	}
	
	/**
	 * 
	 * @Title: fetch
	 * @Description: 取指定的key对应的对象
	 * @param @param key
	 * @param @return    设定文件
	 * @return T    返回类型
	 * @throws
	 */
	public T fetch(String key){
		try{
			BoundValueOperations<String, T> valOpr = redisTemplate.boundValueOps(key);
			return valOpr.get();
		}catch(Exception e){
            e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: del
	 * @Description: 删除指定键值
	 * @param @param key    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void del(String key){
		try{
			redisTemplate.delete(key);
		}catch(Exception e){
			// 不做任何操作 ，删除指定键值  
		}
	}
	
	/**
	 * 
	 * @Title: delAll
	 * @Description: 删除匹配patternKey的键值
	 * @param @param patternKey    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void delAll(String patternKey){
		try{
			Set<String> keys = redisTemplate.keys(patternKey);
		
			if(null != keys){
				redisTemplate.delete(keys);
			}
		}catch(Exception e){
			// do noting 继续后面的流程
		}
	}
	
	/**
	 * 
	 * @Title: setTimeOut
	 * @Description: 设置过期时间
	 * @param @param key 
	 * @param @param timeout 过期时间
	 * @param @param timeunit 时间单位 默认秒
	 * @return void    返回类型
	 * @throws
	 */
	public void setTimeOut(String key,long timeout,TimeUnit timeunit){
		try{
			if(null == timeunit){
				timeunit = TimeUnit.SECONDS;
			}
			redisTemplate.boundValueOps(key).expire(timeout, timeunit);
		}catch(Exception e){
			// do nonting  继续后面的流程 
		}
	}

    /**
	 *
	 * @Title: delAll
	 * @Description: 删除匹配patternKey的键值
	 * @param @param patternKey    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public Set<String> keySet(String patternKey){
        Set<String> keys = new HashSet<>();
		try{
			keys = redisTemplate.keys(patternKey);
		}catch(Exception e){
			// do noting 继续后面的流程
		}
        return keys;
	}
	
	public RedisTemplate<String, T> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static void main(String[] args) {
	}
}
