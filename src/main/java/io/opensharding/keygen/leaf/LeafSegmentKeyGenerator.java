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

import com.alibaba.druid.pool.DruidDataSource;
import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.dao.IDAllocDao;
import com.sankuai.inf.leaf.segment.dao.impl.IDAllocDaoImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;

/**
 * Key generator implemented by leaf segment algorithms.
 */
public final class LeafSegmentKeyGenerator implements ShardingKeyGenerator {

    public final static String TYPE = "LEAF_SEGMENT";

    @Getter
    @Setter
    private Properties properties;

    private IDGen idGen;
    private DruidDataSource dataSource;

    @Override
    public Comparable<?> generateKey() {
        if(this.dataSource == null){
            initDataSourceAndIDGen(this.properties);
        }
        Result result = this.idGen.get(properties.getProperty(LeafPropertiesConstant.LEAF_KEY));
        return result.getId();
    }

    @SneakyThrows
    public synchronized void initDataSourceAndIDGen(Properties properties){
        if(this.properties==null){
            this.setProperties(properties);
        }
        synchronized (this){
            if(this.dataSource == null) {
                DruidDataSource dataSource = new DruidDataSource();
                dataSource.setUrl(properties.getProperty(LeafPropertiesConstant.LEAF_JDBC_URL));
                dataSource.setUsername(properties.getProperty(LeafPropertiesConstant.LEAF_JDBC_USERNAME));
                dataSource.setPassword(properties.getProperty(LeafPropertiesConstant.LEAF_JDBC_PASSWORD));
                dataSource.init();
                IDAllocDao dao = new IDAllocDaoImpl(dataSource);
                this.idGen = new SegmentIDGenImpl();
                ((SegmentIDGenImpl) this.idGen).setDao(dao);
                this.idGen.init();
                this.dataSource = dataSource;
            }
        }
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(this.dataSource!=null) {
            this.dataSource.close();
        }
    }
}