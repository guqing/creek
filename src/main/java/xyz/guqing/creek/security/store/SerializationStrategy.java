package xyz.guqing.creek.security.store;

/**
 * Defines how objects are serialized and deserialized.
 */
public interface SerializationStrategy {

    /**
     * Serializes an object.
     *
     * @param object The object to be serialized.
     * @return A byte array.
     */
    byte[] serialize(Object object);

    /**
     * Deserializes an object from a byte array.
     *
     * @param byteArray The byte array.
     * @param <T>       The type of the object.
     * @return The deserialized object.
     */
    <T> T deserialize(byte[] byteArray);

}