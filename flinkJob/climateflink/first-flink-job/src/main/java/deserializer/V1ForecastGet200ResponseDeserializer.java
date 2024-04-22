package deserializer;

import com.google.gson.Gson;
import dto.MyMsg1;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class MyMsg1Deserializer implements DeserializationSchema<MyMsg1> {

    @Override
    public MyMsg1 deserialize(byte[] message) throws IOException {
        return new Gson().fromJson(new String(message), MyMsg1.class);
    }

    @Override
    public boolean isEndOfStream(MyMsg1 nextElement) {
        return false; // Assuming no end-of-stream signal
    }

    @Override
    public TypeInformation<MyMsg1> getProducedType() {
        return TypeInformation.of(MyMsg1.class);
    }
}