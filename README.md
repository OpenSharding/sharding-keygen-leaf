# Sharding Keygen Leaf

Using [Meituan LeafSegment & LeafSnowflake](https://github.com/Meituan-Dianping/Leaf) to implement two ShardingKeyGenerator with ShardingSphere.
More information about the two algorithms also see: [Leaf key generator introduce](https://tech.meituan.com/2019/03/07/open-source-project-leaf.html).

## Prerequisites

The tools or libraries should installed before build this project:
1. Java7+
2. Git
3. Maven3+
4. A database(for using Leaf-Segment key generator, MySQL etc.)
5. A Zookeeper(for using Leaf-Snowflake key generator)

## Build & Test

Cause Leaf project isn't deployed to Maven Central Repository util now, you should execute `build.sh`, the script will:
1. git clone the leaf project;
2. mvn install to package and install leaf library;
3. mvn install to package sharding-keygen-leaf project.

Then it's ready to used in your own project.

For using Leaf-Segment key generator, this SQL should executed in MySQL first:
```$mysql
CREATE DATABASE leaf;

CREATE TABLE `leaf_alloc` (
  `biz_tag` varchar(128)  NOT NULL DEFAULT '', -- your biz unique name
  `max_id` bigint(20) NOT NULL DEFAULT '1',
  `step` int(11) NOT NULL,
  `description` varchar(256)  DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB;

insert into leaf_alloc(biz_tag, max_id, step, description) values('sstest', 1, 2000, 'Test leaf Segment Mode Get Id');
```

## Examples

In tests of this project, you can see:
1. Configuration parameters in `leaf.properties`;
2. Example codes in test classes.

## Using in ShardingSphere config file

Step1: You just need refer dependency in you pom.xml:
```$xml
    <dependency>
        <groupId>io.opensharding</groupId>
        <artifactId>sharding-keygen-leaf</artifactId>
        <version>5.0.0-RC1-SNAPSHOT</version>
    </dependency>
```

Step2: And then config this parameter in your rule,
Using Leaf Segment via Database:
```$yaml
keyGenerator: LEAF_SEGMENT
```

Or using Leaf Snowflake via zookeeper:
```$yaml
keyGenerator: LEAF_SNOWFLAKE
```
