import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class PetitionDataParser {

    public PetitionData parse(String jsonString) {
        PetitionData petitionData;

        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse(jsonString);

        JsonObject jsonObject = element.getAsJsonObject();

        JsonObject data = jsonObject.getAsJsonObject("data");

        JsonObject attributes = data.getAsJsonObject("attributes");

        String petitionAction = attributes.get("action").getAsString();

        JsonArray byCountry = attributes.getAsJsonArray("signatures_by_country");

        JsonArray byConstituency = attributes.getAsJsonArray("signatures_by_constituency");

        Map<String, Integer> countsByCountry = new HashMap<>();

        byCountry.forEach(
                i -> {
                    JsonObject country = i.getAsJsonObject();
                    String name = country.get("name").getAsString();
                    int count = country.get("signature_count").getAsInt();
                    countsByCountry.put(name, count);
                }
        );

        Map<String, Integer> countsByConstituency = new HashMap<>();

        byConstituency.forEach(
                i -> {
                    JsonObject country = i.getAsJsonObject();
                    String name = country.get("name").getAsString();
                    int count = country.get("signature_count").getAsInt();
                    countsByConstituency.put(name, count);
                }
        );

        petitionData = new PetitionData(petitionAction, countsByCountry, countsByConstituency);

        return petitionData;
    }
}
