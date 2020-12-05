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
import su.nsk.iae.post.generator.st.CodeGenerator;
import su.nsk.iae.post.generator.st.FBGenerator;
import su.nsk.iae.post.generator.st.ProgramGenerator;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;

@SuppressWarnings("all")
public class PoSTGenerator extends AbstractGenerator {
  private List<CodeGenerator> stCodes = new LinkedList<CodeGenerator>();
  
  private List<su.nsk.iae.post.generator.xml.CodeGenerator> xmlCodes = new LinkedList<su.nsk.iae.post.generator.xml.CodeGenerator>();
  
  @Override
  public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    final Model model = ((Model[])Conversions.unwrapArray((Iterables.<Model>filter(IteratorExtensions.<EObject>toIterable(input.getAllContents()), Model.class)), Model.class))[0];
    this.stCodes.clear();
    this.xmlCodes.clear();
    EList<Program> _programs = model.getPrograms();
    for (final Program p : _programs) {
      {
        ProgramGenerator _programGenerator = new ProgramGenerator(p);
        this.stCodes.add(_programGenerator);
        su.nsk.iae.post.generator.xml.ProgramGenerator _programGenerator_1 = new su.nsk.iae.post.generator.xml.ProgramGenerator(p);
        this.xmlCodes.add(_programGenerator_1);
      }
    }
    EList<FunctionBlock> _fbs = model.getFbs();
    for (final FunctionBlock fb : _fbs) {
      {
        FBGenerator _fBGenerator = new FBGenerator(fb);
        this.stCodes.add(_fBGenerator);
        su.nsk.iae.post.generator.xml.FBGenerator _fBGenerator_1 = new su.nsk.iae.post.generator.xml.FBGenerator(fb);
        this.xmlCodes.add(_fBGenerator_1);
      }
    }
  }
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    for (final CodeGenerator c : this.stCodes) {
      c.generate(fsa, "st/");
    }
    for (final su.nsk.iae.post.generator.xml.CodeGenerator c_1 : this.xmlCodes) {
      c_1.generate(fsa, "xml/");
    }
  }
}
