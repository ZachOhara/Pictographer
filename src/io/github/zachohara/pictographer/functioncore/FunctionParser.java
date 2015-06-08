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
	
	public static Polynomial parsePolynomialFunction(String input) {
		List<String> termStrings = new ArrayList<String>();
		int i = 0;
		while (i < input.length()) {
			int lastI = i;
			while (i < input.length() && !charIsDelimiter(input.substring(i, i+1))) {
				i++;
				System.out.println(i);
			}
			termStrings.add(input.substring(lastI, i).trim());
			i++;
		}
		System.out.println(termStrings);
		Polynomial function = new Polynomial();
		for (String term : termStrings)
			parseTerm(function, term);
		return function;
	}
	
	
	private static void parseTerm(Polynomial addTo, String term) {
		//TODO;
	}
	
	private static boolean charIsDelimiter(String s) {
		for (String delimiter : TERM_DELIMITERS) {
			if (s.equals(delimiter))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String function = "1 + x^2 + 3x^4 + 100x^500";
		parsePolynomialFunction(function);
	}

}
