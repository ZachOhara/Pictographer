/*
 *  Copyright (C) 2015 Zach Ohara
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.pictographer.functioncore;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {
	
	private List<Term> terms;

	public Polynomial() {
		this.terms = new ArrayList<Term>();
	}
	
	public Polynomial addTerm(double coefficient, double power) {
		this.terms.add(new Term(coefficient, power));
		return this;
	}
	
	public Polynomial addTerm(Term t) {
		this.terms.add(t);
		return this;
	}
	
	public double valueAt(double x) {
		if (this.terms.size() == 0)
			return Double.NaN;
		double value = 0;
		for (Term t : this.terms) {
			value += t.valueAtX(x);
		}
		return value;
	}
	
	public String getLatexString() {
		String result = "";
		for (Term t : this.terms) {
			result += t.getLatex();
		}
		return result;
	}
	
	public Polynomial derivative() {
		if (terms.size() == 0) {
			return null;
		}
		Polynomial p = new Polynomial();
		for (Term t: terms) {
			p.addTerm(t.derivative());
		}
		return p;
	}
	
	private static class Term {
		
		double coefficient = 0;
		double exponent = 0;
		
		public Term(double c, double e) {
			this.coefficient = c;
			this.exponent = e;
		}
		
		public double valueAtX(double x) {
			return this.coefficient * Math.pow(x, this.exponent);
		}
		
		public String getLatex() {
			String signum = (int)(Math.signum(coefficient)) == -1 ? "-" : "+";
			int den = (int)(Math.pow(Math.abs(this.coefficient), -1));
			String frac = "\\frac{1}{" + den + "} ";
			int exp = (int)(this.exponent);
			String expStr = "x^{" + exp + "} ";
			return signum + frac + expStr;
		}
		
		public Term derivative() {
			if (this.exponent == 0)
				return new Term(0, 0);
			return new Term(this.coefficient * this.exponent, this.exponent - 1);
		}
		
		@Override
		public String toString() {
//			if (this.coefficient < .00001)
//				return "0";
//			String retStr = "";
//			if (this.coefficient < 0) 
//				retStr = "- ";
//			double absCo = Math.abs(this.coefficient);
//			if (this.exponent < .00001)
//				return retStr + absCo;
//			return retStr + absCo + "x^" + this.exponent;
			return this.coefficient + "x^" + this.exponent;
		}
		
	}
	
	@Override
	public String toString() {
		String result = "f(x) =";
		for (Term t : this.terms)
			result += " " + t.toString();
		return result;
	}

	public static void main(String[] args) {
		Polynomial p = FunctionParser.parsePolynomialFunction("1 - x^2 + x^4");
		// f(x) = 1 + x^2
		System.out.println(p);
		System.out.println(p.derivative());
	}

}
