
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
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.types.Row;

import org.apache.pinot.common.utils.http.HttpClient;
import org.apache.pinot.connector.flink.common.FlinkRowGenericRowConverter;
import org.apache.pinot.connector.flink.http.PinotConnectionUtils;
import org.apache.pinot.connector.flink.sink.PinotSinkFunction;
import org.apache.pinot.controller.helix.ControllerRequestClient;
import org.apache.pinot.spi.config.table.TableConfig;
import org.apache.pinot.spi.data.Schema;
import org.apache.pinot.spi.utils.builder.ControllerRequestURLBuilder;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.typeutils.RowTypeInfo;

/**
 * Skeleton code for the datastream walkthrough
 */
public class climateflinkJob {
    public static void main(String[] args) throws Exception {

        final TypeInformation<?>[] typeInformation = { Types.STRING, Types.STRING };
        final String[] fields = { "name", "add" };
        final RowTypeInfo TEST_TYPE_INFO = new RowTypeInfo(typeInformation, fields);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        System.out.println("9094");

        KafkaSource<V1ForecastGet200Response> source = KafkaSource.<V1ForecastGet200Response>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("wea2")
                .setProperty("partition.discovery.interval.ms", "10000")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new V1ForecastGet200ResponseDeserializer())
                .build();

        KafkaSource<V1ForecastGet200Response> source2 = KafkaSource.<V1ForecastGet200Response>builder()
                .setBootstrapServers("kafka:9092")
                .setTopics("wea2")
                .setProperty("partition.discovery.interval.ms", "10000")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new V1ForecastGet200ResponseDeserializer())
                .build();

        PrintSinkFunction<V1ForecastGet200Response> printSink = new PrintSinkFunction<>();

        DataStream<V1ForecastGet200Response> kafkaDataStreamStr = env
                .fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source 2").setParallelism(2).name("kafka source of climate data 1");
        DataStream<V1ForecastGet200Response> kafkaDataStreamStr2 = env
                .fromSource(source2, WatermarkStrategy.noWatermarks(), "Kafka Source 3").setParallelism(3).name("kafka source of climate data 2")
                .name("kafka3");

        /*
         * KafkaSink<V1ForecastGet200Response> sink =
         * KafkaSink.<V1ForecastGet200Response>builder()
         * .setBootstrapServers("kafka")
         * .setRecordSerializer(KafkaRecordSerializationSchema.<V1ForecastGet200Response
         * >builder()
         * .setTopic("wea4")
         * .setValueSerializationSchema(new V1ForecastGet200ResponseSerilalizer())
         * .build())
         * .build();
         */
        // KafkaSink<String> sink = kafkaDataStreamStr.map(a->a.get)
        DataStream<V1ForecastGet200Response> dataStream1 = kafkaDataStreamStr.union(kafkaDataStreamStr2);
        DataStream<V1ForecastGet200Response> dataStream = dataStream1
                .map(new MapFunction<V1ForecastGet200Response, V1ForecastGet200Response>() {
                    @Override
                    public V1ForecastGet200Response map(V1ForecastGet200Response value) throws Exception {
                        return value;
                    }
                }).setParallelism(3).name("Stream processing of data");

        DataStream<Row> dataStream3 = dataStream.map(data -> {
            String name = "test name";
            String add = "add";
            return Row.of(name, add);
        }).returns(TEST_TYPE_INFO).setParallelism(2).name("stream to pinot RowTypeInfo");

        String url = "pinot-controller:9000";
        dataStream.print().name("print data 1");
        dataStream.addSink(printSink).name("print data");

        HttpClient httpClient = HttpClient.getInstance();
        ControllerRequestClient client = new ControllerRequestClient(
        ControllerRequestURLBuilder.baseUrl("http://pinot-controller:9000"), httpClient);
        Schema schema = PinotConnectionUtils.getSchema(client, "test1");
        TableConfig tableConfig = PinotConnectionUtils.getTableConfig(client, "test1", "OFFLINE");
        dataStream3.addSink(new PinotSinkFunction<>(new FlinkRowGenericRowConverter(TEST_TYPE_INFO), tableConfig, schema)).name("sink to pinot instance");
        
        // kafkaData.sinkTo(sink);
        // kafkaData2.sinkTo(sink);
        env.execute("new job 2");
    }
}
