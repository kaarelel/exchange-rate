package com.exchangerate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.*;

@Service
public class ECBClient {
    private static final Logger logger = LoggerFactory.getLogger(ECBClient.class);

    private static final String ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    public Map<LocalDate, Map<String, Double>> fetchRates() throws Exception {
        logger.info("Fetching ECB rates from URL: {}", ECB_URL);

        trustAllSSL();
        InputStream inputStream = new URL(ECB_URL).openStream();
        InputSource input = new InputSource(new InputStreamReader(inputStream));

        Map<LocalDate, Map<String, Double>> result = new TreeMap<>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(input);

        NodeList cubes = doc.getElementsByTagName("Cube");
        for (int i = 0; i < cubes.getLength(); i++) {
            Element cube = (Element) cubes.item(i);
            if (cube.hasAttribute("time")) {
                LocalDate date = LocalDate.parse(cube.getAttribute("time"));
                Map<String, Double> dailyRates = new HashMap<>();
                dailyRates.put("EUR", 1.0); // ECB default base
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

    private void trustAllSSL() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}