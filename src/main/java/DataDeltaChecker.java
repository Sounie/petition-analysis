import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Comparing data from different dates - or petitions.
 */
public class DataDeltaChecker {
    /**
     * Calculate differences and write them to stdout.
     */
    public void spotTheCountryDifferences(PetitionData data1, PetitionData data2) {
        int maxCountries = data1.calculateTotal();
        if (data2.calculateTotal() > maxCountries) {
            maxCountries = data2.calculateTotal();
        }

        Map<String, Integer> deltaByCountry = new HashMap<>(maxCountries);
        Map<String, Integer> countByCountry1 = data1.getCountByCountry();
        Map<String, Integer> countByCountry2 = data2.getCountByCountry();

        for (String country : countByCountry1.keySet()) {
            deltaByCountry.put(country, countByCountry1.get(country) - countByCountry2.get(country));
        }

        // FIXME - determine difference between set keys
        List<Map.Entry<String, Integer>> sortedByConstituency = deltaByCountry.entrySet().stream().sorted(
                (e1, e2) -> e1.getValue().compareTo(e2.getValue())
        ).collect(toList());

        sortedByConstituency.stream().forEach(
                countryDiff -> System.out.println(countryDiff.getKey() + " " + countryDiff.getValue())
        );
    }

    public void spotTheConstituencyyDifferences(PetitionData data1, PetitionData data2) {
        int maxConstituencies = data1.calculateTotal();
        if (data2.calculateTotal() > maxConstituencies) {
            maxConstituencies = data2.calculateTotal();
        }

        Map<String, Integer> deltaByConstituency = new HashMap<>(maxConstituencies);
        Map<String, Integer> countByConstituency1 = data1.getCountByConstituency();
        Map<String, Integer> countByConstituency2 = data2.getCountByConstituency();

        for (String country : countByConstituency1.keySet()) {
            deltaByConstituency.put(country, countByConstituency1.get(country) - countByConstituency2.get(country));
        }

        // FIXME - determine difference between set keys
        List<Map.Entry<String, Integer>> sortedByConstituency = deltaByConstituency.entrySet().stream().sorted(
                (e1, e2) -> e1.getValue().compareTo(e2.getValue())
        ).collect(toList());

        sortedByConstituency.stream().forEach(
                constituencyDiff -> System.out.println(constituencyDiff.getKey() + " " + constituencyDiff.getValue())
        );
    }
}
