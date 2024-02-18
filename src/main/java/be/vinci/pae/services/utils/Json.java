package be.vinci.pae.services.utils;

import be.vinci.pae.utils.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides serialization and deserialization functionalities for objects of type {@code T} to and
 * from JSON. This class reads from and writes to a JSON database file, managing collections within
 * the file.
 *
 * @param <T> the type of objects to be serialized/deserialized.
 */
public class Json<T> {

  private static final String DB_FILE_PATH = Config.getProperty("DatabaseFilePath");
  private static final ObjectMapper jsonMapper = new ObjectMapper();
  private static Path pathToDb = Paths.get(DB_FILE_PATH);
  private Class<T> type;

  /**
   * Constructor for Json utility, specifying the class type for serialization and deserialization.
   * The type information is necessary due to type erasure in Java generics at runtime.
   *
   * @param type the class of type {@code T}.
   */
  public Json(Class<T> type) {
    this.type = type;
  }

  /**
   * Serializes a list of items of type {@code T} into a named collection within the JSON database
   * file. If the database file does not exist, it is created along with the new collection. If the
   * collection already exists, it is overwritten with the provided items list.
   *
   * @param items          the list of items to be serialized and saved in the collection.
   * @param collectionName the name of the collection within the JSON database file.
   */
  public void serialize(List<T> items, String collectionName) {
    try {
      if (!Files.exists(pathToDb)) {
        ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(collectionName, items);
        jsonMapper.writeValue(pathToDb.toFile(), newCollection);
        return;
      }
      JsonNode allCollections = jsonMapper.readTree(pathToDb.toFile());
      if (allCollections.has(collectionName)) {
        ((ObjectNode) allCollections).remove(collectionName);
      }
      ArrayNode updatedCollection = jsonMapper.valueToTree(items);
      ((ObjectNode) allCollections).putArray(collectionName).addAll(updatedCollection);
      jsonMapper.writeValue(pathToDb.toFile(), allCollections);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deserializes items from a named collection within the JSON database file into a list of objects
   * of type {@code T}. If the collection or file does not exist, an empty list is returned.
   *
   * @param collectionName the name of the collection to deserialize from.
   * @return a list of objects of type {@code T}, or an empty list if the collection or file doesn't
   * exist.
   */
  public List<T> parse(String collectionName) {
    try {
      JsonNode node = jsonMapper.readTree(pathToDb.toFile());
      JsonNode collection = node.get(collectionName);
      if (collection == null) {
        return new ArrayList<>();
      }
      return jsonMapper.readerForListOf(type).readValue(collection);
    } catch (FileNotFoundException e) {
      return new ArrayList<>(); // send an empty list if there is no db file
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }
}
