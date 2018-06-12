package cz.alej.prog.loder.templates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Template {
	private String content = "";
	
	public Template(String path) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			content = new String(encoded);
		} catch (IOException e) {
			System.out.println("ERROR: Unable to read template from '" + path + "': " + e);
			System.exit(3);
		}
	}
	
	public Template() {
	}

	public void setTemplate(String content) {
		this.content = content;
	}
	
	public String render(Map<String, String> replacements) {
		String result = new String(content);
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
            result = result.replace("{{ " + entry.getKey() + " }}", entry.getValue()); // nahradi vsechny mista klicovich slov pozadovanou hodnotou
        }
		
		return result;
	}
}
