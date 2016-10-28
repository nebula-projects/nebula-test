/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nebula.test.utils;

import org.apache.commons.configuration.PropertiesConfiguration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

  private JedisPool jedisPool;

  public RedisUtil() {
    PropertiesConfiguration testConfig;
    try {
      testConfig = new PropertiesConfiguration("test.properties");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxActive(10);
    config.setMaxIdle(10);
    config.setMaxWait(1000 * 10);
    config.setTestOnBorrow(true);

    jedisPool =
        new JedisPool(config, testConfig.getString("redis.host"), testConfig.getInt("redis.port"));
  }

  public void removeAllData() {

    Jedis jedis = jedisPool.getResource();
    try {
      jedis.flushDB();
    } finally {
      jedisPool.returnResource(jedis);
    }
  }
}
