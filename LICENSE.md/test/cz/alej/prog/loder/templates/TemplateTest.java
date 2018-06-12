/*
 * Copyright (c) 2018 Gymnazium Nad Aleji
 * Copyright (c) 2018 Vojtech Horky
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

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TemplateTest {
	private final Map<String, String> emptyReplacements = new HashMap<String, String>();
	private final Template template = new Template();
	private Template templateCustomer;
	private final String CONTENT = "bla bla {{ customer }} bla";

	public TemplateTest() {
		templateCustomer = new Template();
		templateCustomer.setTemplate(CONTENT);
	}

	@Test
	public void defaultEmptyString() {
		assertEquals("", template.render(emptyReplacements));
	}

	@Test
	public void setTemplate() {
		String content = "blabla\n {{ vs }} \n\n";
		Template t = new Template();
		t.setTemplate(content);
		assertEquals(content, t.render(emptyReplacements));
	}

	@Test
	public void templateReplacesAll() {
		Template t = new Template();
		t.setTemplate("bla {{ number }}, {{ multi }} aaa {{ number } {{ multi }}, {{ multi }}");
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("number", "7");
		replacements.put("multi", "123");
		assertEquals("bla 7, 123 aaa {{ number } 123, 123", t.render(replacements));
	}

	@Test
	public void templateReplaceCaseSensitive() {
		assertEquals(CONTENT, templateCustomer.render(emptyReplacements));

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("Customer", "Jan Novak");
		// not replaced, Customer != customer
		assertEquals(CONTENT, templateCustomer.render(replacements));
		replacements.remove("Customer");
		replacements.put("customer", "Jan Novak");
		assertEquals("bla bla Jan Novak bla", templateCustomer.render(replacements));
	}

	@Test
	public void templateCanBeReused() {
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("customer", "Jan Novak");
		assertEquals("bla bla Jan Novak bla", templateCustomer.render(replacements));
		assertEquals(CONTENT, templateCustomer.render(emptyReplacements));
	}
}
