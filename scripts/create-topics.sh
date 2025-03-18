#https://kafka.apache.org/30/generated/topic_config.html
#https://dev.to/devshawn/apache-kafka-topic-naming-conventions-3do6
sudo docker exec broker opt/kafka/bin/kafka-topics.sh --create --topic alerts-cdc-conditions --partitions 1 --replication-factor 1 --bootstrap-server broker:9092 --config retention.ms=-1 --config cleanup.policy=delete
sudo docker exec broker opt/kafka/bin/kafka-topics.sh --create --topic mails-cmd-send --partitions 1 --replication-factor 1 --bootstrap-server broker:9092
