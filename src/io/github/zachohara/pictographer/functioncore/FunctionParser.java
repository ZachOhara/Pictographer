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
		System.out.println(input);
		List<String> termStrings = new ArrayList<String>();
		int i = 1;
		while (i < input.length()) {
			int lastI = i;
			while (i < input.length() && !charIsDelimiter(input.substring(i, i+1))) {
				i++;
			}
			termStrings.add(input.substring(lastI - 1, i));
			i++;
		}
		System.out.println(termStrings);
		Polynomial function = new Polynomial();
		for (String term : termStrings) {
			try {
				parseTerm(function, term);
			} catch (NumberFormatException e) {
				// TODO: warn invalid term
			}
		}
		return function;
	}
	
	
	private static void parseTerm(Polynomial addTo, String term) {
		System.out.println(term);
		int signum = 1;
		if (term.startsWith("-"))
			signum = -1;
		if (charIsDelimiter(term.substring(0, 1)))
			term = term.substring(1);
		double coeff;
		double exp;
		if (term.indexOf("x") == -1) {
			coeff = Double.parseDouble(term);
			exp = 0;
		} else {
			if (term.indexOf("x") == 0)
				coeff = 1;
			else
				coeff = Double.parseDouble(term.substring(0, term.indexOf("x")));
			if (term.indexOf("^") == -1)
				exp = 1;
			else
				exp = Double.parseDouble(term.substring(term.indexOf("^") + 1));
		}
		addTo.addTerm(signum * coeff, exp);
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
		String function = "1 - x^2 + 3x^4 - 100x^500";
		Polynomial p = parsePolynomialFunction(function);
		System.out.println(p);
	}

}
