package com.pej.utils;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONUtils {
    public static String toJSON(Object object) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        JsonElement jsonElement = gson.toJsonTree(object, object.getClass());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(object.getClass().getName(), jsonElement);
        JsonParser jsonParser = new JsonParser();
        jsonElement = jsonParser.parse(jsonObject.toString());
        return gson.toJson(jsonElement);
    }

    public static String toJSONWithoutClassName(Object object) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public static String toJSON(Object object, Type type) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        return gson.toJson(object, type);
    }


    public static String toJSON(Writer writer, Object object) throws Exception{
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        String json = toJSON(object);
        bufferedWriter.write(json);
        bufferedWriter.close();
        return json;
    }

    public static byte[] toJSONasBytes(Object object) {
        return JSONUtils.toJSON(object).getBytes(StandardCharsets.UTF_8);
    }

    public static <T> T fromJSON(Class<T> theClass, String json) {
        return JSONUtils.fromJSON(theClass.getName(), theClass, json);
    }

    public static <T> T fromJSON(Class<T> theClass, byte[] jsonAsBytes) {
        return JSONUtils.fromJSON(theClass, new String(jsonAsBytes,StandardCharsets.UTF_8));
    }

    public static <T> T fromJSON(String classAliasName, Class<T> theClass, String json) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.fromJson(json, theClass);
    }

    public static <T> T fromJSON(String classAliasName, Class<T> theClass, byte[] jsonAsBytes) {
        return JSONUtils.fromJSON(classAliasName, theClass, new String(jsonAsBytes,StandardCharsets.UTF_8));
    }

    public static <T> T fromJSON(Reader reader, Class<T> theClass) throws IOException {
        // gson is sensitive to white spaces. 1 option is to use gson's JsonReader.setLinient(true).
        // but that will allow malformed json. So, the hack is to read the contents and trim the lines
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            sb.append(line.trim());
        }
        bufferedReader.close();
        return JSONUtils.fromJSON(sb.toString(), theClass);
    }

    public static <T> T fromJSON(Reader reader, Type type) throws IOException {
        // gson is sensitive to white spaces. 1 option is to use gson's JsonReader.setLinient(true).
        // but that will allow malformed json. So, the hack is to read the contents and trim the lines
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            sb.append(line.trim());
        }
        bufferedReader.close();
        return JSONUtils.fromJSON(sb.toString(), type);
    }

    public static <T> T fromJSON(String json, Type theClass) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, theClass);
    }

    public static <T> T fromJSON(byte[] jsonAsBytes, Type theClass) {
        return JSONUtils.fromJSON(new String(jsonAsBytes, StandardCharsets.UTF_8), theClass);
    }

    public static <T> T fromJSON(String json, Class<T> theClass) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        return gson.fromJson(jsonObject.get(theClass.getName()), theClass);
    }

    public static <T> T fromJSON(byte[] jsonAsBytes, Class<T> theClass) {
        return JSONUtils.fromJSON(new String(jsonAsBytes, StandardCharsets.UTF_8), theClass);
    }

    public static String createAndWriteToFile(String fileName, Object object) throws IOException{
        Path jsonFilePath = Paths.get(fileName);
        Files.createDirectories(jsonFilePath.getParent());
        Files.deleteIfExists(jsonFilePath);
        Files.createFile(jsonFilePath);
        PrintWriter writer = new PrintWriter(new File(fileName), StandardCharsets.UTF_8.toString());
        String json = toJSON(object);
        writer.write(json);
        writer.close();
        return json;
    }

    public static String createAndWriteToFile(String fileName, byte[] jsonAsBytes) throws IOException {
        return JSONUtils.createAndWriteToFile(fileName, new String(jsonAsBytes, StandardCharsets.UTF_8));
    }

    public static String createAndWriteToFile(String fileName, Object object, Type type) throws IOException{
        Path jsonFilePath = Paths.get(fileName);
        Files.createDirectories(jsonFilePath.getParent());
        Files.deleteIfExists(jsonFilePath);
        Files.createFile(jsonFilePath);
        PrintWriter writer = new PrintWriter(new File(fileName), StandardCharsets.UTF_8.toString());
        String json = toJSON(object, type);
        writer.write(json);
        writer.close();
        return json;
    }

    public static String createAndWriteToFileWithoutClassname(String fileName, Object object) throws IOException{
        Path jsonFilePath = Paths.get(fileName);
        Files.createDirectories(jsonFilePath.getParent());
        Files.deleteIfExists(jsonFilePath);
        Files.createFile(jsonFilePath);
        PrintWriter writer = new PrintWriter(new File(fileName), StandardCharsets.UTF_8.toString());
        String json = toJSONWithoutClassName(object);
        writer.write(json);
        writer.close();
        return json;
    }

}