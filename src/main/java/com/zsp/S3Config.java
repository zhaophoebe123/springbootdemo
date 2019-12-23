package com.zsp;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhaoshiping
 * @Date: Created in 10:35 2019/12/12
 * @Description:
 * @Version:
 */
@Configuration
public class S3Config {
    @Value("${aws.secret.accesskeyId}")
    private String awsId;
    @Value("${aws.secret.accesskey}")
    private String awsKey;
    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.endPoint}")
    private String endPoint;

    @Bean
    public AmazonS3 s3client(){

        ClientConfiguration config = new ClientConfiguration();

        AwsClientBuilder.EndpointConfiguration endpointConfig =
                new AwsClientBuilder.EndpointConfiguration(endPoint,region);

        AWSCredentials awsCredentials = new BasicAWSCredentials(awsId,awsKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

       AmazonS3 s3  = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(config)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .build();
       return s3;
    }

}
