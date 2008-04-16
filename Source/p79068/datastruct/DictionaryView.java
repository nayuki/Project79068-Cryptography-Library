package p79068.datastruct;


/**
 * An abstract readable dictionary.
 */
public abstract class DictionaryView<K,V>{

 /**
  * Creates a readable dictionary.
  */
 protected DictionaryView(){}

 /**
  * Returns the value associated with the specified key.
  */
 public abstract V get(K key);

 /**
  * Tests whether this set contains the specified key.
  */
 public abstract boolean contains(K key);

 /**
  * Returns the number of objects in this set.
  */
 public int size(){
  return getKeys().size();}

 /**
  * Returns the set of keys in this dictionary.
  */
 public abstract SetView<K> getKeys();

 /**
  * Returns the collection of values in this dictionary.
  */
 public abstract CollectionView<V> getValues();}