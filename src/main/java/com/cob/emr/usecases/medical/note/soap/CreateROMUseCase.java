package com.cob.emr.usecases.medical.note.soap;

import com.cob.emr.entity.medical.note.soap.profile.ROMModelEntity;
import com.cob.emr.model.medical.note.soap.profile.rom.ROMModel;
import com.cob.emr.repositories.medical.note.ROMModelEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateROMUseCase {
    @Autowired
    ROMModelEntityRepository romModelEntityRepository;
    @Autowired
    ModelMapper mapper;

    public String create(String romTestName, String htmlBody) throws JsonProcessingException {
        Document doc = Jsoup.parse(htmlBody, "UTF-8");

        // Find the parent div with the specified ID and class
        Element parentDiv = doc.select("div.rowdiv.inset-tests#divTShlAROM").first();


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
        List<ROMModel> romModelList = movementOptions.entrySet().stream()
                .map(entry -> new ROMModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        romModelEntityRepository.saveAll(
                romModelList.stream().map(romModel -> {
                            ROMModelEntity romModelEntity = mapper.map(romModel, ROMModelEntity.class);
                            romModelEntity.setRomTestName(romTestName);
                            return romModelEntity;
                        })
                        .collect(Collectors.toList())
        );
        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(movementOptions);
        return json;
    }
}
