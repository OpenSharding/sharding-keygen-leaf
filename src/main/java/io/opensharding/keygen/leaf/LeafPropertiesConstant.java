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

public final class LeafPropertiesConstant {
    // for keyGenerator
    public final static String LEAF_KEY = "leaf.key";
    // for LeafSegment
    public final static String LEAF_JDBC_URL = "leaf.jdbc.url";
    public final static String LEAF_JDBC_USERNAME = "leaf.jdbc.username";
    public final static String LEAF_JDBC_PASSWORD = "leaf.jdbc.password";
    // for LeafSnowflake
    public final static String LEAF_ZK_LIST = "leaf.zk.list";
}
