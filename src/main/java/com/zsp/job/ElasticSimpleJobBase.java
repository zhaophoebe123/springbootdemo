package com.zsp.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public abstract class ElasticSimpleJobBase implements SimpleJob {

    @Resource(name="regCenter")
    private ZookeeperRegistryCenter regCenter;
    
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 分片数
     */
    private Integer shardingTotalCount;
    /**
     * 分片参数
     */
    private String shardingItemParameters;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 批量导入Mysql的工作线程模板名称
     */
    private String runnerName;

    /**
     * 初始化操作，注入job
     */
    @PostConstruct
    public void init() {
        elasticJobSimpleScheduler(this);
    }

    public void elasticJobSimpleScheduler(ElasticSimpleJobBase simpleJobBase) {
        new SpringJobScheduler(simpleJobBase, regCenter, getLiteJobConfiguration(simpleJobBase.getClass(),
                simpleJobBase.getCron(), simpleJobBase.getShardingTotalCount(), simpleJobBase.getShardingItemParameters(),
                simpleJobBase.getDescription())).init();
    }

    /**
     * 
     * @param jobClass 任务类
     * @param cron 定时任务表达式
     * @param shardingTotalCount 分片数
     * @param shardingItemParameters 分片参数
     * @param description 任务描述
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
                                                         final int shardingTotalCount, final String shardingItemParameters, final String description) {
        if (StringUtils.isEmpty(jobName)) {
            jobName = jobClass.getName().substring(jobClass.getName().lastIndexOf(".") + 1);
        }
        return LiteJobConfiguration
                .newBuilder(
                        new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                                .shardingItemParameters(shardingItemParameters).description(description).build(), jobClass
                                .getCanonicalName())).overwrite(true).build();

    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getShardingTotalCount() {
        return shardingTotalCount;
    }

    public void setShardingTotalCount(Integer shardingTotalCount) {
        this.shardingTotalCount = shardingTotalCount;
    }

    public String getShardingItemParameters() {
        return shardingItemParameters;
    }

    public void setShardingItemParameters(String shardingItemParameters) {
        this.shardingItemParameters = shardingItemParameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }
}
