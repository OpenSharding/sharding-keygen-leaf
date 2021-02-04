/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opensharding.keygen.leaf;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.snowflake.SnowflakeIDGenImpl;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;

/**
 * Key generator implemented by leaf snowflake algorithms.
 */
public final class LeafSnowflakeKeyGenerator implements ShardingKeyGenerator {

    public final static String TYPE = "LEAF_SNOWFLAKE";

    @Getter
    @Setter
    private Properties properties;

    private volatile IDGen idGen;

    @Override
    public Comparable<?> generateKey() {
        if(this.idGen == null) {
            synchronized (this) {
                if(this.idGen == null) {
                    IDGen idGen = new SnowflakeIDGenImpl(properties.getProperty(LeafPropertiesConstant.LEAF_ZK_LIST), 8089);
                    this.idGen = idGen;
                }
            }
        }
        return this.idGen.get(properties.getProperty(LeafPropertiesConstant.LEAF_KEY)).getId();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
