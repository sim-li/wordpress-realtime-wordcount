ZK=localhost:2181
KAFKA_HOME=/Users/funkjaymatada/code/challenges/thekey/kafka-docker/kafka_2.13-2.7.0

bin/kafka-topics.sh --create --topic streams-plaintext-input --bootstrap-server localhost:9092

bin/kafka-topics.sh --create --topic streams-wordcount-output --bootstrap-server localhost:9092

$KAFKA_HOME/bin/kafka-console-consumer.sh --topic=topic --bootstrap-server localhost:9092

$KAFKA_HOME/bin/kafka-console-producer.sh --topic=topic --broker-list=`./broker-list.sh`

bin/kafka-console-producer.sh --topic streams-plaintext-input --bootstrap-server localhost:9092

