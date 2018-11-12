package cn.kgc.model;

import java.math.BigDecimal;

public class Multiple extends Opertion {

	@Override
	public BigDecimal operte(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}

}
