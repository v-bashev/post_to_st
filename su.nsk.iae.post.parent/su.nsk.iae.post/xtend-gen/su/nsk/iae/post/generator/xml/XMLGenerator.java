package su.nsk.iae.post.generator.xml;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import su.nsk.iae.post.generator.IpoSTGenerator;
import su.nsk.iae.post.generator.xml.CodeGenerator;
import su.nsk.iae.post.generator.xml.FBGenerator;
import su.nsk.iae.post.generator.xml.ProgramGenerator;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;

@SuppressWarnings("all")
public class XMLGenerator implements IpoSTGenerator {
  private List<CodeGenerator> codes = new LinkedList<CodeGenerator>();
  
  @Override
  public void setModel(final Model model) {
    this.codes.clear();
    EList<Program> _programs = model.getPrograms();
    for (final Program p : _programs) {
      ProgramGenerator _programGenerator = new ProgramGenerator(p);
      this.codes.add(_programGenerator);
    }
    EList<FunctionBlock> _fbs = model.getFbs();
    for (final FunctionBlock fb : _fbs) {
      FBGenerator _fBGenerator = new FBGenerator(fb);
      this.codes.add(_fBGenerator);
    }
  }
  
  @Override
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    for (final CodeGenerator c : this.codes) {
      c.generate(fsa, path);
    }
  }
}
