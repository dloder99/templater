package cz.alej.prog.loder.templates;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Options {
	private final Map<String, String> replacements = new HashMap<String, String>();
	private String csvPath;
	private String templatePath;
	private String outputPattern = "templater-out-%05d.txt";

	public Options(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--var=")) {
				List<String> parts = Arrays.asList(args[i].split("="));
				if (parts.size() < 3) { // pripad,kdy chybi hodnota
					System.out.println("Ignoring invalid --var option: " + args[i]);
				} else {
					String value = String.join("=", parts.subList(2, parts.size()));
					replacements.put(parts.get(1), value); // vsechno projde a vytvori si z toho mapu
				}
			} else if (args[i].startsWith("--csv=")) {
				csvPath = args[i].substring(6);
			} else if (args[i].startsWith("--out=")) {
				outputPattern = args[i].substring(6);
			} else if (args[i].startsWith("--template=")) {
				templatePath = args[i].substring(11); //resi potrebne udaje pro 2.cast ulohy
			} else {
				System.out.println("Ignoring unknown argument: " + args[i]);
			}
		}
	}

	public Map<String, String> getReplacements() {
		return replacements;
	}

	public String getCsvPath() {
		return csvPath;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public String getOutputPattern() {
		return outputPattern;
	}
}
