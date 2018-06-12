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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CsvTemplater {
	public static void main(String[] args) {
		Options options = new Options(args);
		if (options.getTemplatePath() == null) {
			System.out.println("ERROR: Missing mandatory argument --template");
			System.exit(1);
		} else if (options.getCsvPath() == null) {
			System.out.println("ERROR: Missing mandatory argument --csv");
			System.exit(2); // osetri, kdyz nam nekdo nezada potrebne informace o template a CSVcku
		}

		Template template = new Template(options.getTemplatePath());
		CsvParser csv = new CsvParser(options.getCsvPath());

		int counter = 1;
		Map<String, String> replacements;
		while ((replacements = csv.getNextReplacements()) != null) { // kdyz neexistuje zadny pouzitelny radek
			String result = template.render(replacements);
			String outputPath = String.format(options.getOutputPattern(), counter++);

			try {
				PrintWriter writer = new PrintWriter(outputPath, "UTF-8");
				writer.print(result);
				writer.close();
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: Unable to write file: " + outputPath);
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
					//pro zapisovani vyplnene sablony do konkretniho souboru
			}
		}
	}
}
