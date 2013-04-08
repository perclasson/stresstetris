package analyzation;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
	String root;

	public static void main(String[] args) {
		Analysis analysis = new Analysis(
				"/home/per/workspace/stresstetris/tests/");
		analysis.printLatex();
	}

	private void printBaselineDifferences() {
		File[] subjects = getTestFolders(root, explicitFeedbackFilter());
		System.out.println("Explicit");
		// System.out.println("------------");
		for (File subject : subjects) {
			FeedbackTest test = getFeedbackTestData(subject,
					explicitFeedbackFilter());
			test.printBaselineDifference();
		}

		subjects = getTestFolders(root, implicitFeedbackFilter());
		System.out.println("Implicit");
		// System.out.println("------------");
		for (File subject : subjects) {
			FeedbackTest test = getFeedbackTestData(subject,
					implicitFeedbackFilter());
			test.printBaselineDifference();
		}
	}

	private void printMeanDifferences() {
		File[] subjects = getTestFolders(root, explicitFeedbackFilter());

		System.out.println("Explicit");
		for (File subject : subjects) {
			FeedbackTest test = getFeedbackTestData(subject,
					explicitFeedbackFilter());
			if (test.getFeedbackSize() >= 2) {
				test.printDifference();
			}
		}

		subjects = getTestFolders(root, implicitFeedbackFilter());

		System.out.println("Implicit");
		for (File subject : subjects) {
			FeedbackTest test = getFeedbackTestData(subject,
					implicitFeedbackFilter());
			if (test.getFeedbackSize() >= 2) {
				test.printDifference();
			}
		}
	}

	public Analysis(String root) {
		this.root = root;
	}

	private void printResult() {
		File[] subjects = getTestFolders(root, patternFilter());
		StringBuilder sb = new StringBuilder();

		for (File subject : subjects) {
			List<EdaTest> tests = getPatternTestData(subject);
			sb.append(subject.getName()).append("\n");
			sb.append("-------------").append("\n");

			for (EdaTest test : tests) {
				sb.append(test.getName()).append("\n");
				sb.append(test.getCorrelation()).append("\n");
				sb.append(test.getConfidence()).append("\n\n");
			}
			sb.append("\n\n");
		}

		System.out.println(sb.toString());

	}

	private void printLatex() {
		File[] subjects = getTestFolders(root, patternFilter());
		StringBuilder sb = new StringBuilder();

		for (File subject : subjects) {
			List<EdaTest> tests = getPatternTestData(subject);
			for (int i = 0; i < tests.size(); i++) {
				sb.append("    ").append(
						capitalize(subject.getName())
								+ (i > 0 ? " (" + (i + 1) + ") " : ""));
				sb.append(" & ").append(tests.get(i).getRawCorrelation());
				sb.append(" & ").append(tests.get(i).getCorrelation());
				sb.append(" & ").append(tests.get(i).getConfidence());
				sb.append(" & ").append(tests.get(i).getN());
				sb.append("\\\\ \\hline\n");
			}
		}

		System.out.println(sb.toString());

	}

	private String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	private List<EdaTest> getPatternTestData(File firstTestSubject) {
		List<EdaTest> testFiles = new ArrayList<EdaTest>();

		File[] edaFiles = firstTestSubject.listFiles(patternFilter());

		for (File eda : edaFiles) {
			File time = new File(eda.getAbsolutePath().replace("-eda", "-dif"));
			testFiles.add(new EdaTest(eda, time));
		}

		return testFiles;
	}

	private FeedbackTest getFeedbackTestData(File subject, FilenameFilter filter) {
		List<EdaTest> patternTests = new ArrayList<EdaTest>();
		List<EdaTest> feedbackTests = new ArrayList<EdaTest>();

		File[] patternFiles = subject.listFiles(patternFilter());
		File[] explicitFiles = subject.listFiles(filter);

		for (File edaFile : patternFiles) {
			patternTests.add(new EdaTest(edaFile));
		}

		for (File edaFile : explicitFiles) {
			feedbackTests.add(new EdaTest(edaFile));
		}

		return new FeedbackTest(feedbackTests, patternTests);
	}

	private File[] getTestFolders(String testFolderPath,
			final FilenameFilter filter) {
		File testRoot = new File(testFolderPath);

		File[] listOfTestFolder = testRoot.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File folder, String name) {
				System.out.println(name);
				return folder.isDirectory()
						&& new File(folder.getAbsolutePath() + "/" + name + "/")
								.listFiles(filter).length > 0;
			}
		});

		return listOfTestFolder;
	}

	private FilenameFilter csvFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.contains("csv") && name.contains("eda")
						&& !name.contains("FIXED");
			}
		};
	}

	private FilenameFilter patternFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.contains("csv") && name.contains("eda")
						&& !name.contains("FIXED")
						&& !name.contains("feedback");
			}
		};
	}

	private FilenameFilter explicitFeedbackFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.contains("csv") && name.contains("eda")
						&& !name.contains("FIXED") && name.contains("explicit");
			}
		};
	}

	private FilenameFilter implicitFeedbackFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return name.contains("csv") && name.contains("eda")
						&& !name.contains("FIXED") && name.contains("implicit");
			}
		};
	}

}
