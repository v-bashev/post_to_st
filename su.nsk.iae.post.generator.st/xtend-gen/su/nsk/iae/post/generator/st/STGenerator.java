package su.nsk.iae.post.generator.st;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import su.nsk.iae.post.generator.IPoSTGenerator;
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;

@SuppressWarnings("all")
public class STGenerator implements IPoSTGenerator {
  private VarHelper globVarList = new GlobalVarHelper();
  
  private List<CodeGenerator> codes = new LinkedList<CodeGenerator>();
  
  @Override
  public void setModel(final Model model) {
    this.globVarList.clear();
    this.codes.clear();
    EList<GlobalVarDeclaration> _globVars = model.getGlobVars();
    for (final GlobalVarDeclaration v : _globVars) {
      this.globVarList.add(v);
    }
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
  
  private void generateMultipleFiles(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    _builder.append("GVL.st");
    fsa.generateFile(_builder.toString(), CodeGenerator.generateVar(this.globVarList));
    for (final CodeGenerator c : this.codes) {
      c.generate(fsa, path);
    }
  }
  
  private String generateSingleFileBody() {
    StringConcatenation _builder = new StringConcatenation();
    String _generateVar = CodeGenerator.generateVar(this.globVarList);
    _builder.append(_generateVar);
    _builder.newLineIfNotEmpty();
    {
      for(final CodeGenerator c : this.codes) {
        String _generateCode = c.generateCode();
        _builder.append(_generateCode);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
}
