package com.exchangerate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class ECBClient {
    private static final Logger logger = LoggerFactory.getLogger(ECBClient.class);

    @Value("${ecb.url:https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml}")
    private String ecbUrl;

    public Map<LocalDate, Map<String, Double>> fetchRates() throws Exception {
        try (InputStream inputStream = openEcbStream()) {
            logger.info("Fetching ECB rates from URL: {}", ecbUrl);
            return parseRates(inputStream);
        }
    }

    protected InputStream openEcbStream() throws Exception {
        return new URL(ecbUrl).openStream();
    }

    protected Map<LocalDate, Map<String, Double>> parseRates(InputStream stream) throws Exception {
        InputSource input = new InputSource(new InputStreamReader(stream));

        Map<LocalDate, Map<String, Double>> result = new TreeMap<>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(input);

        NodeList cubes = doc.getElementsByTagName("Cube");
        for (int i = 0; i < cubes.getLength(); i++) {
            Element cube = (Element) cubes.item(i);
            if (cube.hasAttribute("time")) {
                LocalDate date = LocalDate.parse(cube.getAttribute("time"));
                Map<String, Double> dailyRates = new HashMap<>();
                dailyRates.put("EUR", 1.0);
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

        logger.info("Parsed {} days of ECB exchange rates.", result.size());
        return result;
    }
}