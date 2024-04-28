package uub.cachelayer;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import uub.staticlayer.CustomBankException;

public class RedisCache<K, V> implements Cache<K,V>{
	private JedisPool jedisPool;
    public RedisCache(int port) {
    	JedisPoolConfig poolConfig = new JedisPoolConfig();
    	poolConfig.setMaxTotal(10);
    	poolConfig.setMaxIdle(5);
    	
    	jedisPool = new JedisPool(poolConfig, "localhost", port);
    }

    @Override
	public void set(K key, V value) throws CustomBankException {
        try (Jedis jedis = jedisPool.getResource();
        		ByteArrayOutputStream bos = new ByteArrayOutputStream();
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
    	try(Jedis jedis = jedisPool.getResource()){
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
    	}
        catch(Exception e) {
        	throw new CustomBankException(e);
        }
        return null;
    }

    @Override
	public void rem(K key) throws CustomBankException {
    	try(Jedis jedis = jedisPool.getResource()){
        jedis.unlink(key.toString().getBytes());
        }catch (Exception e) {
        	throw new CustomBankException(e);
		}
    }

    @Override
	public void close() throws CustomBankException {
    	try(Jedis jedis = jedisPool.getResource()){
        jedis.close();
    	 }catch (Exception e) {
         	throw new CustomBankException(e);
 		}
    }



}

