package Goals;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class LoseWeightSerializer extends JsonSerializer<LoseWeight> {

    @Override
    public void serialize(LoseWeight loseWeight, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("goalType", "Lose Weight");
        jsonGenerator.writeNumberField("targetCalories", loseWeight.targetCalories());
        jsonGenerator.writeNumberField("targetFat", loseWeight.targetFat());
        jsonGenerator.writeNumberField("targetProtein", loseWeight.targetProtein());
        jsonGenerator.writeNumberField("targetFiber", loseWeight.targetFiber());
        jsonGenerator.writeNumberField("targetCarbs", loseWeight.targetCarbs());
        jsonGenerator.writeEndObject();
    }
}
