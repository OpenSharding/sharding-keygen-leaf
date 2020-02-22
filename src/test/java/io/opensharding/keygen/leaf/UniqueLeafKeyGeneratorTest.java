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

import com.sankuai.inf.leaf.common.PropertyFactory;
import org.apache.shardingsphere.spi.algorithm.keygen.ShardingKeyGeneratorServiceLoader;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.junit.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class UniqueLeafKeyGeneratorTest {

    ShardingKeyGenerator keyGenerator;

    public abstract String getLeafType();

    public void before() throws IOException, SQLException {
        if(this.getLeafType() != null) {
            Properties properties = PropertyFactory.getProperties();
            keyGenerator = new ShardingKeyGeneratorServiceLoader().newService(this.getLeafType(), properties);
        }
    }

    public void testGetId() {
        if(this.getLeafType() != null) {
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < 100; ++i) {
                Object r = keyGenerator.generateKey();
                System.out.println(r);
                if (((Long) r).longValue() > 0) list.add(r);
            }
            Assert.assertEquals(100, list.size());
        }
    }

}
