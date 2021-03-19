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
		main.runGenerator(args[0]);
	}

	@Inject
	private Provider<ResourceSet> resourceSetProvider;

	@Inject
	private IResourceValidator validator;

	@Inject
	private GeneratorDelegate generator;

	@Inject 
	private JavaIoFileSystemAccess fileAccess;

	protected void runGenerator(String string) {
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
		fileAccess.setOutputPath(".");
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
