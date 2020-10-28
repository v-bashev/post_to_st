package su.nsk.iae.post.generator;

import com.google.common.collect.Iterables;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import su.nsk.iae.post.generator.st.ProgramGenerator;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class PoSTGenerator extends AbstractGenerator {
  private List<ProgramGenerator> programs = new LinkedList<ProgramGenerator>();
  
  @Override
  public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    final Model model = ((Model[])Conversions.unwrapArray((Iterables.<Model>filter(IteratorExtensions.<EObject>toIterable(input.getAllContents()), Model.class)), Model.class))[0];
    EList<Program> _programs = model.getPrograms();
    for (final Program p : _programs) {
      ProgramGenerator _programGenerator = new ProgramGenerator(p);
      this.programs.add(_programGenerator);
    }
  }
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    for (final ProgramGenerator p : this.programs) {
      p.generate(fsa, "");
    }
  }
}