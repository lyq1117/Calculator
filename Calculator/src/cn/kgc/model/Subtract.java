package cn.kgc.model;

import java.math.BigDecimal;

public class Subtract extends Opertion {

	@Override
	public BigDecimal operte(BigDecimal a, BigDecimal b) {
		return a.subtract(b);
	}

}
