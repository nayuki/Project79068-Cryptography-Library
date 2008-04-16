package p79068.net;

import java.util.*;


public class HttpResponse{

 protected String httpVersion;
 protected int statusCode;
 protected String reason;
 protected Map<String,List<String>> headers;
 protected byte[] message;


 HttpResponse(){
  httpVersion=null;
  statusCode=-1;
  reason=null;
  headers=new HashMap<String,List<String>>();
  message=null;}

 public HttpResponse(int status,String reason){
  this(status,reason,null);}

 public HttpResponse(int status,String reason,byte[] message){
  setStatusCode(status);
  setReasonPhrase(reason);
  setHttpVersion("HTTP/1.1");
  setMessage(message);
  headers=new HashMap<String,List<String>>();}


 public String getHttpVersion(){
  return httpVersion;}

 public int getStatusCode(){
  return statusCode;}

 public String getReason(){
  return reason;}

 public Map<String,List<String>> getHeaders(){
  return (Map<String,List<String>>)((HashMap)headers).clone();}

 public byte[] getMessage(){
  return (byte[])message.clone();}


 public void setHttpVersion(String version){
  if(!version.matches("HTTP/\\d+\\.\\d+"))throw new IllegalArgumentException();
  httpVersion=version;}

 public void setStatusCode(int status){
  if(status<0||status>=1000)throw new IllegalArgumentException();
  statusCode=status;}

 public void setReasonPhrase(String reason){
  this.reason=reason;}

 public void addHeader(String name,String value){
  if(!isToken(name))throw new IllegalArgumentException();
  if(!headers.containsKey(name))headers.put(name,new ArrayList<String>());
  if(value!=null)headers.get(name).add(value);}

 public void setMessage(byte[] message){
  if(message!=null)this.message=(byte[])message.clone();
  else this.message=null;}


 protected static boolean isToken(String str){
  for(int i=0;i<str.length();i++){
   char c=str.charAt(i);
   if(c<32||c==' '||c=='('||c==')'||c=='<'||c=='>'||c=='@'||c==','||c==';'||c==':'||c=='\\'||c=='"'||c=='/'||c=='['||c==']'||c=='?'||c=='='||c=='{'||c=='}')return false;}
  return true;}}