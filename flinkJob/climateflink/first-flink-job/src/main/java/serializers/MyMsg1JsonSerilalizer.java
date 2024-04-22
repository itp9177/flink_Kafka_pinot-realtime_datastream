package serializers;

import com.google.gson.Gson;
import dto.MyMsg1;
import org.apache.flink.api.common.serialization.SerializationSchema;

public class MyMsg1JsonSerilalizer implements SerializationSchema<MyMsg1> {

    @Override
    public byte[] serialize(MyMsg1 element) {
        return new Gson().toJson(element).getBytes();
    }
}