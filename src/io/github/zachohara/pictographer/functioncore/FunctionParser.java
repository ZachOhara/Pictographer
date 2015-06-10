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

public abstract class FunctionParser {

	public static final String[] TERM_DELIMITERS = {"+", "-"}; 
	public static final String[] WHITESPACE_CHARS = {" ", "\t", "\n"};
	
	public static Polynomial parsePolynomialFunction(String input) {
		input = removeWhitespace(input);
//		System.out.println(input);
		List<String> termStrings = new ArrayList<String>();
		int i = 0;
		while (i < input.length()) {
			int lastI = i;
			while (i < input.length() && !charIsDelimiter(input.substring(i, i+1))) {
				i++;
			}
			termStrings.add(input.substring(Math.max(0, lastI - 1), i));
//			System.out.println(lastI + ", " + i);
			i++;
		}
		System.out.println(termStrings);
		Polynomial function = new Polynomial();
		for (String term : termStrings) {
			try {
				parseTerm(function, term);
			} catch (NumberFormatException e) {
				// TODO: warn invalid term
				e.printStackTrace();
			}
		}
		return function;
	}
	
	
	private static void parseTerm(Polynomial addTo, String term) {
		if (term.length() == 0)
			return;
		System.out.println(term);
		int signum = 1;
		if (term.startsWith("-"))
			signum = -1;
		if (charIsDelimiter(term.substring(0, 1)))
			term = term.substring(1);
		double coeff;
		double exp;
		if (term.indexOf("x") == -1) {
			coeff = parseFraction(term);
			exp = 0;
		} else {
			if (term.indexOf("x") == 0)
				coeff = 1;
			else
				coeff = parseFraction(term.substring(0, term.indexOf("x")));
			if (term.indexOf("^") == -1)
				exp = 1;
			else
				exp = parseFraction(term.substring(term.indexOf("^") + 1));
		}
//		System.out.println(", " + coeff + ", " + exp);
		addTo.addTerm(signum * coeff, exp);
	}
	
	private static double parseFraction(String fraction) {
		if (fraction.startsWith("(") && fraction.endsWith(")"))
				fraction = fraction.substring(1, fraction.length() - 1);
		if (fraction.indexOf("(") != -1 || fraction.indexOf(")") != -1)
			throw new NumberFormatException();
		int slash = fraction.indexOf("/");
		if (slash == -1) {
			return Double.parseDouble(fraction);
		} else {
			double num = Double.parseDouble(fraction.substring(0, slash));
			double den = Double.parseDouble(fraction.substring(slash + 1));
			return num / den;
		}
	}
	
	private static boolean charIsDelimiter(String s) {
		for (String delimiter : TERM_DELIMITERS) {
			if (s.equals(delimiter))
				return true;
		}
		return false;
	}
	
	private static String removeWhitespace(String s) {
		int i = 0;
		while (i < s.length()) {
			if (charIsWhitespace(s.substring(i, i+1)))
				s = s.substring(0, i) + s.substring(i+1);
			else
				i++;
		}
		return s;
	}
	
	private static boolean charIsWhitespace(String s) {
		for (String whitespace : WHITESPACE_CHARS) {
			if (s.equals(whitespace))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
//		String function = "1 - (1/2)x^2 + 3x^4 - 100/2x^500";
		String function = "x";
		Polynomial p = parsePolynomialFunction(function);
		System.out.println(p);
	}

}
