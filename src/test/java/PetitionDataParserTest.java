import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

// TODO: This isn't really about unit tests, just a way of driving out some parsing and output of data
public class PetitionDataParserTest {
    @Test
    public void shouldParseValidJsonFromString() throws IOException {
        InputStream stream = PetitionDataParserTest.class.getResourceAsStream("2nd-referendum-data.json");

        String jsonString;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            jsonString = buffer.lines().collect(Collectors.joining("\n"));
        }

        PetitionDataParser parser = new PetitionDataParser();
        PetitionData petitionData = parser.parse(jsonString);

        Map<String, Integer> countByCountry = petitionData.getCountByCountry();

        List<Map.Entry<String, Integer>> sorted = countByCountry.entrySet().stream().sorted(
                (e1, e2) -> e1.getValue().compareTo(e2.getValue())
        ).collect(toList());

//        sorted.forEach(
//                e -> System.out.println(e.getKey() + ": " + e.getValue())
//        );

        Map<String, Integer> countByConstituency = petitionData.getCountByConstituency();

        List<Map.Entry<String, Integer>> sortedByConstituency = countByConstituency.entrySet().stream().sorted(
                (e1, e2) -> e1.getValue().compareTo(e2.getValue())
        ).collect(toList());

        sortedByConstituency.forEach(
                e -> System.out.println(e.getKey() + ": " + e.getValue())
        );

//        System.out.println("Total count:  " + petitionData.calculateTotal());
    }
}