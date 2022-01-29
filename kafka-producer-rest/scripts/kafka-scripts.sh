confluent local services start

# it will be created by application automatically:
# kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic users

kafka-console-consumer --bootstrap-server localhost:9092 --topic users --from-beginning --property print.key=true --property key.separator=":"

confluent local services stop
confluent local destroy