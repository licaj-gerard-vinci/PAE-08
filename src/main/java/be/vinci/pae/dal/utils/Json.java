package be.vinci.pae.dal.utils;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * The Json class provides methods to serialize and deserialize objects using Jackson ObjectMapper
 * with specified JSON Views.
 *
 * @param <T> the type of the object being serialized or deserialized
 */
public class Json<T> {

    private final static ObjectMapper jsonMapper = new ObjectMapper();
    private Class<T> type;

    /**
     * Constructs a new Json object with the specified type.
     *
     * @param type the class type of the object
     */
    public Json(Class<T> type) {
        this.type = type;
    }

    /**
     * Filters a list of objects based on the public JSON view.
     *
     * @param list the list of objects to filter
     * @return a filtered list of objects with fields as per the public view
     */
    public <T> List<T> filterPublicJsonViewAsList(List<T> list) {
        try {
            JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, this.type);
            // serialize using JSON Views : public view (all fields not required in the
            // views are not serialized)
            String publicItemListAsString = jsonMapper.writerWithView(Views.Public.class).writeValueAsString(list);
            // deserialize using JSON Views : Public View (all fields that are not serialized
            // are set to their default values in the POJOs)
            return jsonMapper.readerWithView(Views.Public.class).forType(type).readValue(publicItemListAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Filters an object based on the public JSON view.
     *
     * @param item the object to filter
     * @return the filtered object with fields as per the public view
     */
    public <T> T filterPublicJsonView(T item) {
        try {
            // serialize using JSON Views : public view (all fields not required in the
            // views are not serialized)
            String publicItemAsString = jsonMapper.writerWithView(Views.Public.class).writeValueAsString(item);
            // deserialize using JSON Views : Public View (all fields that are not serialized
            // are set to their default values in the POJO)
            return jsonMapper.readerWithView(Views.Public.class).forType(type).readValue(publicItemAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
