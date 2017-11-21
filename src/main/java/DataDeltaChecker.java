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

        for (Map.Entry<String, Integer> countryCount1 : countByCountry1.entrySet()) {
            deltaByCountry.put(countryCount1.getKey(), countryCount1.getValue() - countByCountry2.get(countryCount1.getKey()));
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

        for (Map.Entry<String, Integer> countryCount1 : countByConstituency1.entrySet()) {
            deltaByConstituency.put(countryCount1.getKey(), countryCount1.getValue() - countByConstituency2.get(countryCount1.getKey()));
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
