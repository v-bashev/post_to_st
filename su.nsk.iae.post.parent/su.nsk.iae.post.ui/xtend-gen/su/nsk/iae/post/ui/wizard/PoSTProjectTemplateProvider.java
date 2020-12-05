/**
 * generated by Xtext 2.23.0
 */
package su.nsk.iae.post.ui.wizard;

import org.eclipse.xtext.ui.wizard.template.AbstractProjectTemplate;
import org.eclipse.xtext.ui.wizard.template.IProjectTemplateProvider;
import su.nsk.iae.post.ui.wizard.HelloWorldProject;

/**
 * Create a list with all project templates to be shown in the template new project wizard.
 * 
 * Each template is able to generate one or more projects. Each project can be configured such that any number of files are included.
 */
@SuppressWarnings("all")
public class PoSTProjectTemplateProvider implements IProjectTemplateProvider {
  @Override
  public AbstractProjectTemplate[] getProjectTemplates() {
    HelloWorldProject _helloWorldProject = new HelloWorldProject();
    return new AbstractProjectTemplate[] { _helloWorldProject };
  }
}