package Goals;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class GainWeightSerializer extends JsonSerializer<GainWeight> {

    @Override
    public void serialize(GainWeight gainWeight, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("goalType", "Gain Weight");
        jsonGenerator.writeNumberField("targetCalories", gainWeight.targetCalories());
        jsonGenerator.writeNumberField("targetFat", gainWeight.targetFat());
        jsonGenerator.writeNumberField("targetProtein", gainWeight.targetProtein());
        jsonGenerator.writeNumberField("targetFiber", gainWeight.targetFiber());
        jsonGenerator.writeNumberField("targetCarbs", gainWeight.targetCarbs());
        jsonGenerator.writeEndObject();
    }
}
