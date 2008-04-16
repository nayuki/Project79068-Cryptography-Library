package p79068.datastruct;


public class DictionaryEntry<K,V>{

 public K key;
 public V value;


 public DictionaryEntry(K key,V value){
  this.key=key;
  this.value=value;}


 /**
  * Tests for equality with the specified object, comparing only the keys, not the values.
  * @param o the object to compare equality to
  * @return whether the keys are equal, ignoring the values
  */
 @SuppressWarnings("unchecked")
 public boolean equals(Object o){
  if(this==o)return true;
  else if(!(o instanceof DictionaryEntry))return false;
  else return key.equals(((DictionaryEntry)o).key);}

 /**
  * Returns the key's hash code.
  * @return <code>key.hashCode()</code>
  */
 public int hashCode(){
  return key.hashCode();}

 public String toString(){
  return String.format("DictionaryEntry: %s -> %s",key,value);}}