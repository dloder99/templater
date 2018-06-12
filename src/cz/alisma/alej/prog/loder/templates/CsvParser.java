/*
 * MIT License
 * Copyright (c) 2017 Gymnazium Nad Aleji
 * Copyright (c) 2017 Vojtech Horky
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cz.alej.prog.loder.templates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParser {
	private List<String> lines;
	private String[] headers;

	public CsvParser(String path) {
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			System.out.println("ERROR: Unable to read CSV from '" + path + "': " + e);
			System.exit(4);
		}

		headers = lines.remove(0).split(",");
	}

	public Map<String, String> getNextReplacements() {

		while (lines.size() > 0) {
			String line = lines.remove(0);
			String[] values = line.split(",");
			if (values.length != headers.length) {
				System.out.println("WARN: ignoring CSV line (number of values does not match headers): " + line);
				//kdyz ma radek nedobrou strukturu pro CSV, hlasim varovani,ale pokracuji..
			} else {
				Map<String, String> replacements = new HashMap<String, String>();

				for (int i = 0; i < values.length; i++) {
					replacements.put(headers[i], values[i]);
				}
				return replacements;
			}
		}

		return null;
	}
}
