package com.sogal.config;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Properties;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class Configuration {

    private final static Logger logger =  Logger.getLogger(Configuration.class);

    private Properties props = null;

    public Configuration(){
        props = new Properties();
    }

    public void init(String fileName) throws Exception {
        if (null == fileName) {
            throw new RuntimeException("The configuration file name can not be null!");
        }
        InputStream is = Configuration.class.getClassLoader().getResourceAsStream(fileName);
        if (null == is) {
            File modelFile = new File(fileName);
            if (!modelFile.exists()) {
                throw new Exception("Can not find file :" + fileName);
            }
            try {
                is = new FileInputStream(modelFile);
            } catch (FileNotFoundException e) {
                throw new Exception("Fail to load file :" + fileName, e);
            }
        }
        try {
            props.load(is);
        } catch (IOException e) {
            throw new Exception("Can not load properties from input stream :", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new Exception("Can not close file :" + fileName, e);
            }
        }
    }

    /**
     * load xml resource file
     **/
    private void loadResource(Properties properties, Object name, String fileName) throws Exception {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setIgnoringComments(true);
            docBuilderFactory.setNamespaceAware(true);
            try {
                docBuilderFactory.setXIncludeAware(true);
            } catch (UnsupportedOperationException e) {
                logger.error("Failed to set setXIncludeAware(true) for parser " + docBuilderFactory + ":" + e, e);
                throw new Exception("Fail to parse file:" + fileName);
            }
            DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
            Document doc = null;
            Element root = null;
            if (name instanceof InputStream) {
                doc = builder.parse((InputStream) name);
            } else if (name instanceof Element) {
                root = (Element) name;
            }
            if (root == null) {
                root = doc.getDocumentElement();
            }
            if (!"configuration".equals(root.getTagName())) {
                throw new Exception("The bad configuration file: top-level element not <configuration>");
            }

            NodeList props = root.getChildNodes();
            for (int i = 0; i < props.getLength(); i++) {
                Node propNode = props.item(i);
                if (!(propNode instanceof Element)) continue;
                Element prop = (Element) propNode;
                if ("configuration".equals(prop.getTagName())) {
                    loadResource(properties, prop, fileName);
                    continue;
                }
                if (!"property".equals(prop.getTagName()))
                    logger.warn("The bad configuration file: element not <property>");
                NodeList fields = prop.getChildNodes();
                String attr = null;
                String value = null;
                for (int j = 0; j < fields.getLength(); j++) {
                    Node fieldNode = fields.item(j);
                    if (!(fieldNode instanceof Element)) continue;
                    Element field = (Element) fieldNode;
                    if ("name".equals(field.getTagName()) && field.hasChildNodes())
                        attr = ((Text) field.getFirstChild()).getData().trim();
                    if ("value".equals(field.getTagName()) && field.hasChildNodes())
                        value = ((Text) field.getFirstChild()).getData();
                }
                if (attr != null && value != null) {
                    properties.setProperty(attr, value);
                }
            }
        } catch (IOException e) {
            logger.error("error parsing conf file: " + e);
            throw new Exception(e);
        } catch (DOMException e) {
            logger.error("error parsing conf file: " + e);
            throw new Exception(e);
        } catch (SAXException e) {
            logger.error("error parsing conf file: " + e);
            throw new Exception(e);
        } catch (ParserConfigurationException e) {
            logger.error("error parsing conf file: " + e);
            throw new Exception(e);
        }
    }

    public String getString(String paramName) {
        return null == paramName ? null : props.getProperty(paramName);
    }

    public String getString(String paramName, String defaultValue) {
        String strValue = null == paramName ? null : props.getProperty(paramName);
        if (strValue == null || "".equals(strValue)) {
            return defaultValue;
        }

        return strValue;
    }

    public String[] getStrings(String paramName) {
        String strValue = null == paramName ? null : props.getProperty(paramName);
        if (strValue == null || "".equals(strValue)) {
            return null;
        }

        return strValue.split(",");
    }

    public int getInt(String paramName, int defaultValue) {
        String strValue = null == paramName ? null : props.getProperty(paramName);
        if (strValue == null || "".equals(strValue)) {
            return defaultValue;
        }
        return Integer.parseInt(strValue);
    }

    public long getLong(String paramName, long defaultValue) {
        String strValue = null == paramName ? null : props.getProperty(paramName);
        if (strValue == null || "".equals(strValue)) {
            return defaultValue;
        }
        return Long.parseLong(strValue);
    }

    public boolean getBoolean(String paramName, boolean defaultValue) {
        String strValue = null == paramName ? null : props.getProperty(paramName);
        if (strValue == null || "".equals(strValue)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(strValue);
    }
}
