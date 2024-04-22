
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
import serializers.V1ForecastGet200ResponseSerilalizer;
import com.itp.openapi.model.V1ForecastGet200Response;
import deserializer.V1ForecastGet200ResponseDeserializer;
/**
 * Skeleton code for the datastream walkthrough
 */
public class climateflinkJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

     //   DataStream<String> mystream = env.fromElements("6","6","6").name("mydatasoruce");
        KafkaSource<V1ForecastGet200Response> source = KafkaSource.<V1ForecastGet200Response>builder()
                .setBootstrapServers("http://localhost:9092")
                .setTopics("topic1")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new V1ForecastGet200ResponseDeserializer())
                .build();


   /*     KafkaSource<MyMsg1> source2 = KafkaSource.<MyMsg1>builder()
                .setBootstrapServers("http://localhost:9092")
                .setTopics("topic2")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new MyMsg1Deserializer())
                .build();*/

      //  DataStream<MyMsg1> kafkaDataStream= env.fromSource(source2, WatermarkStrategy.noWatermarks(), "Kafka Source");
        DataStream<V1ForecastGet200Response> kafkaDataStreamStr= env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source 2");

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

        KafkaSink<V1ForecastGet200Response> sink = KafkaSink.<V1ForecastGet200Response>builder()
                .setBootstrapServers("http://localhost:9092")
                .setRecordSerializer(KafkaRecordSerializationSchema.<V1ForecastGet200Response>builder()
                        .setTopic("topic2")
                        .setValueSerializationSchema(new V1ForecastGet200ResponseSerilalizer())
                        .build())
                .build();

      //  kafkaDataStream.map(MyMsg1::getName).print();
        DataStream<V1ForecastGet200Response> kafkaData2 = kafkaDataStreamStr.map(a->{V1ForecastGet200Response p = new V1ForecastGet200Response(); return p;});
      //kafkaData.sinkTo(sink);
        kafkaData2.sinkTo(sink);
        env.execute("new job 2");


    }
}
