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
	
	public void addTerm(double coefficient, double power) {
		this.terms.add(new Term(coefficient, power));
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
	
	private static class Term {
		
		double coefficient;
		double exponent;
		
		public Term(double c, double e) {
			this.coefficient = c;
			this.exponent = e;
		}
		
		public double valueAtX(double x) {
			return this.coefficient * Math.pow(x, this.exponent);
		}
		
		@Override
		public String toString() {
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
		Polynomial p = new Polynomial();
		p.addTerm(1, 0);
		p.addTerm(1, 2);
		// f(x) = 1 + x^2
		System.out.println(p.valueAt(0));
		System.out.println(p.valueAt(1));
		System.out.println(p.valueAt(2));
	}

}
