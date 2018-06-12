/*
 * MIT License
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class OptionsTest {
	@Test
	public void noArguments() {
		Options o = new Options(new String[] {});
		
		assertNull(o.getCsvPath());
		assertEquals("templater-out-%05d.txt", o.getOutputPattern());
		assertNull(o.getTemplatePath());
		assertNotNull(o.getReplacements());
		assertEquals(0, o.getReplacements().size());
	}
	
	@Test
	public void csvPathOnly() {
		Options o = new Options(new String[] {"--csv=my=data here.csv"});
		
		assertEquals("my=data here.csv", o.getCsvPath());
		assertEquals("templater-out-%05d.txt", o.getOutputPattern());
		assertNull(o.getTemplatePath());
		assertNotNull(o.getReplacements());
		assertEquals(0, o.getReplacements().size());
	}
	
	@Test
	public void outputPatternOnly() {
		Options o = new Options(new String[] {"--out=out file=pattern-%05d.txt"});
		
		assertNull(o.getCsvPath());
		assertEquals("out file=pattern-%05d.txt", o.getOutputPattern());
		assertNull(o.getTemplatePath());
		assertNotNull(o.getReplacements());
		assertEquals(0, o.getReplacements().size());
	}
	
	@Test
	public void templatePathOnly() {
		Options o = new Options(new String[] {"--template=my=template here.txt"});
		
		assertNull(o.getCsvPath());
		assertEquals("templater-out-%05d.txt", o.getOutputPattern());
		assertEquals("my=template here.txt", o.getTemplatePath());
		assertNotNull(o.getReplacements());
		assertEquals(0, o.getReplacements().size());
	}
	
	@Test
	public void replacementsOnly() {
		Options o = new Options(new String[] {"--var=zakaznik=Jan Novak", "--var=a=b=c", "--var=dup=1", "--var=dup=2"});
		
		assertNull(o.getCsvPath());
		assertEquals("templater-out-%05d.txt", o.getOutputPattern());
		assertNull(o.getTemplatePath());
		assertNotNull(o.getReplacements());
		assertEquals(3, o.getReplacements().size());
		assertEquals("Jan Novak", o.getReplacements().get("zakaznik"));
		assertEquals("b=c", o.getReplacements().get("a"));
		assertEquals("2", o.getReplacements().get("dup")); // only last parameter is used
	}
	
	@Test
	public void allArguments() {
		Options o = new Options(new String[] {
				"--out=out file=pattern-%05d.txt",
				"--csv=my=data here.csv",
				"--var=zakaznik=Jan Novak",
				"--template=my=template here.txt",
				"--var=a=b=c",
				"--var=dup=1",
				"--var=dup=2"
		});
		
		assertEquals("my=data here.csv", o.getCsvPath());
		assertEquals("out file=pattern-%05d.txt", o.getOutputPattern());
		assertEquals("my=template here.txt", o.getTemplatePath());
		assertNotNull(o.getReplacements());
		assertEquals(3, o.getReplacements().size());
		assertEquals("Jan Novak", o.getReplacements().get("zakaznik"));
		assertEquals("b=c", o.getReplacements().get("a"));
		assertEquals("2", o.getReplacements().get("dup")); // only last parameter is used
	}
}
