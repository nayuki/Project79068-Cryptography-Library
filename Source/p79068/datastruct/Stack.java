package p79068.datastruct;


/**
 * An abstract stack, a last-in-first-out (LIFO) data structure.
 */
public abstract class Stack<E> implements Cloneable{

 /**
  * Creates a stack.
  */
 protected Stack(){}


 /**
  * Adds the specified object to the top of this stack.
  */
 public abstract void push(E obj);

 /**
  * Removes and returns the object at the top of this stack.
  */
 public abstract E pop();

 /**
  * Returns the object at the top of this stack.
  */
 public E peek(){
  return clone().pop();}

 /**
  * Tests whether this stack is empty.
  */
 public abstract boolean isEmpty();

 @SuppressWarnings("unchecked")
 public Stack<E> clone(){
  try{
   return (Stack<E>)super.clone();}
  catch(CloneNotSupportedException e){
   throw new AssertionError(e);}}

 /**
  * Returns a string representation of the stack. Currently, the contents of the stack are listed from bottom to top. This is subjected to change.
  * @return a string representation of the stack
  */
 public String toString(){
  Stack<E> temp=clone();
  List<E> contents=new ArrayList<E>();
  while(!temp.isEmpty())contents.append(temp.pop());
  StringBuilder sb=new StringBuilder();
  sb.append("Stack [");
  for(int i=contents.length()-1;i>=0;i--){
   if(i!=contents.length()-1)sb.append(", ");
   sb.append(contents.getAt(i));}
  sb.append("]");
  return sb.toString();}}