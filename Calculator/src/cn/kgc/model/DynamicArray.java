package cn.kgc.model;

public class DynamicArray {

	
	
	
	
	public static void main(String[] args) {
		String str = "3+5";
		char[] s = new char[str.length()];
		str.getChars(0, str.length(), s, 0);
		for(int i=0; i<s.length; i++) {
			System.out.println(s[i]);
		}
	}
	
}
