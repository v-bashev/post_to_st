package su.nsk.iae.post.generator.st;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import su.nsk.iae.post.generator.IPoSTGenerator;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;

@SuppressWarnings("all")
public class STGenerator implements IPoSTGenerator {
  private VarHelper globVarList = new GlobalVarHelper();
  
  private List<ProgramGenerator> programs = new LinkedList<ProgramGenerator>();
  
  @Override
  public void setModel(final Model model) {
    this.globVarList.clear();
    this.programs.clear();
    EList<GlobalVarDeclaration> _globVars = model.getGlobVars();
    for (final GlobalVarDeclaration v : _globVars) {
      this.globVarList.add(v);
    }
    EList<Program> _programs = model.getPrograms();
    for (final Program p : _programs) {
      ProgramPOUGenerator _programPOUGenerator = new ProgramPOUGenerator(p);
      this.programs.add(_programPOUGenerator);
    }
    EList<FunctionBlock> _fbs = model.getFbs();
    for (final FunctionBlock fb : _fbs) {
      FunctionBlockPOUGenerator _functionBlockPOUGenerator = new FunctionBlockPOUGenerator(fb);
      this.programs.add(_functionBlockPOUGenerator);
    }
  }
  
  @Override
  public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
  }
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.generateSingleFile(fsa, "");
  }
  
  @Override
  public void afterGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
  }
  
  private void generateSingleFile(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    _builder.append("poST_code.st");
    fsa.generateFile(_builder.toString(), this.generateSingleFileBody());
  }
  
  private String generateSingleFileBody() {
    StringConcatenation _builder = new StringConcatenation();
    String _generateVars = ProgramGenerator.generateVars(this.globVarList);
    _builder.append(_generateVars);
    _builder.newLineIfNotEmpty();
    {
      for(final ProgramGenerator c : this.programs) {
        String _generateProgram = c.generateProgram();
        _builder.append(_generateProgram);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
}
