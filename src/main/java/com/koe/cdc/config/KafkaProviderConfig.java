package com.koe.cdc.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:kafka.properties")
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProviderConfig {

    @Value("${kafka.bootstrap-servers}")
    //指定kafka集群
    private String bootstrapServers;

    //指定发送消息失败重试后的次数
    @Value("${kafka.producer.retries}")
    private String retries;

    //指定超时时间
    @Value("${kafka.producer.timeout}")
    private String timeout;

    //指定key序列化器
    @Value("${kafka.producer.key-serializer}")
    private String keySerializer;

    //指定value序列化器
    @Value("${kafka.producer.value-serializer}")
    private String valueSerializer;

    public Map<String, Object> producerConfigs() {

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 设置重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
//        //达到batchSize大小的时候会发送消息
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
//        //延时时间，延时时间到达之后计算批量发送的大小没达到也发送消息
//        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
//        //缓冲区的值
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        // 序列化手段
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
//        //producer端的消息确认机制,-1和all都表示消息不仅要写入本地的leader中还要写入对应的副本中
//        props.put(ProducerConfig.ACKS_CONFIG, -1);
//        //单条消息的最大值以字节为单位,默认值为1048576
//        props.put(ProducerConfig.LINGER_MS_CONFIG, 10485760);
        // 设置broker响应时间，如果broker在60秒之内还是没有返回给producer确认消息，则认为发送失败
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, Integer.parseInt(timeout));
//        //指定拦截器(value为对应的class)
//        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.te.handler.KafkaProducerInterceptor");
//        //设置压缩算法(默认是木有压缩算法的)
//        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "LZ4");
        return props;
    }

    @Bean // 生产者工厂
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean // 模板发送消息
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * 此模板已经设置了topic的名称，使用的时候可以直接注入此bean然后调用setDefaultTopic方法
     *
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> defaultKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
