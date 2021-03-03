$KAFKA_HOME/bin/kafka-topics.sh --create --topic streams-plaintext-input --replication-factor 1 \
  --partitions 2  --zookeeper localhost:2181
  
$KAFKA_HOME/kafka_2.13-2.7.0/bin/kafka-topics.sh --create --topic streams-wordcount-output --replication-factor 1 \
  --partitions 2  --zookeeper localhost:2181

 