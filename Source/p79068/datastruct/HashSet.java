package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.*;


/** A hash table-based set. */
public final class HashSet<E> extends Set<E>{

 private Set<E>[] hashTable;
 private int size;
 private int mask;


 public HashSet(){
  this(16);}

 @SuppressWarnings({"unchecked","cast"})
 public HashSet(int hashTableSize){
  hashTable=(Set<E>[])new ArraySet[hashTableSize];
  size=0;
  mask=hashTableSize-1;}


 public int size(){
  return size;}

 public boolean contains(Object o){
  NullChecker.check(o);
  int index=o.hashCode()&mask;
  return hashTable[index]!=null&&hashTable[index].contains(o);}

 public int count(Object o){
  if(contains(o))return 1;
  else return 0;}

 public E get(Object o){
  NullChecker.check(o);
  int index=o.hashCode()&mask;
  if(hashTable[index]!=null)return hashTable[index].get(o);
  else return null;}

 public Iterator<E> iterator(){
  return new Itr();}


 public void add(E obj){
  NullChecker.check(obj);
  int index=obj.hashCode()&mask;
  if(hashTable[index]==null)hashTable[index]=new ArraySet<E>(1);
  if(!hashTable[index].contains(obj)){
   hashTable[index].add(obj);
   size++;}}

 public void add(CollectionView<? extends E> coll){
  NullChecker.check(coll);
  for(E obj:coll)add(obj);}

 public void set(E obj){
  NullChecker.check(obj);
  int index=obj.hashCode()&mask;
  if(hashTable[index]==null)hashTable[index]=new ArraySet<E>(1);
  if(!hashTable[index].contains(obj))size++;
  hashTable[index].set(obj);}

 public void set(CollectionView<? extends E> coll){
  NullChecker.check(coll);
  for(E obj:coll)set(obj);}

 public E remove(Object o){
  NullChecker.check(o);
  int index=o.hashCode()&mask;
  if(hashTable[index]!=null)return hashTable[index].remove(o);
  else return null;}

 public void remove(CollectionView<?> coll){
  NullChecker.check(coll);
  for(Object obj:coll)remove(obj);}

 public void intersect(CollectionView<?> coll){
  NullChecker.check(coll);
// FIXME
}

 public void clear(){
  for(int i=0;i<hashTable.length;i++)hashTable[i]=null;}



 private class Itr implements Iterator<E>{

  private int index;
  private Iterator<E> currentIterator;


  Itr(){
   index=0;
   currentIterator=hashTable[index].iterator();
   while(!currentIterator.hasNext()){
    index++;
    if(index==hashTable.length){
     currentIterator=null;
     break;}
    currentIterator=hashTable[index].iterator();}}


  public boolean hasNext(){
   return currentIterator==null;}

  public E next(){
   if(!hasNext())throw new NoSuchElementException();
   E result=currentIterator.next();
   while(!currentIterator.hasNext()){
    index++;
    if(index==hashTable.length){
     currentIterator=null;
     break;}
    currentIterator=hashTable[index].iterator();}
   return result;}

  public void remove(){
   throw new UnsupportedOperationException();}}}