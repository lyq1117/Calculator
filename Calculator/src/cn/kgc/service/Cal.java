package cn.kgc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

import cn.kgc.model.Add;
import cn.kgc.model.Divide;
import cn.kgc.model.DynamicArray;
import cn.kgc.model.Multiple;
import cn.kgc.model.Opertion;
import cn.kgc.model.Subtract;

/**
 * 运算类   只要把调用dealwithStr(String str)方法即可计算答案
 * @author Administrator
 *
 */
public class Cal {

	public LinkedList<BigDecimal> number = new LinkedList<BigDecimal>(); // 数字栈
	public LinkedList<Character> opSymbol = new LinkedList<Character>(); // 运算符栈

	public Cal() {
		
	}
	
	/**
	 * 将字符串处理，然后数字放入数字栈，运算符放入运算符栈。其中，在放运算符时，要进行优先级判断。
	 * @param str
	 */
	public void dealwithStr(String str) {
		char[] s = new char[str.length()];
		str.getChars(0, str.length(), s, 0); // 把str转换成字符数组
		int i = 0; // i用来遍历s数组
		BigDecimal decimal = new BigDecimal("0.1");
		BigDecimal sum = new BigDecimal("0");
		while (i < s.length - 1) {
			if (s[i] >= '0' && s[i] <= '9') { // 若是数字0~9 进行计算
				sum = sum.multiply(new BigDecimal("10")).add(new BigDecimal(s[i] + ""));// 乘10再加s[i]
				
				// 若下一个字符是运算符，就把sum加入数字数组
				if (!(s[i + 1] >= '0' && s[i + 1] <= '9') && s[i + 1] != '.') {
					number.add(sum);
					if(opSymbol.size()>1) // 若运算符栈长度大于1
						calculateEveryNumberIn(); // 每次添加一个数字到number栈中，就判断运算符栈的优先级
					sum = new BigDecimal("0");
					decimal = new BigDecimal("0.1");
				}
			}
			if (s[i] == '.') {
				i++;
				while(s[i]>='0' && s[i]<='9') {
				sum = sum.add(decimal.multiply(new BigDecimal(s[i] + "")));
				decimal = decimal.multiply(new BigDecimal("0.1")); // decimal = decimal*0.1
				i++;
				System.out.println("i="+i);
				}
				// 若下一个字符是运算符，就把sum加入数字栈
				if (!(s[i] >= '0' && s[i] <= '9') && s[i] != '.') {
					number.add(sum);
					if(opSymbol.size()>1) // 若运算符栈长度大于1
						calculateEveryNumberIn(); // 每次添加一个数字到number栈中，就判断运算符栈的优先级
					sum = new BigDecimal("0");
					decimal = new BigDecimal("0.1");
				}
			}
			if (!(s[i] >= '0' && s[i] <= '9') && s[i]!='=') { // 若不是数字(包括运算符和括号)且不是等号
				addOpSymbol(s[i]); // 加入到运算符栈
			}
			if(s[i]!='=')
				i++;
			if(s[i]=='=') {
				while(opSymbol.size()>0) {
					calculateEveryNumberIn();
					BigDecimal result = opeGetResult();
					number.add(result); // 运算得到的结果放入数字栈顶
				}
			}
		}
	}

	public void addOpSymbol(char op) {
		if (op == ')') {
			dealWithNegative();
		}else
			opSymbol.add(op);
	}
	
	/**
	 * 每次添加一个数字到number栈中，就判断运算符栈的优先级
	 */
	public void calculateEveryNumberIn() {
		// 若栈顶运算符优先级比栈顶前一个优先级高
		/*if((opSymbol.getLast()=='*' || opSymbol.getLast()=='/') &&
				(opSymbol.get(opSymbol.size()-2)=='+' || opSymbol.get(opSymbol.size()-2)=='-')) {
			BigDecimal result = opeGetResult();
			number.add(result); // 运算得到的结果放入数字栈顶
		}*/
		if(opSymbol.getLast()=='*' || opSymbol.getLast()=='/') {
			BigDecimal result = opeGetResult();
			number.add(result); // 运算得到的结果放入数字栈顶
		}
		
	}
	
	/**
	 * 去数字栈顶两个元素和运算符栈顶一个元素
	 * @return
	 */
	public BigDecimal opeGetResult() {
		BigDecimal bNo = number.getLast(); // 得到number栈顶元素 作为 运算的b
		System.out.println("bno=" + bNo);
		number.removeLast(); // 数字栈 栈顶元素出栈
		BigDecimal aNo = number.getLast(); // 得到number的栈顶元素 作为 运算的a
		if(opSymbol.size()>1 && opSymbol.get(opSymbol.size()-2)=='-') {
			aNo=aNo.multiply(new BigDecimal("-1"));
			opSymbol.set(opSymbol.size()-2, '+');
		}
		System.out.println("ano=" + aNo);
		number.removeLast(); // 数字栈 栈顶元素出栈
		char op = opSymbol.getLast(); // 得到opSymbol的栈顶元素 作为 运算的运算符
		System.out.println("op " + op);
		opSymbol.removeLast(); // 运算符栈 栈顶元素出栈
		System.out.println("....." + number.size() + opSymbol.size());
		Opertion opertion = null; // 运用多态实现加、减、乘、除。
		switch(op) {
			case '+':
				opertion = new Add();
				break;
			case '-':
				opertion = new Subtract();
				break;
			case '*':
				opertion = new Multiple();
				break;
			case '/':
				opertion = new Divide();
				break;
		}
		BigDecimal result = opertion.operte(aNo, bNo); // 得到运算结果，准备放入number栈顶
		return result;
	}
	
	/**
	 * 当遇到右括号时，先计算括号里面的内容
	 * @return
	 */
	public void dealWithNegative() {
		//char c = opSymbol.getLast();
		opSymbol.removeLast();
		opSymbol.removeLast();
		BigDecimal b = number.getLast();
		b = b.multiply(new BigDecimal("-1")); // 变为负数
		number.removeLast();
		number.add(b);
		// 数字栈每入栈一个数，都要进行calculateEveryNumberIn方法
		if(opSymbol.size()>1) // 若运算符栈长度大于1
			calculateEveryNumberIn(); // 每次添加一个数字到number栈中，就判断运算符栈的优先级
	}

}
