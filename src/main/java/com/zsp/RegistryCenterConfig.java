package com.zsp;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zsp.job.Demo1Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elastic-job 注册中心配置
 *
 * @author zhaoshiping
 */
@Configuration
@ConditionalOnExpression("'${regCenter.serverLists}'.length() > 0")
public class RegistryCenterConfig {
    /**
     * @param serverList zookeeper 地址列表
     * @param namespace  注册中心名称
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${regCenter.serverLists}") final String serverList,
                                             @Value("${regCenter.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

    @Bean
    @ConfigurationProperties(prefix = "ik")
    @ConditionalOnProperty(prefix = "ik", name = "cron")
    public Demo1Job demo1Job() {
        return new Demo1Job();
    }

}
