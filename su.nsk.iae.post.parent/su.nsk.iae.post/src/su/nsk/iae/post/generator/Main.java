package su.nsk.iae.post.generator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import su.nsk.iae.post.PoSTStandaloneSetup;

public class Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Aborting: no path to poST file provided!");
			return;
		}
		Injector injector = new PoSTStandaloneSetup().createInjectorAndDoEMFRegistration();
		Main main = injector.getInstance(Main.class);
		if (args.length == 1) {
			main.runGenerator(args[0], false);
		} else {
			if (args[0].contains(".post")) {
				main.runGenerator(args[0], args[1].equals("-l"));
			} else {
				main.runGenerator(args[1], args[0].equals("-l"));
			}
		}
	}

	@Inject
	private Provider<ResourceSet> resourceSetProvider;

	@Inject
	private IResourceValidator validator;

	@Inject
	private GeneratorDelegate generator;

	@Inject 
	private JavaIoFileSystemAccess fileAccess;

	protected void runGenerator(String string, boolean local) {
		// Load the resource
		ResourceSet set = resourceSetProvider.get();
		Resource resource = set.getResource(URI.createFileURI(string), true);

		// Validate the resource
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		if (checkErrors(issues)) {
			System.out.println("Code generation aborted.");
			printIssues(issues);
			return;
		}

		// Configure and start the generator
		if (local) {
			fileAccess.setOutputPath(".");
		} else {
			fileAccess.setOutputPath("src-gen/");
		}
		GeneratorContext context = new GeneratorContext();
		context.setCancelIndicator(CancelIndicator.NullImpl);
		generator.generate(resource, fileAccess, context);

		System.out.println("Code generation finished.");
		printIssues(issues);
	}
	
	private boolean checkErrors(List<Issue> issues) {
		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR) {
				return true;
			}
		}
		return false;
	}
	
	private void printIssues(List<Issue> issues) {
		for (Issue issue : issues) {
			System.err.println(issue);
		}
	}
}
