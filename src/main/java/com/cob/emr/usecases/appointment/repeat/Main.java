package com.cob.emr.usecases.appointment.repeat;
import com.cob.emr.usecases.medical.note.util.CreateSOAPFieldName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        System.out.println(String.format("%08d", ThreadLocalRandom.current().nextInt(0, 100000000)));
    }
    public static Map<String, Map<String, String[]>> parseMovementOptions(File htmlFile) throws IOException {
        // Parse the HTML file with Jsoup
        Document doc = Jsoup.parse(htmlFile, "UTF-8");

        // Find the parent div with the specified ID and class
        Element parentDiv = doc.select("div.rowdiv.inset-tests#divTShlAROM").first();
        if (parentDiv == null) {
            return new HashMap<>(); // Return an empty map if parent div is not found
        }

        Map<String, Map<String, String[]>> movementOptions = new HashMap<>();

        // Get all "rowdiv" divs, skipping the first and second ones
        Elements rowDivs = parentDiv.select("div.rowdiv");

        // Loop through rowDivs starting from the third element (index 2)
        for (int i = 2; i < rowDivs.size(); i++) {
            Element rowDiv = rowDivs.get(i);

            // Extract the movement name from col1div
            String movementName = rowDiv.select("div.col1div").text().trim();

            // Extract all options from the select element
            Elements options = rowDiv.select("div.colRightdiv select option");
            String[] optionValues = options.stream().map(option -> option.text().trim()).toArray(String[]::new);

            if (!movementName.isEmpty() && optionValues.length > 0) {
                // Map both 'right' and 'left' keys to the same options
                movementOptions.put(movementName, new HashMap<String, String[]>() {{
                    put("right", optionValues);
                    put("left", optionValues);
                }});
            }
        }

        return movementOptions;
    }
}
