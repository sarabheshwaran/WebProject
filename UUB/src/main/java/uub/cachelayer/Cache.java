package uub.cachelayer;

import uub.staticlayer.CustomBankException;

public interface Cache<K,V> {

	 public void set(K key, V value) throws CustomBankException;

	    public V get(K key) throws CustomBankException ;

	    public void rem(K key) ;

	    public void close() ;

	    //this is cache
	    //dveb

}
