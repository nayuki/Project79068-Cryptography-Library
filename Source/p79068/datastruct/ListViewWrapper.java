package p79068.datastruct;

import java.util.Iterator;


/**
 * A read-only wrapper for a readable list.
 */
public final class ListViewWrapper<E> extends ListView<E>{

 private ListView<E> list;


 /**
  * Creates a read-only wrapper over the specified readable list.
  */
 public ListViewWrapper(ListView<E> list){
  this.list=list;}


 public E getAt(int index){
  return list.getAt(index);}

 public int length(){
  return list.length();}

 public int findNext(Object o){
  return list.findNext(o);}

 public int findNext(Object o,int start){
  return list.findNext(o,start);}

 public int findPrevious(Object o){
  return list.findPrevious(o);}

 public int findPrevious(Object o,int start){
  return list.findPrevious(o,start);}

 public Iterator<E> iterator(){
  return list.iterator();}}