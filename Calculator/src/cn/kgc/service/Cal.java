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
 * ������   ֻҪ�ѵ���dealwithStr(String str)�������ɼ����
 * @author Administrator
 *
 */
public class Cal {

	public LinkedList<BigDecimal> number = new LinkedList<BigDecimal>(); // ����ջ
	public LinkedList<Character> opSymbol = new LinkedList<Character>(); // �����ջ

	public Cal() {
		
	}
	
	/**
	 * ���ַ�������Ȼ�����ַ�������ջ����������������ջ�����У��ڷ������ʱ��Ҫ�������ȼ��жϡ�
	 * @param str
	 */
	public void dealwithStr(String str) {
		char[] s = new char[str.length()];
		str.getChars(0, str.length(), s, 0); // ��strת�����ַ�����
		int i = 0; // i��������s����
		BigDecimal decimal = new BigDecimal("0.1");
		BigDecimal sum = new BigDecimal("0");
		while (i < s.length - 1) {
			if (s[i] >= '0' && s[i] <= '9') { // ��������0~9 ���м���
				sum = sum.multiply(new BigDecimal("10")).add(new BigDecimal(s[i] + ""));// ��10�ټ�s[i]
				
				// ����һ���ַ�����������Ͱ�sum������������
				if (!(s[i + 1] >= '0' && s[i + 1] <= '9') && s[i + 1] != '.') {
					number.add(sum);
					if(opSymbol.size()>1) // �������ջ���ȴ���1
						calculateEveryNumberIn(); // ÿ�����һ�����ֵ�numberջ�У����ж������ջ�����ȼ�
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
				// ����һ���ַ�����������Ͱ�sum��������ջ
				if (!(s[i] >= '0' && s[i] <= '9') && s[i] != '.') {
					number.add(sum);
					if(opSymbol.size()>1) // �������ջ���ȴ���1
						calculateEveryNumberIn(); // ÿ�����һ�����ֵ�numberջ�У����ж������ջ�����ȼ�
					sum = new BigDecimal("0");
					decimal = new BigDecimal("0.1");
				}
			}
			if (!(s[i] >= '0' && s[i] <= '9') && s[i]!='=') { // ����������(���������������)�Ҳ��ǵȺ�
				addOpSymbol(s[i]); // ���뵽�����ջ
			}
			if(s[i]!='=')
				i++;
			if(s[i]=='=') {
				while(opSymbol.size()>0) {
					calculateEveryNumberIn();
					BigDecimal result = opeGetResult();
					number.add(result); // ����õ��Ľ����������ջ��
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
	 * ÿ�����һ�����ֵ�numberջ�У����ж������ջ�����ȼ�
	 */
	public void calculateEveryNumberIn() {
		// ��ջ����������ȼ���ջ��ǰһ�����ȼ���
		/*if((opSymbol.getLast()=='*' || opSymbol.getLast()=='/') &&
				(opSymbol.get(opSymbol.size()-2)=='+' || opSymbol.get(opSymbol.size()-2)=='-')) {
			BigDecimal result = opeGetResult();
			number.add(result); // ����õ��Ľ����������ջ��
		}*/
		if(opSymbol.getLast()=='*' || opSymbol.getLast()=='/') {
			BigDecimal result = opeGetResult();
			number.add(result); // ����õ��Ľ����������ջ��
		}
		
	}
	
	/**
	 * ȥ����ջ������Ԫ�غ������ջ��һ��Ԫ��
	 * @return
	 */
	public BigDecimal opeGetResult() {
		BigDecimal bNo = number.getLast(); // �õ�numberջ��Ԫ�� ��Ϊ �����b
		System.out.println("bno=" + bNo);
		number.removeLast(); // ����ջ ջ��Ԫ�س�ջ
		BigDecimal aNo = number.getLast(); // �õ�number��ջ��Ԫ�� ��Ϊ �����a
		if(opSymbol.size()>1 && opSymbol.get(opSymbol.size()-2)=='-') {
			aNo=aNo.multiply(new BigDecimal("-1"));
			opSymbol.set(opSymbol.size()-2, '+');
		}
		System.out.println("ano=" + aNo);
		number.removeLast(); // ����ջ ջ��Ԫ�س�ջ
		char op = opSymbol.getLast(); // �õ�opSymbol��ջ��Ԫ�� ��Ϊ ����������
		System.out.println("op " + op);
		opSymbol.removeLast(); // �����ջ ջ��Ԫ�س�ջ
		System.out.println("....." + number.size() + opSymbol.size());
		Opertion opertion = null; // ���ö�̬ʵ�ּӡ������ˡ�����
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
		BigDecimal result = opertion.operte(aNo, bNo); // �õ���������׼������numberջ��
		return result;
	}
	
	/**
	 * ������������ʱ���ȼ����������������
	 * @return
	 */
	public void dealWithNegative() {
		//char c = opSymbol.getLast();
		opSymbol.removeLast();
		opSymbol.removeLast();
		BigDecimal b = number.getLast();
		b = b.multiply(new BigDecimal("-1")); // ��Ϊ����
		number.removeLast();
		number.add(b);
		// ����ջÿ��ջһ��������Ҫ����calculateEveryNumberIn����
		if(opSymbol.size()>1) // �������ջ���ȴ���1
			calculateEveryNumberIn(); // ÿ�����һ�����ֵ�numberջ�У����ж������ջ�����ȼ�
	}

}
