package Goals;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class MaintainWeightSerializer extends JsonSerializer<MaintainWeight> {

    @Override
    public void serialize(MaintainWeight maintainWeight, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("goalType", "Maintain Weight");
        jsonGenerator.writeNumberField("targetCalories", maintainWeight.targetCalories());
        jsonGenerator.writeNumberField("targetFat", maintainWeight.targetFat());
        jsonGenerator.writeNumberField("targetProtein", maintainWeight.targetProtein());
        jsonGenerator.writeNumberField("targetFiber", maintainWeight.targetFiber());
        jsonGenerator.writeNumberField("targetCarbs", maintainWeight.targetCarbs());
        jsonGenerator.writeEndObject();
    }
}
