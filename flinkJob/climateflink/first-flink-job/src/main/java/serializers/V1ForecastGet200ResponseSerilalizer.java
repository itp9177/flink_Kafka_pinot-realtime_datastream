package serializers;

import com.google.gson.Gson;
import dto.MyMsg1;
import org.apache.flink.api.common.serialization.SerializationSchema;
import com.itp.openapi.model.V1ForecastGet200Response;

public class V1ForecastGet200ResponseSerilalizer implements SerializationSchema<V1ForecastGet200Response> {

    @Override
    public byte[] serialize(V1ForecastGet200Response element) {
        return new Gson().toJson(element).getBytes();
    }
}