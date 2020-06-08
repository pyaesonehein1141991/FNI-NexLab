package org.tat.fni.api.common.emumdata;

import org.tat.fni.api.common.ScaleUnit;

public enum Scale {
	SHORT, LONG;

	private ScaleUnit[] SCALE_UNITS = new ScaleUnit[] { new ScaleUnit(63, "vigintillion", "decilliard"), new ScaleUnit(60, "novemdecillion", "decillion"),
			new ScaleUnit(57, "octodecillion", "nonilliard"), new ScaleUnit(54, "septendecillion", "nonillion"), new ScaleUnit(51, "sexdecillion", "octilliard"),
			new ScaleUnit(48, "quindecillion", "octillion"), new ScaleUnit(45, "quattuordecillion", "septilliard"), new ScaleUnit(42, "tredecillion", "septillion"),
			new ScaleUnit(39, "duodecillion", "sextilliard"), new ScaleUnit(36, "undecillion", "sextillion"), new ScaleUnit(33, "decillion", "quintilliard"),
			new ScaleUnit(30, "nonillion", "quintillion"), new ScaleUnit(27, "octillion", "quadrilliard"), new ScaleUnit(24, "septillion", "quadrillion"),
			new ScaleUnit(21, "sextillion", "trilliard"), new ScaleUnit(18, "quintillion", "trillion"), new ScaleUnit(15, "quadrillion", "billiard"),
			new ScaleUnit(12, "trillion", "billion"), new ScaleUnit(9, "billion", "milliard"), new ScaleUnit(6, "Million", "Million"), new ScaleUnit(3, "Thousand", "Thousand"),
			new ScaleUnit(2, "Hundred", "Hundred"),
			// new ScaleUnit(1, "ten", "ten"),
			// new ScaleUnit(0, "one", "one"),
			new ScaleUnit(-1, "tenth", "tenth"), new ScaleUnit(-2, "hundredth", "hundredth"), new ScaleUnit(-3, "thousandth", "thousandth"),
			new ScaleUnit(-4, "ten-thousandth", "ten-thousandth"), new ScaleUnit(-5, "hundred-thousandth", "hundred-thousandth"), new ScaleUnit(-6, "millionth", "millionth"),
			new ScaleUnit(-7, "ten-millionth", "ten-millionth"), new ScaleUnit(-8, "hundred-millionth", "hundred-millionth"), new ScaleUnit(-9, "billionth", "milliardth"),
			new ScaleUnit(-10, "ten-billionth", "ten-milliardth"), new ScaleUnit(-11, "hundred-billionth", "hundred-milliardth"), new ScaleUnit(-12, "trillionth", "billionth"),
			new ScaleUnit(-13, "ten-trillionth", "ten-billionth"), new ScaleUnit(-14, "hundred-trillionth", "hundred-billionth"), new ScaleUnit(-15, "quadrillionth", "billiardth"),
			new ScaleUnit(-16, "ten-quadrillionth", "ten-billiardth"), new ScaleUnit(-17, "hundred-quadrillionth", "hundred-billiardth"),
			new ScaleUnit(-18, "quintillionth", "trillionth"), new ScaleUnit(-19, "ten-quintillionth", "ten-trillionth"),
			new ScaleUnit(-20, "hundred-quintillionth", "hundred-trillionth"), new ScaleUnit(-21, "sextillionth", "trilliardth"),
			new ScaleUnit(-22, "ten-sextillionth", "ten-trilliardth"), new ScaleUnit(-23, "hundred-sextillionth", "hundred-trilliardth"),
			new ScaleUnit(-24, "septillionth", "quadrillionth"), new ScaleUnit(-25, "ten-septillionth", "ten-quadrillionth"),
			new ScaleUnit(-26, "hundred-septillionth", "hundred-quadrillionth"), };

	public String getName(int exponent) {
		for (ScaleUnit unit : SCALE_UNITS) {
			if (unit.getExponent() == exponent) {
				return unit.getName(this.ordinal());
			}
		}
		return "";
	}
}
