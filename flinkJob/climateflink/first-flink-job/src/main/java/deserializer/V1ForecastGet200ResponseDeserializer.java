package deserializer;

import com.google.gson.Gson;
import dto.MyMsg1;
import com.itp.openapi.model.V1ForecastGet200Response;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class V1ForecastGet200ResponseDeserializer implements DeserializationSchema<V1ForecastGet200Response> {

    @Override
    public V1ForecastGet200Response deserialize(byte[] message) throws IOException {
        return new Gson().fromJson(new String(message), V1ForecastGet200Response.class);
    }

    @Override
    public boolean isEndOfStream(V1ForecastGet200Response nextElement) {
        return false; // Assuming no end-of-stream signal
    }

    @Override
    public TypeInformation<V1ForecastGet200Response> getProducedType() {
        return TypeInformation.of(V1ForecastGet200Response.class);
    }
}