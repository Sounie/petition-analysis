import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

// TODO: This isn't really about unit tests, just a way of driving out some parsing and output of data
public class PetitionDataParserTest {
    @Test
    public void shouldParseValidJsonFromString() throws IOException {
//        InputStream stream = PetitionDataParserTest.class.getResourceAsStream("2nd-referendum-data.json");
        InputStream stream = PetitionDataParserTest.class.getResourceAsStream("australia-canada-nz-free-movement.json");

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

        sorted.forEach(
                e -> System.out.println(e.getKey() + ": " + e.getValue())
        );

        Map<String, Integer> countByConstituency = petitionData.getCountByConstituency();

        List<Map.Entry<String, Integer>> sortedByConstituency = countByConstituency.entrySet().stream().sorted(
                (e1, e2) -> e1.getValue().compareTo(e2.getValue())
        ).collect(toList());
//
//        sortedByConstituency.forEach(
//                e -> System.out.println(e.getKey() + ": " + e.getValue())
//        );

//        System.out.println("Total count:  " + petitionData.calculateTotal());
    }

    @Test
    public void testDeltaByCountry() throws Exception {
        InputStream stream = PetitionDataParserTest.class.getResourceAsStream("2nd-referendum-data.json");
        InputStream streamBots = PetitionDataParserTest.class.getResourceAsStream("2nd-referendum-data-including-bots.json");

        PetitionData countsByCountry = dataFromStream(stream);

        PetitionData countsByCountryWithBots = dataFromStream(streamBots);

        DataDeltaChecker checker = new DataDeltaChecker();
        checker.spotTheCountryDifferences(countsByCountryWithBots, countsByCountry);
    }

    @Test
    public void testDeltaByConstituency() throws Exception {
        InputStream stream = PetitionDataParserTest.class.getResourceAsStream("2nd-referendum-data.json");
        InputStream streamBots = PetitionDataParserTest.class.getResourceAsStream("2nd-referendum-data-including-bots.json");

        PetitionData data = dataFromStream(stream);

        PetitionData dataWithBots = dataFromStream(streamBots);

        DataDeltaChecker checker = new DataDeltaChecker();
        checker.spotTheConstituencyyDifferences(data, dataWithBots);
    }

    private PetitionData dataFromStream(InputStream stream) throws IOException {
        String jsonString;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            jsonString = buffer.lines().collect(Collectors.joining("\n"));
        }

        PetitionDataParser parser = new PetitionDataParser();
        return parser.parse(jsonString);
    }
}