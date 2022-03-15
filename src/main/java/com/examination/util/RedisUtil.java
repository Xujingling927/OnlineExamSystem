package com.examination.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * 声明: 此工具只简单包装了redisTemplate的大部分常用的api, 没有包装redisTemplate所有的api。
 *      如果对此工具类中的功能不太满意, 或对StringRedisTemplate提供的api不太满意，
 *      那么可自行实现相应的{@link StringRedisTemplate}类中的对应execute方法, 以达
 *      到自己想要的效果; 至于如何实现,则可参考源码中的方法。
 *
 * 注: 此工具类依赖spring-boot-starter-data-redis类库、以及可选的lombok、fastjson
 * 注: 更多javadoc细节，可详见{@link RedisOperations}
 *
 * 统一说明一: 方法中的key、 value都不能为null。
 * 统一说明二: 不能跨数据类型进行操作， 否者会操作失败/操作报错。
 *            如: 向一个String类型的做Hash操作，会失败/报错......等等
 *
 * @author JustryDeng
 *  2020/3/7 16:50:05
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class RedisUtil implements ApplicationContextAware {

    /** 使用StringRedisTemplate(，其是RedisTemplate的定制化升级) */
    private static StringRedisTemplate redisTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisUtil.redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }

    /**
     * key相关操作
     *
     * @author JustryDeng
     *  2020/3/7 16:54:25
     */
    public static class KeyOps {

        /**
         * 根据key, 删除redis中的对应key-value
         *
         *  注: 若删除失败, 则返回false。
         *
         *      若redis中，不存在该key, 那么返回的也是false。
         *      所以，不能因为返回了false,就认为redis中一定还存
         *      在该key对应的key-value。
         *
         * @param key
         *            要删除的key
         * @return  删除是否成功
         *  2020/3/7 17:15:02
         */
        public static boolean delete(String key) {
            log.info("delete(...) => key -> {}", key);
            // 返回值只可能为true/false, 不可能为null
            Boolean result = redisTemplate.delete(key);
            log.info("delete(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 根据keys, 批量删除key-value
         *
         * 注: 若redis中，不存在对应的key, 那么计数不会加1, 即:
         *     redis中存在的key-value里，有名为a1、a2的key，
         *     删除时，传的集合是a1、a2、a3，那么返回结果为2。
         *
         * @param keys
         *            要删除的key集合
         * @return  删除了的key-value个数
         *  2020/3/7 17:48:04
         */
        public static long delete(Collection<String> keys) {
            log.info("delete(...) => keys -> {}", keys);
            Long count = redisTemplate.delete(keys);
            log.info("delete(...) => count -> {}", count);
            if (count == null) {
                throw new RedisOpsResultIsNullException();
            }
            return count;
        }

        /**
         * 将key对应的value值进行序列化，并返回序列化后的value值。
         *
         * 注: 若不存在对应的key, 则返回null。
         * 注: dump时，并不会删除redis中的对应key-value。
         * 注: dump功能与restore相反。
         *
         * @param key
         *            要序列化的value的key
         * @return  序列化后的value值
         *  2020/3/8 11:34:13
         */
        public static byte[] dump(String key) {
            log.info("dump(...) =>key -> {}", key);
            byte[] result = redisTemplate.dump(key);
            log.info("dump(...) => result -> {}", result);
            return result;
        }

        /**
         * 将给定的value值，反序列化到redis中, 形成新的key-value。
         *
         * @param key
         *            value对应的key
         * @param value
         *            要反序列的value值。
         *            注: 这个值可以由{@link this#dump(String)}获得
         * @param timeToLive
         *            反序列化后的key-value的存活时长
         * @param unit
         *            timeToLive的单位
         *
         * @throws RedisSystemException
         *             如果redis中已存在同样的key时，抛出此异常
         *  2020/3/8 11:36:45
         */
        public static void restore(String key, byte[] value, long timeToLive, TimeUnit unit) {
            restore(key, value, timeToLive, unit, false);
        }

        /**
         * 将给定的value值，反序列化到redis中, 形成新的key-value。
         *
         * @param key
         *            value对应的key
         * @param value
         *            要反序列的value值。
         *            注: 这个值可以由{@link this#dump(String)}获得
         * @param timeout
         *            反序列化后的key-value的存活时长
         * @param unit
         *            timeout的单位
         * @param replace
         *            若redis中已经存在了相同的key, 是否替代原来的key-value
         *
         * @throws RedisSystemException
         *             如果redis中已存在同样的key, 且replace为false时，抛出此异常
         *  2020/3/8 11:36:45
         */
        public static void restore(String key, byte[] value, long timeout, TimeUnit unit, boolean replace) {
            log.info("restore(...) => key -> {}, value -> {}, timeout -> {}, unit -> {}, replace -> {}",
                    key, value, timeout, unit, replace);
            redisTemplate.restore(key, value, timeout, unit, replace);
        }

        /**
         * redis中是否存在,指定key的key-value
         *
         * @param key
         *            指定的key
         * @return  是否存在对应的key-value
         *  2020/3/8 12:16:46
         */
        public static boolean hasKey(String key) {
            log.info("hasKey(...) => key -> {}", key);
            Boolean result = redisTemplate.hasKey(key);
            log.info("hasKey(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 给指定的key对应的key-value设置: 多久过时
         *
         * 注:过时后，redis会自动删除对应的key-value。
         * 注:若key不存在，那么也会返回false。
         *
         * @param key
         *            指定的key
         * @param timeout
         *            过时时间
         * @param unit
         *            timeout的单位
         * @return  操作是否成功
         *  2020/3/8 12:18:58
         */
        public static boolean expire(String key, long timeout, TimeUnit unit) {
            log.info("expire(...) => key -> {}, timeout -> {}, unit -> {}", key, timeout, unit);
            Boolean result = redisTemplate.expire(key, timeout, unit);
            log.info("expire(...) => result is -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 给指定的key对应的key-value设置: 什么时候过时
         *
         * 注:过时后，redis会自动删除对应的key-value。
         * 注:若key不存在，那么也会返回false。
         *
         * @param key
         *            指定的key
         * @param date
         *            啥时候过时
         *
         * @return  操作是否成功
         *  2020/3/8 12:19:29
         */
        public static boolean expireAt(String key, Date date) {
            log.info("expireAt(...) => key -> {}, date -> {}", key, date);
            Boolean result = redisTemplate.expireAt(key, date);
            log.info("expireAt(...) => result is -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 找到所有匹配pattern的key,并返回该key的结合.
         *
         * 提示:若redis中键值对较多，此方法耗时相对较长，慎用！慎用！慎用！
         *
         * @param pattern
         *            匹配模板。
         *            注: 常用的通配符有:
         *                 ?    有且只有一个;
         *                 *     >=0哥;
         *
         * @return  匹配pattern的key的集合。 可能为null。
         *  2020/3/8 12:38:38
         */
        public static Set<String> keys(String pattern) {
            log.info("keys(...) => pattern -> {}", pattern);
            Set<String> keys = redisTemplate.keys(pattern);
            log.info("keys(...) => keys -> {}", keys);
            return keys;
        }

        /**
         * 将当前数据库中的key对应的key-value,移动到对应位置的数据库中。
         *
         * 注:单机版的redis,默认将存储分为16个db, index为0 到 15。
         * 注:同一个db下，key唯一； 但是在不同db中，key可以相同。
         * 注:若目标db下，已存在相同的key, 那么move会失败，返回false。
         *
         * @param key
         *            定位要移动的key-value的key
         * @param dbIndex
         *            要移动到哪个db
         * @return 移动是否成功。
         *         注: 若目标db下，已存在相同的key, 那么move会失败，返回false。
         *  2020/3/8 13:01:00
         */
        public static boolean move(String key, int dbIndex) {
            log.info("move(...) => key  -> {}, dbIndex -> {}", key, dbIndex);
            Boolean result = redisTemplate.move(key, dbIndex);
            log.info("move(...) =>result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 移除key对应的key-value的过期时间, 使该key-value一直存在
         *
         * 注: 若key对应的key-value，本身就是一直存在(无过期时间的)， 那么persist方法会返回false;
         *    若没有key对应的key-value存在，本那么persist方法会返回false;
         *
         * @param key
         *            定位key-value的key
         * @return 操作是否成功
         *  2020/3/8 13:10:02
         */
        public static boolean persist(String key) {
            log.info("persist(...) => key -> {}", key);
            Boolean result = redisTemplate.persist(key);
            log.info("persist(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 获取key对应的key-value的过期时间
         *
         * 注: 若key-value永不过期， 那么返回的为-1。
         * 注: 若不存在key对应的key-value， 那么返回的为-2
         * 注:若存在零碎时间不足1 SECONDS,则(大体上)四舍五入到SECONDS级别。
         *
         * @param key
         *            定位key-value的key
         * @return  过期时间(单位s)
         *  2020/3/8 13:17:35
         */
        public static long getExpire(String key) {
            return getExpire(key, TimeUnit.SECONDS);
        }

        /**
         * 获取key对应的key-value的过期时间
         *
         * 注: 若key-value永不过期， 那么返回的为-1。
         * 注: 若不存在key对应的key-value， 那么返回的为-2
         * 注:若存在零碎时间不足1 unit,则(大体上)四舍五入到unit别。
         *
         * @param key
         *            定位key-value的key
         * @return  过期时间(单位unit)
         *  2020/3/8 13:17:35
         */
        public static long getExpire(String key, TimeUnit unit) {
            log.info("getExpire(...) =>key -> {}, unit is -> {}", key, unit);
            Long result = redisTemplate.getExpire(key, unit);
            log.info("getExpire(...) => result ->  {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 从redis的所有key中，随机获取一个key
         *
         * 注: 若redis中不存在任何key-value, 那么这里返回null
         *
         * @return  随机获取到的一个key
         *  2020/3/8 14:11:43
         */
        public static String randomKey() {
            String result = redisTemplate.randomKey();
            log.info("randomKey(...) => result is -> {}", result);
            return result;
        }

        /**
         * 重命名对应的oldKey为新的newKey
         *
         * 注: 若oldKey不存在， 则会抛出异常.
         * 注: 若redis中已存在与newKey一样的key,
         *     那么原key-value会被丢弃，
         *     只留下新的key，以及原来的value
         *     示例说明: 假设redis中已有 (keyAlpha, valueAlpha) 和 (keyBeta, valueBeat),
         *              在使用rename(keyAlpha, keyBeta)替换后, redis中只会剩下(keyBeta, valueAlpha)
         *
         * @param oldKey
         *            旧的key
         * @param newKey
         *            新的key
         * @throws RedisSystemException
         *             若oldKey不存在时， 抛出此异常
         *  2020/3/8 14:14:17
         */
        public static void rename(String oldKey, String newKey) {
            log.info("rename(...) => oldKey -> {}, newKey -> {}", oldKey, newKey);
            redisTemplate.rename(oldKey, newKey);
        }

        /**
         * 当redis中不存在newKey时, 重命名对应的oldKey为新的newKey。
         * 否者不进行重命名操作。
         *
         * 注: 若oldKey不存在， 则会抛出异常.
         *
         * @param oldKey
         *            旧的key
         * @param newKey
         *            新的key
         * @throws RedisSystemException
         *             若oldKey不存在时， 抛出此异常
         *  2020/3/8 14:14:17
         */
        public static boolean renameIfAbsent(String oldKey, String newKey) {
            log.info("renameIfAbsent(...) => oldKey -> {}, newKey -> {}", oldKey, newKey);
            Boolean result = redisTemplate.renameIfAbsent(oldKey, newKey);
            log.info("renameIfAbsent(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 获取key对应的value的数据类型
         *
         * 注: 若redis中不存在该key对应的key-value， 那么这里返回NONE。
         *
         * @param key
         *            用于定位的key
         * @return  key对应的value的数据类型
         *  2020/3/8 14:40:16
         */
        public static DataType type(String key) {
            log.info("type(...) => key -> {}", key);
            DataType result = redisTemplate.type(key);
            log.info("type(...) => result -> {}", result);
            return result;
        }
    }

    /**
     * string相关操作
     *
     * 提示: redis中String的数据结构可参考resources/data-structure/String(字符串)的数据结构(示例一).png
     *      redis中String的数据结构可参考resources/data-structure/String(字符串)的数据结构(示例二).png
     *
     * @author JustryDeng
     *  2020/3/7 16:54:25
     */
    public static class StringOps {

        /**
         * 设置key-value
         *
         * 注: 若已存在相同的key, 那么原来的key-value会被丢弃。
         *
         * @param key
         *            key
         * @param value
         *            key对应的value
         *  2020/3/8 15:40:59
         */
        public static void set(String key, String value) {
            log.info("set(...) => key -> {}, value -> {}", key, value);
            redisTemplate.opsForValue().set(key, value);
        }

        /**
         * 处理redis中key对应的value值, 将第offset位的值, 设置为1或0。
         *
         * 说明: 在redis中，存储的字符串都是以二级制的进行存在的; 如存储的key-value里，值为abc,实际上，
         *       在redis里面存储的是011000010110001001100011,前8为对应a,中间8为对应b,后面8位对应c。
         *       示例：这里如果setBit(key, 6, true)的话，就是将索引位置6的那个数，设置值为1，值就变成
         *            了011000110110001001100011
         *       追注:offset即index,从0开始。
         *
         * 注: 参数value为true, 则设置为1；参数value为false, 则设置为0。
         *
         * 注: 若redis中不存在对应的key,那么会自动创建新的。
         * 注: offset可以超过value在二进制下的索引长度。
         *
         * @param key
         *            定位value的key
         * @param offset
         *            要改变的bit的索引
         * @param value
         *            改为1或0, true - 改为1, false - 改为0
         *
         * @return set是否成功
         *  2020/3/8 16:30:37
         */
        public static boolean setBit(String key, long offset, boolean value) {
            log.info("setBit(...) => key -> {}, offset -> {}, value -> {}", key, offset, value);
            Boolean result = redisTemplate.opsForValue().setBit(key, offset, value);
            log.info("setBit(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 设置key-value
         *
         * 注: 若已存在相同的key, 那么原来的key-value会被丢弃
         *
         * @param key
         *            key
         * @param value
         *            key对应的value
         * @param timeout
         *            过时时长
         * @param unit
         *            timeout的单位
         *  2020/3/8 15:40:59
         */
        public static void setEx(String key, String value, long timeout, TimeUnit unit) {
            log.info("setEx(...) => key -> {}, value -> {}, timeout -> {}, unit -> {}",
                    key, value, timeout, unit);
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        }

        /**
         * 若不存在key时, 向redis中添加key-value, 返回成功/失败。
         * 若存在，则不作任何操作, 返回false。
         *
         * @param key
         *            key
         * @param value
         *            key对应的value
         *
         * @return set是否成功
         *  2020/3/8 16:51:36
         */
        public static boolean setIfAbsent(String key, String value) {
            log.info("setIfAbsent(...) => key -> {}, value -> {}", key, value);
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
            log.info("setIfAbsent(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 若不存在key时, 向redis中添加一个(具有超时时长的)key-value, 返回成功/失败。
         * 若存在，则不作任何操作, 返回false。
         *
         * @param key
         *            key
         * @param value
         *            key对应的value
         * @param timeout
         *            超时时长
         * @param unit
         *            timeout的单位
         *
         * @return set是否成功
         *  2020/3/8 16:51:36
         */
        public static boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
            log.info("setIfAbsent(...) => key -> {}, value -> {}, key -> {}, value -> {}", key, value, timeout, unit);
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
            log.info("setIfAbsent(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 从(redis中key对应的)value的offset位置起(包含该位置),用replaceValue替换对应长度的值。
         *
         * 举例说明:
         *       1.假设redis中存在key-value ("ds", "0123456789"); 调
         *         用setRange("ds", "abcdefghijk", 3)后， redis中该value值就变为了[012abcdefghijk]
         *
         *       2.假设redis中存在key-value ("jd", "0123456789");调
         * 		   用setRange("jd", "xyz", 3)后， redis中该value值就变为了[012xyz6789]
         *
         *       3.假设redis中存在key-value ("ey", "0123456789");调
         * 		   用setRange("ey", "qwer", 15)后， redis中该value值就变为了[0123456789     qwer]
         *       注:case3比较特殊，offset超过了原value的长度了, 中间就会有一些空格来填充，但是如果在程序
         *          中直接输出的话，中间那部分空格可能会出现乱码。
         *
         * @param key
         *            定位key-value的key
         * @param replaceValue
         *            要替换的值
         * @param offset
         *            起始位置
         *  2020/3/8 17:04:31
         */
        public static void setRange(String key, String replaceValue, long offset) {
            log.info("setRange(...) => key -> {}, replaceValue -> {}, offset -> {}", key, replaceValue, offset);
            redisTemplate.opsForValue().set(key, replaceValue, offset);
        }

        /**
         * 获取到key对应的value的长度。
         *
         * 注: 长度等于{@link String#length}。
         * 注: 若redis中不存在对应的key-value, 则返回值为0.
         *
         * @param key
         *            定位value的key
         * @return  value的长度
         *  2020/3/8 17:14:30
         */
        public static long size(String key) {
            log.info("size(...) => key -> {}", key);
            Long result = redisTemplate.opsForValue().size(key);
            log.info("size(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 批量设置 key-value
         *
         * 注: 若存在相同的key, 则原来的key-value会被丢弃。
         *
         * @param maps
         *            key-value 集
         *  2020/3/8 17:21:19
         */
        public static void multiSet(Map<String, String> maps) {
            log.info("multiSet(...) => maps -> {}", maps);
            redisTemplate.opsForValue().multiSet(maps);
        }

        /**
         * 当redis中,不存在任何一个keys时, 才批量设置 key-value, 并返回成功/失败.
         * 否者，不进行任何操作, 并返回false。
         *
         * 即: 假设调用此方法时传入的参数map是这样的: {k1=v1, k2=v2, k3=v3}
         *     那么redis中, k1、k2、k3都不存在时,才会批量设置key-value;
         *     否则不会设置任何key-value。
         *
         * 注: 若存在相同的key, 则原来的key-value会被丢弃。
         *
         * 注:
         *
         * @param maps
         *            key-value 集
         *
         * @return 操作是否成功
         *  2020/3/8 17:21:19
         */
        public static boolean multiSetIfAbsent(Map<String, String> maps) {
            log.info("multiSetIfAbsent(...) => maps -> {}", maps);
            Boolean result = redisTemplate.opsForValue().multiSetIfAbsent(maps);
            log.info("multiSetIfAbsent(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 增/减 整数
         *
         * 注: 负数则为减。
         * 注: 若key对应的value值不支持增/减操作(即: value不是数字)， 那么会
         *     抛出org.springframework.data.redis.RedisSystemException
         *
         * @param key
         *            用于定位value的key
         * @param increment
         *            增加多少
         * @return  增加后的总值。
         * @throws RedisSystemException key对应的value值不支持增/减操作时
         *  2020/3/8 17:45:51
         */
        public static long incrBy(String key, long increment) {
            log.info("incrBy(...) => key -> {}, increment -> {}", key, increment);
            Long result = redisTemplate.opsForValue().increment(key, increment);
            log.info("incrBy(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 增/减 浮点数
         *
         * 注: 慎用浮点数，会有精度问题。
         *     如: 先 RedisUtil.StringOps.set("ds", "123");
         *         然后再RedisUtil.StringOps.incrByFloat("ds", 100.6);
         *         就会看到精度问题。
         * 注: 负数则为减。
         * 注: 若key对应的value值不支持增/减操作(即: value不是数字)， 那么会
         *     抛出org.springframework.data.redis.RedisSystemException
         *
         * @param key
         *            用于定位value的key
         * @param increment
         *            增加多少
         * @return  增加后的总值。
         * @throws RedisSystemException key对应的value值不支持增/减操作时
         *  2020/3/8 17:45:51
         */
        public static double incrByFloat(String key, double increment) {
            log.info("incrByFloat(...) => key -> {}, increment -> {}", key, increment);
            Double result = redisTemplate.opsForValue().increment(key, increment);
            log.info("incrByFloat(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 追加值到末尾
         *
         * 注: 当redis中原本不存在key时,那么（从效果上来看）此方法就等价于{@link this#set(String, String)}
         *
         * @param key
         *            定位value的key
         * @param value
         *            要追加的value值
         * @return 追加后， 整个value的长度
         *  2020/3/8 17:59:21
         */
        public static int append(String key, String value) {
            log.info("append(...) => key -> {}, value -> {}", key, value);
            Integer result = redisTemplate.opsForValue().append(key, value);
            log.info("append(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 根据key，获取到对应的value值
         *
         * @param key
         *            key-value对应的key
         * @return  该key对应的值。
         *          注: 若key不存在， 则返回null。
         *
         *  2020/3/8 16:27:41
         */
        public static String get(String key) {
            log.info("get(...) => key -> {}", key);
            String result = redisTemplate.opsForValue().get(key);
            log.info("get(...) => result -> {} ", result);
            return result;
        }

        /**
         * 对(key对应的)value进行截取, 截取范围为[start, end]
         *
         * 注: 若[start, end]的范围不在value的范围中，那么返回的是空字符串 ""
         * 注: 若value只有一部分在[start, end]的范围中，那么返回的是value对应部分的内容(即:不足的地方，并不会以空来填充)
         *
         * @param key
         *            定位value的key
         * @param start
         *            起始位置 (从0开始)
         * @param end
         *            结尾位置 (从0开始)
         * @return  截取后的字符串
         *  2020/3/8 18:08:45
         */
        public static String getRange(String key, long start, long end) {
            log.info("getRange(...) => kry -> {}", key);
            String result = redisTemplate.opsForValue().get(key, start, end);
            log.info("getRange(...) => result -> {} ", result);
            return result;
        }

        /**
         * 给指定key设置新的value, 并返回旧的value
         *
         * 注: 若redis中不存在key, 那么此操作仍然可以成功， 不过返回的旧值是null
         *
         * @param key
         *            定位value的key
         * @param newValue
         *            要为该key设置的新的value值
         * @return  旧的value值
         *  2020/3/8 18:14:24
         */
        public static String getAndSet(String key, String newValue) {
            log.info("getAndSet(...) => key -> {}, value -> {}", key, newValue);
            String oldValue = redisTemplate.opsForValue().getAndSet(key, newValue);
            log.info("getAndSet(...) => oldValue -> {}", oldValue);
            return oldValue;
        }

        /**
         * 获取(key对应的)value在二进制下，offset位置的bit值。
         *
         * 注: 当offset的值在(二进制下的value的)索引范围外时, 返回的也是false。
         *
         * 示例:
         *      RedisUtil.StringOps.set("akey", "a");
         *      字符串a, 转换为二进制为01100001
         *      那么getBit("akey", 6)获取到的结果为false。
         *
         * @param key
         *            定位value的key
         * @param offset
         *            定位bit的索引
         * @return  offset位置对应的bit的值(true - 1, false - 0)
         *  2020/3/8 18:21:10
         */
        public static boolean getBit(String key, long offset) {
            log.info("getBit(...) => key -> {}, offset -> {}", key, offset);
            Boolean result = redisTemplate.opsForValue().getBit(key, offset);
            log.info("getBit(...) => result -> {}", result);
            if (result == null) {
                throw new RedisOpsResultIsNullException();
            }
            return result;
        }

        /**
         * 批量获取value值
         *
         * 注: 若redis中，对应的key不存在，那么该key对应的返回的value值为null
         *
         * @param keys
         *            key集
         * @return  value值集合
         *  2020/3/8 18:26:33
         */
        public static List<String> multiGet(Collection<String> keys) {
            log.info("multiGet(...) => keys -> {}", keys);
            List<String> result = redisTemplate.opsForValue().multiGet(keys);
            log.info("multiGet(...) => result -> {}", result);
            return result;
        }
    }

    /**
     * 提供一些基础功能支持
     *
     * @author JustryDeng
     *  2020/3/16 0:48:14
     */
    public static class Helper {

        /** 默认拼接符 */
        public static final String DEFAULT_SYMBOL = ":";

        /**
         * 拼接args
         *
         * @see Helper#joinBySymbol(String, String...)
         */
        public static String join(String... args) {
            return Helper.joinBySymbol(DEFAULT_SYMBOL, args);
        }

        /**
         * 使用symbol拼接args
         *
         * @param symbol
         *            分隔符， 如: 【:】
         * @param args
         *            要拼接的元素数组, 如: 【a b c】
         *
         * @return  拼接后的字符串, 如  【a:b:c】
         *  2019/9/8 16:11
         */
        public static String joinBySymbol(String symbol, String... args) {
            if (symbol == null || symbol.trim().length() == 0) {
                throw new RuntimeException(" symbol must not be empty!");
            }
            if (args == null || args.length == 0) {
                throw new RuntimeException(" args must not be empty!");
            }
            StringBuilder sb = new StringBuilder(16);
            for (String arg : args) {
                sb.append(arg).append(symbol);
            }
            sb.replace(sb.length() - symbol.length(), sb.length(), "");
            return sb.toString();
        }



    }public static class RedisOpsResultIsNullException extends NullPointerException {

        public RedisOpsResultIsNullException() {
            super();
        }

        public RedisOpsResultIsNullException(String message) {
            super(message);
        }
    }
}