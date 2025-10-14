package com.lxp.support;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap<>();

    static {
        loadQueries();
    }

    public static String getQuery(String key) {
        return queries.get(key);
    }

    private static void loadQueries() {
        try (InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("queries.xml")) {

            if (inputStream == null) {
                throw new RuntimeException("queries.xml not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("query");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String key = element.getAttribute("key");
                String sql = element.getTextContent().trim();
                queries.put(key, sql);
            }

        } catch (Exception e) {
            throw new RuntimeException("쿼리 로드 중 오류 발생", e);
        }
    }
}