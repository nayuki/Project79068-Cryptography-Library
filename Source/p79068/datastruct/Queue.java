package p79068.datastruct;


/** An abstract queue, a first-in-first-out (FIFO) data structure. */
public abstract class Queue<E> implements Cloneable{

 /**
  * Creates a queue.
  */
 protected Queue(){}


 /** Adds the specified object to the tail of this queue. */
 public abstract void enqueue(E obj);

 /** Removes and returns the head of this queue. */
 public abstract E dequeue();

 /** Returns the head of this queue. */
 public E peek(){
  return clone().dequeue();}

 /** Tests whether this queue is empty. */
 public abstract boolean isEmpty();


 @SuppressWarnings("unchecked")
 public Queue<E> clone(){
  try{
   return (Queue<E>)super.clone();}
  catch(CloneNotSupportedException e){
   throw new AssertionError(e);}}

 /**
  * Returns a string representation of the queue. Currently, the contents of the queue are listed from head to tail. This is subjected to change.
  * @return a string representation of the queue
  */
 public String toString(){
  StringBuilder sb=new StringBuilder();
  sb.append("Queue [");
  Queue<E> temp=clone();
  boolean initial=true;
  while(!temp.isEmpty()){
   if(initial)initial=false;
   else sb.append(", ");
   sb.append(temp.dequeue());}
  sb.append("]");
  return sb.toString();}}