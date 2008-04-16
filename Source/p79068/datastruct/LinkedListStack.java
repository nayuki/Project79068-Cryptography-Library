package p79068.datastruct;

import p79068.lang.NullChecker;


/**
A linked list-based stack. All operations are in O(1) time.
<p>Mutability: <em>Mutable</em><br>
 Thread safety: <em>Unsafe</em></p>
*/
public final class LinkedListStack<E> extends Stack<E>{

 private LinkedListNode<E> top;


 /**
  * Creates a linked list stack.
  */
 public LinkedListStack(){
  top=null;}


 public void push(E obj){
  NullChecker.check(obj);
  top=new LinkedListNode<E>(obj,top);}

 public E pop(){
  if(isEmpty())throw new IllegalStateException("Stack underflow");
  E result=top.value;
  top=top.next;
  return result;}

 public E peek(){
  if(isEmpty())throw new IllegalStateException("Stack underflow");
  return top.value;}

 public boolean isEmpty(){
  return top==null;}


 @SuppressWarnings("unchecked")
 public LinkedListStack<E> clone(){
  LinkedListStack<E> result=(LinkedListStack<E>)super.clone();
  if(result.top!=null){
   result.top=result.top.clone();
   for(LinkedListNode<E> node=result.top;node.next!=null;node=node.next)node.next=node.next.clone();}
  return result;}

 public String toString(){
  List<E> contents=new ArrayList<E>();
  for(LinkedListNode<E> node=top;node!=null;node=node.next)contents.append(node.value);
  StringBuilder sb=new StringBuilder();
  sb.append("Stack [");
  for(int i=contents.length()-1;i>=0;i--){
   if(i!=contents.length()-1)sb.append(", ");
   sb.append(contents.getAt(i));}
  sb.append("]");
  return sb.toString();}}