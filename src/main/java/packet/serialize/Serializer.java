package packet.serialize;

public interface Serializer {
    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制对象转成java对象
     */

    <T> T deserialize(Class<T> clazz,byte[] bytes);



    Serializer DEFAULT = new JSONSerializer();

    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;
}
