//package xyz.lischka.textanalysis.infrastructure.config
//
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.clients.producer.ProducerConfig
//import org.springframework.context.annotation.Bean
//import org.springframework.kafka.core.DefaultKafkaProducerFactory
//import org.springframework.kafka.core.ProducerFactory
//
//class KafkaTestContainerConfig {
//    @Bean
//    fun consumerConfigs(): Map<String, Any>? {
//        val props: MutableMap<String, Any> = HashMap()
//        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafka.getBootstrapServers()
//        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
//        props[ConsumerConfig.GROUP_ID_CONFIG] = "baeldung"
//        // more standard configuration
//        return props
//    }
//
//    @Bean
//    fun producerFactory(): ProducerFactory<String?, String?>? {
//        val configProps: MutableMap<String, Any> = HashMap()
//        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafka.getBootstrapServers()
//        // more standard configuration
//        return DefaultKafkaProducerFactory(configProps)
//    }
//}