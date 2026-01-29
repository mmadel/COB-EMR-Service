package com.cob.emr.service.nppes;

import com.cob.emr.model.nppes.NPPESResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NPPESSearchService {

    @Value("${nppes.base.url}")
    private String nppesBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public NPPESResponse searchByFirstName(String firstName) {
        String url = nppesBaseUrl + "?version=2.1&first_name=" + firstName.toLowerCase() + "&limit=200";
        return restTemplate.getForObject(url, NPPESResponse.class);
    }

    public NPPESResponse searchByLastName(String lastName) {
        String url = nppesBaseUrl + "?version=2.1&last_name=" + lastName.toLowerCase() + "&limit=200";
        return restTemplate.getForObject(url, NPPESResponse.class);
    }

    public NPPESResponse searchByFirstAndLastName(String firstName, String lastName) {
        String url = nppesBaseUrl + "?version=2.1&first_name=" + firstName.toLowerCase()
                + "&last_name=" + lastName.toLowerCase() + "&limit=200";
        return restTemplate.getForObject(url, NPPESResponse.class);
    }

    public NPPESResponse searchByNPI(String npi) {
        String url = nppesBaseUrl + "?version=2.1&number=" + npi;
        return restTemplate.getForObject(url, NPPESResponse.class);
    }
}
