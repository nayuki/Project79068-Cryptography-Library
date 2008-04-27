package datastruct;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.datastruct.List;


public abstract class ListTest {
	
	protected abstract <E> List<E> newList();
	
	
	@Test
	public void testAppend() {
		List<String> list = newList();
		list.append("January");
		list.append("February");
		list.append("March");
		list.append("April");
		list.append("May");
		list.append("June");
		assertEquals(6, list.length());
		assertEquals("January",  list.getAt(0));
		assertEquals("February", list.getAt(1));
		assertEquals("March",    list.getAt(2));
		assertEquals("April",    list.getAt(3));
		assertEquals("May",      list.getAt(4));
		assertEquals("June",     list.getAt(5));
	}
	
	
	@Test
	public void testAppendList() {
		List<String> list = newList();
		{
			List<String> smalllist = newList();
			smalllist.append("January");
			list.appendList(smalllist);
		} {
			List<String> smalllist = newList();
			smalllist.append("February");
			smalllist.append("March");
			smalllist.append("April");
			list.appendList(smalllist);
		} {
			List<String> smalllist = newList();
			smalllist.append("May");
			smalllist.append("June");
			smalllist.append("July");
			smalllist.append("August");
			smalllist.append("September");
			smalllist.append("October");
			smalllist.append("November");
			smalllist.append("December");
			list.appendList(smalllist);
		}
		assertEquals(12, list.length());
		assertEquals("January",   list.getAt( 0));
		assertEquals("February",  list.getAt( 1));
		assertEquals("March",     list.getAt( 2));
		assertEquals("April",     list.getAt( 3));
		assertEquals("May",       list.getAt( 4));
		assertEquals("June",      list.getAt( 5));
		assertEquals("July",      list.getAt( 6));
		assertEquals("August",    list.getAt( 7));
		assertEquals("September", list.getAt( 8));
		assertEquals("October",   list.getAt( 9));
		assertEquals("November",  list.getAt(10));
		assertEquals("December",  list.getAt(11));
	}
	
	
	@Test
	public void testSetAt() {
		List<String> list = newList();
		for (int i = 0; i < 10; i++)
			list.append(null);
		list.setAt(0, "zero");
		list.setAt(1, "ten");
		list.setAt(2, "twenty");
		list.setAt(3, "thirty");
		list.setAt(4, "forty");
		list.setAt(5, "fifty");
		list.setAt(6, "sixty");
		list.setAt(7, "seventy");
		list.setAt(8, "eighty");
		list.setAt(9, "ninety");
		assertEquals(10, list.length());
		assertEquals("zero"   , list.getAt(0));
		assertEquals("ten"    , list.getAt(1));
		assertEquals("twenty" , list.getAt(2));
		assertEquals("thirty" , list.getAt(3));
		assertEquals("forty"  , list.getAt(4));
		assertEquals("fifty"  , list.getAt(5));
		assertEquals("sixty"  , list.getAt(6));
		assertEquals("seventy", list.getAt(7));
		assertEquals("eighty" , list.getAt(8));
		assertEquals("ninety" , list.getAt(9));
	}
	
	
	@Test
	public void testInsertAtBeginning() {
		List<String> list = newList();
		list.insertAt(0, "Sunday");
		list.insertAt(0, "Monday");
		list.insertAt(0, "Tuesday");
		assertEquals(3, list.length());
		assertEquals("Tuesday", list.getAt(0));
		assertEquals("Monday" , list.getAt(1));
		assertEquals("Sunday" , list.getAt(2));
	}
	
	
	@Test
	public void testInsertAtEnd() {
		List<String> list = newList();
		list.insertAt(0, "Saturday");
		list.insertAt(1, "Friday");
		list.insertAt(2, "Thursday");
		list.insertAt(3, "Wednesday");
		assertEquals(4, list.length());
		assertEquals("Saturday" , list.getAt(0));
		assertEquals("Friday"   , list.getAt(1));
		assertEquals("Thursday" , list.getAt(2));
		assertEquals("Wednesday", list.getAt(3));
	}
	
	
	@Test
	public void testInsertInMiddle() {
		List<String> list = newList();
		list.insertAt(0, "Up");
		list.insertAt(1, "Down");
		list.insertAt(1, "Left");
		list.insertAt(2, "Right");
		list.insertAt(1, "Front");
		list.insertAt(2, "Back");
		assertEquals(6, list.length());
		assertEquals("Up"   , list.getAt(0));
		assertEquals("Front", list.getAt(1));
		assertEquals("Back" , list.getAt(2));
		assertEquals("Left" , list.getAt(3));
		assertEquals("Right", list.getAt(4));
		assertEquals("Down" , list.getAt(5));
	}
	
	
	@Test
	public void testInsertList() {
		List<String> list = newList();
		{
			List<String> smalllist = newList();
			smalllist.append("1");
			smalllist.append("2");
			smalllist.append("3");
			smalllist.append("5");
			list.insertListAt(0, smalllist);
		} {
			List<String> smalllist = newList();
			smalllist.append("377");
			smalllist.append("610");
			smalllist.append("987");
			list.insertListAt(4, smalllist);
		} {
			List<String> smalllist = newList();
			smalllist.append("8");
			smalllist.append("13");
			smalllist.append("21");
			smalllist.append("144");
			smalllist.append("233");
			list.insertListAt(4, smalllist);
		} {
			List<String> smalllist = newList();
			smalllist.append("34");
			smalllist.append("55");
			smalllist.append("89");
			list.insertListAt(7, smalllist);
		}
		assertEquals(15, list.length());
		assertEquals(  "1", list.getAt( 0));
		assertEquals(  "2", list.getAt( 1));
		assertEquals(  "3", list.getAt( 2));
		assertEquals(  "5", list.getAt( 3));
		assertEquals(  "8", list.getAt( 4));
		assertEquals( "13", list.getAt( 5));
		assertEquals( "21", list.getAt( 6));
		assertEquals( "34", list.getAt( 7));
		assertEquals( "55", list.getAt( 8));
		assertEquals( "89", list.getAt( 9));
		assertEquals("144", list.getAt(10));
		assertEquals("233", list.getAt(11));
		assertEquals("377", list.getAt(12));
		assertEquals("610", list.getAt(13));
		assertEquals("987", list.getAt(14));
	}
	
	
	@Test
	public void testRemoveAt() {
		List<Character> list = newList();
		{
			String str = "the quick brown fox jumped over the lazy dog";
			for (int i = 0; i < str.length(); i++)
				list.append(str.charAt(i));
			assertEquals(str.length(), list.length());
		}
		
		assertEquals('e', list.removeAt( 2));
		assertEquals('u', list.removeAt( 4));
		assertEquals('q', list.removeAt( 3));
		assertEquals(' ', list.removeAt( 2));
		
		assertEquals('f', list.removeAt(12));
		assertEquals(' ', list.removeAt(11));
		assertEquals('n', list.removeAt(10));
		assertEquals('w', list.removeAt( 9));
		
		assertEquals(' ', list.removeAt(11));
		assertEquals('j', list.removeAt(11));
		assertEquals('u', list.removeAt(11));
		assertEquals('x', list.removeAt(10));
		
		assertEquals('p', list.removeAt(11));
		assertEquals('d', list.removeAt(12));
		assertEquals('e', list.removeAt(11));
		
		assertEquals('v', list.removeAt(13));
		assertEquals('e', list.removeAt(13));
		
		assertEquals('l', list.removeAt(19));
		assertEquals('z', list.removeAt(20));
		assertEquals('a', list.removeAt(19));
		assertEquals(' ', list.removeAt(18));
		
		assertEquals('g', list.removeAt(22));
		
		{
			String str = "thick broom or they do";
			assertEquals(str.length(), list.length());
			for (int i = 0; i < str.length(); i++)
				assertEquals(str.charAt(i), list.getAt(i));
		}
		
		assertEquals('t', list.removeAt( 0));
		
		assertEquals('c', list.removeAt(2));
		assertEquals('k', list.removeAt(2));
		assertEquals(' ', list.removeAt(2));
		assertEquals('b', list.removeAt(2));
		assertEquals('r', list.removeAt(2));
		assertEquals('o', list.removeAt(2));
		assertEquals('o', list.removeAt(2));
		
		assertEquals('o', list.removeAt(4));
		
		assertEquals('h', list.removeAt(7));
		assertEquals(' ', list.removeAt(5));
		assertEquals('t', list.removeAt(5));
		
		assertEquals('o', list.removeAt(9));
		
		assertEquals(' ', list.removeAt(7));
		assertEquals('y', list.removeAt(6));
		
		{
			String str = "him red";
			assertEquals(str.length(), list.length());
			for (int i = 0; i < str.length(); i++)
				assertEquals(str.charAt(i), list.getAt(i));
		}
	}
	
}