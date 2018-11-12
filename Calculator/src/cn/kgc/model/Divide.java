package cn.kgc.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends Opertion {

	@Override
	public BigDecimal operte(BigDecimal a, BigDecimal b) {
		return a.divide(b, 2, RoundingMode.HALF_UP);
	}

}
