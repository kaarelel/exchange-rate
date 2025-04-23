package com.exchangerate.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.*;

@Service
public class ECBClient {
    private static final Logger logger = LoggerFactory.getLogger(ECBClient.class);

    public Map<LocalDate, Map<String, Double>> fetchRates() throws Exception {
        logger.info("Mock test load: reading ECB sample XML from classpath.");
        InputSource input = new InputSource(new InputStreamReader(new ClassPathResource("mock-ecb.xml").getInputStream()));

        Map<LocalDate, Map<String, Double>> result = new TreeMap<>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(input);

        NodeList cubes = doc.getElementsByTagName("Cube");
        for (int i = 0; i < cubes.getLength(); i++) {
            Element cube = (Element) cubes.item(i);
            if (cube.hasAttribute("time")) {
                LocalDate date = LocalDate.parse(cube.getAttribute("time"));
                Map<String, Double> dailyRates = new HashMap<>();
                NodeList children = cube.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j) instanceof Element child && child.hasAttribute("currency")) {
                        dailyRates.put(child.getAttribute("currency"),
                                Double.parseDouble(child.getAttribute("rate")));
                    }
                }
                result.put(date, dailyRates);
            }
        }

        logger.info("Parsed {} days of mock exchange rates.", result.size());
        return result;
    }
}
