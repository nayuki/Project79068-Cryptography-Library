package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.BoundsChecker;
import p79068.lang.NullChecker;
import p79068.util.Random;


/**
 * An array-based array.
 */
public final class ArrayArray<E> extends Array<E>{

 private E[] data;


 /**
  * Creates a new array-based array of the specified length with all elements set to the specified object.
  */
 @SuppressWarnings("unchecked")
 public ArrayArray(int length,E initObj){
  data=(E[])new Object[length];
  clearTo(initObj);}


 public int length(){
  return data.length;}

 public boolean contains(Object o){
  NullChecker.check(o);
  for(E obj:data){
   if(o.equals(obj))return true;}
  return false;}

 public int count(Object o){
  NullChecker.check(o);
  int count=0;
  for(E obj:data){
   if(o.equals(obj))count++;}
  return count;}

 public CollectionView<E> getAll(Object o){
  List<E> result=new ArrayList<E>();
  for(E obj:data){
   if(o.equals(obj))result.append(obj);}
  //return new CollectionViewWrapper<E>(result); FIXME
  return null;
  }

 public E getAt(int index){
  BoundsChecker.check(data.length,index);
  return data[index];}

 public Iterator<E> iterator(){
  return new Itr();}


 public void setAt(int index,E obj){
  NullChecker.check(obj);
  BoundsChecker.check(data.length,index);
  data[index]=obj;}

 public void shuffle(Random r){
  for(int i=0;i<data.length;i++){
   int j=i+r.randomInt(data.length-i);
   E temp=data[i];
   data[i]=data[j];
   data[j]=temp;}}

 public void clearTo(E obj){
  NullChecker.check(obj);
  for(int i=0;i<data.length;i++)data[i]=obj;}

 public int findNext(Object o){
  return findNext(o,0);}

 public int findNext(Object o,int start){
  NullChecker.check(o);
  for(int i=start;i<data.length;i++){
   if(o.equals(data[i]))return i;}
  return -1;}

 public int findPrevious(Object o){
  return findPrevious(o,data.length-1);}

 public int findPrevious(Object o,int start){
  NullChecker.check(o);
  for(int i=start;i>=0;i--){
   if(o.equals(data[i]))return i;}
  return -1;}



 private class Itr implements Iterator<E>{

  private int index;


  Itr(){
   index=0;}


  public boolean hasNext(){
   return index!=data.length;}

  public E next(){
   if(!hasNext())throw new NoSuchElementException();
   E result=data[index];
   index++;
   return result;}

  public void remove(){
   throw new UnsupportedOperationException();}}}