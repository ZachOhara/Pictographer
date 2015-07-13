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

package io.github.zachohara.pictographer.core;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * The {@code FunctionParser} class contains static methods for converting a String into
 * a {@code Polynomial} that values can be easily calculated from.
 * 
 * @author Zach Ohara
 */
public abstract class FunctionParser {

	/**
	 * The set of symbols that will always separate different terms.
	 */
	public static final String[] TERM_DELIMITERS = {"+", "-"};

	/**
	 * The set of symbols that will be counted as whitespace, and removed.
	 */
	public static final String[] WHITESPACE_CHARS = {" ", "\t", "\n"};


	/**
	 * Parses an input string and returns a {@code Polynomial} that represents the same
	 * mathematical function as the string.
	 * 
	 * @param input the String of the function.
	 * @return the {@code Polynomial} function.
	 */
	public static Polynomial parsePolynomialFunction(String input) {
		input = removeWhitespace(input);
		List<String> termStrings = new ArrayList<String>();
		int i = 0;
		while (i < input.length()) {
			int lastI = i;
			while (i < input.length() && !charIsDelimiter(input.substring(i, i+1))) {
				i++;
			}
			termStrings.add(input.substring(Math.max(0, lastI - 1), i));
			i++;
		}
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

	/**
	 * Parses a given term for a coefficient and exponent, and then adds the converted
	 * polynomial to the given {@code Polynomial}.
	 * 
	 * @param addTo the {@code Polynomial} to add the parsed term to.
	 * @param term the term that should be parsed.
	 */
	private static void parseTerm(Polynomial addTo, String term) {
		if (term.length() == 0)
			return;
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
		addTo.addTerm(signum * coeff, exp);
	}

	/**
	 * Parses the given string representing a fraction, a decimal, or an integer, and
	 * returns a double with the value of the fraction.
	 * 
	 * @param fraction the string to parse as a fraction.
	 * @return the double value of the fraction.
	 */
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

	/**
	 * Determines if the given (single-character) string is a character that separates
	 * different terms.
	 * 
	 * @param s the string to search for a term delimiter in.
	 * @return {@code true} if the given string is a term delimiter; {@code false}
	 * otherwise.
	 */
	private static boolean charIsDelimiter(String s) {
		for (String delimiter : TERM_DELIMITERS) {
			if (s.equals(delimiter))
				return true;
		}
		return false;
	}

	/**
	 * Removes all the whitespace characters from the given string, and returns the
	 * whitespace-less version.
	 * 
	 * @param s the string to remove whitespace characters from.
	 * @return the string with all whitespace removed.
	 */
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

	/**
	 * Determines if the given (single-character) string is a whitespace character.
	 * 
	 * @param s the string to look for whitespace in.
	 * @return {@code true} if the given string is a whitespace character; {@code false}
	 * otherwise.
	 */
	private static boolean charIsWhitespace(String s) {
		for (String whitespace : WHITESPACE_CHARS) {
			if (s.equals(whitespace))
				return true;
		}
		return false;
	}

	/**
	 * Warns the user that a given term is not a valid term.
	 * 
	 * @param term the term that could not be parsed.
	 */
	public static void warnIllegalTerm(String term) {
		JOptionPane.showMessageDialog(null, "\"" + term + "\" is not a valid polynomial term",
				"Warning", JOptionPane.WARNING_MESSAGE);
	}

}
