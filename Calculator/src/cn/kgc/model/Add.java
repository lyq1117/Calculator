package cn.kgc.model;

import java.math.BigDecimal;

public class Add extends Opertion {

	
	@Override
	public BigDecimal operte(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}

}
