
package flinkjob;

import dto.MyMsg1;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import serializers.MyMsg1JsonSerilalizer;

/**
 * Skeleton code for the datastream walkthrough
 */
public class climateflinkJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

     //   DataStream<String> mystream = env.fromElements("6","6","6").name("mydatasoruce");
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("http://localhost:9092")
                .setTopics("topic1")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();


   /*     KafkaSource<MyMsg1> source2 = KafkaSource.<MyMsg1>builder()
                .setBootstrapServers("http://localhost:9092")
                .setTopics("topic2")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new MyMsg1Deserializer())
                .build();*/

      //  DataStream<MyMsg1> kafkaDataStream= env.fromSource(source2, WatermarkStrategy.noWatermarks(), "Kafka Source");
        DataStream<String> kafkaDataStreamStr= env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source 2");

     /*   DataStream<String> kafkaData = kafkaDataStream.map(a->{

            return a+"pp";});
        KafkaSink<String> sink = KafkaSink.<String>builder()
                .setBootstrapServers("http://localhost:9092")
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic("topic1")
                        .setKeySerializationSchema(new SimpleStringSchema())
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build())
                .build();*/
     /*   KafkaRecordSerializationSchema<MyMsg1> serializer =
                KafkaRecordSerializationSchema.<MyMsg1>builder()
                        .setTopic("topic2")
                        .setValueSerializationSchema(
                                new JsonSerializationSchema<>()
                        )
                        .build();*/

        KafkaSink<MyMsg1> sink = KafkaSink.<MyMsg1>builder()
                .setBootstrapServers("http://localhost:9092")
                .setRecordSerializer(KafkaRecordSerializationSchema.<MyMsg1>builder()
                        .setTopic("topic2")
                        .setValueSerializationSchema(new MyMsg1JsonSerilalizer())
                        .build())
                .build();

      //  kafkaDataStream.map(MyMsg1::getName).print();
        DataStream<MyMsg1> kafkaData2 = kafkaDataStreamStr.map(a->{MyMsg1 p = new MyMsg1(); p.setAge("23");p.setName(a); return p;});
      //kafkaData.sinkTo(sink);
        kafkaData2.sinkTo(sink);
        env.execute("new job 2");


    }
}
