/* Copyright (C) 2015 Zach Ohara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.pictographer.functioncore;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@code Polynomial} object represents a mathematical polynomial function. It can be
 * easily queried for values that exist on the x-y plane and are on this function.
 * 
 * @author Zach Ohara
 */
public class Polynomial {

	/**
	 * The list of terms that compose this polynomial.
	 */
	private List<Term> terms;

	/**
	 * Constructs a new polynomial with no terms.
	 */
	public Polynomial() {
		this.terms = new ArrayList<Term>();
	}

	/**
	 * Adds a new term with the given coefficient and power to this polynomial.
	 * 
	 * @param coefficient the coefficient of x in the new term.
	 * @param power the power of x in the new term.
	 * @return this polynomial after adding the new term.
	 */
	public Polynomial addTerm(double coefficient, double power) {
		this.terms.add(new Term(coefficient, power));
		return this;
	}

	/**
	 * Adds a new {@code Term} object to this polynomial.
	 * 
	 * @param t the {@code Term} to add to this polynomial.
	 * @return this polynomial after adding the new term.
	 */
	public Polynomial addTerm(Term t) {
		this.terms.add(t);
		return this;
	}

	/**
	 * Calculates the y-value of this polynomial function at the given x-coordinate.
	 * 
	 * @param x the x-coordinate to calculate a y-value for.
	 * @return the y-value at the given x-value.
	 */
	public double valueAt(double x) {
		if (this.terms.size() == 0)
			return Double.NaN;
		double value = 0;
		for (Term t : this.terms) {
			value += t.valueAtX(x);
		}
		return value;
	}

	/**
	 * Builds a string that contains the source code for representing this polynomial
	 * function in a LaTeX engine. This method assumes that every coefficient is a
	 * fraction with a numerator of 1, and that every exponent is an integer. If this is
	 * not true for this polynomial, the method will return an incorrect result. The
	 * method is deprecated for this reason.
	 * 
	 * @return the LaTeX source for this polynomial function.
	 */
	@Deprecated
	public String getLatexString() {
		String result = "";
		for (Term t : this.terms) {
			result += t.getLatex();
		}
		return result;
	}

	/**
	 * Calculates the derivative of this polynomial and returns it as another
	 * {@code Polynomial} object.
	 * 
	 * @return the {@code Polynomial} of this polynomial's derivative.
	 */
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

	/**
	 * A {@code Term} object represents a single term of a polynomial function. A
	 * {@code Term} contains a coefficient and an exponent.
	 */
	private static class Term {

		/**
		 * The coefficient of this term.
		 */
		double coefficient = 0;

		/**
		 * The exponent of this term.
		 */
		double exponent = 0;


		/**
		 * Constructs a new {@code Term} with the given coefficient and exponent.
		 * 
		 * @param c the coefficient of this term.
		 * @param e the exponent of this term.
		 */
		public Term(double c, double e) {
			this.coefficient = c;
			this.exponent = e;
		}

		/**
		 * Calculates the y-value of this polynomial function at the given x-coordinate.
		 * 
		 * @param x the x-coordinate to calculate a y-value for.
		 * @return the y-value at the given x-value.
		 */
		public double valueAtX(double x) {
			return this.coefficient * Math.pow(x, this.exponent);
		}

		/**
		 * Builds a string that contains the source code for representing this term in a
		 * LaTeX engine.This method assumes that the coefficient for this term is a
		 * fraction with a numerator of 1, and that the exponent is an integer. If this
		 * is not true for this term, the method will return an incorrect result. The
		 * method is deprecated for this reason.
		 * 
		 * @return the LaTeX source for this polynomial.
		 */
		@Deprecated
		public String getLatex() {
			String signum = (int)(Math.signum(coefficient)) == -1 ? "-" : "+";
			int den = (int)(Math.pow(Math.abs(this.coefficient), -1));
			String frac = "\\frac{1}{" + den + "} ";
			int exp = (int)(this.exponent);
			String expStr = "x^{" + exp + "} ";
			return signum + frac + expStr;
		}

		/**
		 * Calculates the derivative for this term, and returns it as another {@code Term}
		 * object.
		 * 
		 * @return the derivative for this term.
		 */
		public Term derivative() {
			if (this.exponent == 0)
				return new Term(0, 0);
			return new Term(this.coefficient * this.exponent, this.exponent - 1);
		}

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public String toString() {
			return this.coefficient + "x^" + this.exponent;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String result = "f(x) =";
		for (Term t : this.terms)
			result += " " + t.toString();
		return result;
	}

}
