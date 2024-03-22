package uub.cachelayer;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import redis.clients.jedis.Jedis;
import uub.staticlayer.CustomBankException;

public class RedisCache<K, V> implements Cache<K,V>{

    private final Jedis jedis;

    public RedisCache(int port) {
        this.jedis = new Jedis("localhost", port);
    }

    @Override
	public void set(K key, V value) throws CustomBankException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(value);
            byte[] serializedValue = bos.toByteArray();
            jedis.set(key.toString().getBytes(), serializedValue);

        } catch (IOException e) {
        	e.printStackTrace();
            throw new CustomBankException("Incorrect casting");
        }
    }

    @Override
	public V get(K key) throws CustomBankException {
        byte[] bytes = jedis.get(key.toString().getBytes());

        if (bytes != null) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInput in = new ObjectInputStream(bis)) {

                @SuppressWarnings("unchecked")
                V deserializedValue = (V) in.readObject();
                return deserializedValue;

            } catch (IOException | ClassNotFoundException e) {
            	e.printStackTrace();
            	throw new CustomBankException("Incorrect casting");
            }
        }
        return null;
    }

    @Override
	public void rem(K key) {
        jedis.del(key.toString().getBytes());
    }

    @Override
	public void close() {
        jedis.close();
    }



}

