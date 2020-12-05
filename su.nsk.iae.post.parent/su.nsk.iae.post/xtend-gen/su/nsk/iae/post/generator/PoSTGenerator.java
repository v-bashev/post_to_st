package su.nsk.iae.post.generator;

import com.google.common.collect.Iterables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import su.nsk.iae.post.generator.IpoSTGenerator;
import su.nsk.iae.post.generator.st.STGenerator;
import su.nsk.iae.post.generator.xml.XMLGenerator;
import su.nsk.iae.post.poST.Model;

@SuppressWarnings("all")
public class PoSTGenerator extends AbstractGenerator {
  private IpoSTGenerator stGenerator = new STGenerator();
  
  private IpoSTGenerator xmlGenerator = new XMLGenerator();
  
  @Override
  public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    final Model model = ((Model[])Conversions.unwrapArray((Iterables.<Model>filter(IteratorExtensions.<EObject>toIterable(input.getAllContents()), Model.class)), Model.class))[0];
    this.stGenerator.setModel(model);
    this.xmlGenerator.setModel(model);
  }
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.stGenerator.generate(fsa, "st/");
    this.xmlGenerator.generate(fsa, "xml/");
  }
}
