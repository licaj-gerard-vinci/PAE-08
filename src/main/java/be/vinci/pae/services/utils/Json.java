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

  // Java generics are mostly compile time, this means that the type information is lost at runtime.
  // To get the type information at runtime you have to add it as an argument of the constructor.
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
      // if no DB file, write a new collection to a new db file
      if (!Files.exists(pathToDb)) {
        // Create an object and add a JSON array as POJO, e.g. { items:[...]}
        ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(collectionName, items);
        jsonMapper.writeValue(pathToDb.toFile(),
            newCollection); // write the JSON Object in the DB file
        return;
      }
      // get all collections : can be read as generic JsonNode, if it can be Object or Array;
      JsonNode allCollections = jsonMapper.readTree(
          pathToDb.toFile()); // e.g. { users:[...], items:[...]}
      // remove current collection, e.g. remove the array of items
      if (allCollections.has(collectionName)) {
        ((ObjectNode) allCollections).remove(collectionName); //e.g. it leaves { users:[...]}
      }

      ArrayNode updatedCollection = jsonMapper.valueToTree(items);
      // Add the JSON array in allCollections, e.g. : { users:[...], items:[...]}
      ((ObjectNode) allCollections).putArray(collectionName).addAll(updatedCollection);
      // write to the db file allCollections
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
      // get allCollections
      JsonNode node = jsonMapper.readTree(pathToDb.toFile());
      // accessing value of the specified field of an object node,
      // e.g. the JSON array within "items" field of { users:[...], items:[...]}
      JsonNode collection = node.get(collectionName);
      if (collection == null) {
        return (List<T>) new ArrayList<T>();
      }
      // convert the JsonNode to a List of POJOs & return it
      return jsonMapper.readerForListOf(type).readValue(collection);
    } catch (FileNotFoundException e) {
      return (List<T>) new ArrayList<T>(); // send an empty list if there is no db file
    } catch (IOException e) {
      e.printStackTrace();
      return (List<T>) new ArrayList<T>();
    }
  }

}