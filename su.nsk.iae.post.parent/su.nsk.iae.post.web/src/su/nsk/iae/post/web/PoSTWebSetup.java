/*
 * generated by Xtext 2.24.0
 */
package su.nsk.iae.post.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;
import su.nsk.iae.post.PoSTRuntimeModule;
import su.nsk.iae.post.PoSTStandaloneSetup;
import su.nsk.iae.post.ide.PoSTIdeModule;

/**
 * Initialization support for running Xtext languages in web applications.
 */
public class PoSTWebSetup extends PoSTStandaloneSetup {
	
	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new PoSTRuntimeModule(), new PoSTIdeModule(), new PoSTWebModule()));
	}
	
}
