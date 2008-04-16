package p79068.datastruct;

import java.util.Iterator;


/**
 * A read-only wrapper for a readable set.
 */
public final class SetViewWrapper<E> extends SetView<E>{

 private SetView<E> set;


 /**
  * Creates a read-only wrapper over the specified readable set.
  */
 public SetViewWrapper(SetView<E> set){
  this.set=set;}


 public boolean contains(Object o){
  return set.contains(o);}

 public int count(Object o){
  return set.count(o);}

 public int size(){
  return set.size();}

 public E get(Object o){
  return set.get(o);}

 public Iterator<E> iterator(){
  return set.iterator();}}