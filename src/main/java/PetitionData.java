import java.util.Collections;
import java.util.Map;

public class PetitionData {
    private String name;

    private final Map<String, Integer> countByCountry;
    private final Map<String, Integer> countByConstituency;

    public PetitionData(String name,
                        Map<String, Integer> countByCountry,
                        Map<String, Integer> countByConstituency) {
        this.name = name;
        this.countByCountry = countByCountry;
        this.countByConstituency = countByConstituency;
    }

    public Map<String, Integer> getCountByCountry() {
        return Collections.unmodifiableMap(countByCountry);
    }

    public Map<String, Integer> getCountByConstituency() {
        return Collections.unmodifiableMap(countByConstituency);
    }

    public int calculateTotal() {
        return countByCountry.entrySet().stream()
                .mapToInt(e -> e.getValue())
        .sum();
    }

    public String getName() {
        return name;
    }
}
