package su.nsk.iae.post.generator.st;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.st.ProcessGenerator;
import su.nsk.iae.post.generator.st.vars.ExternalVarHelper;
import su.nsk.iae.post.generator.st.vars.InputOutputVarHelper;
import su.nsk.iae.post.generator.st.vars.InputVarHelper;
import su.nsk.iae.post.generator.st.vars.OutputVarHelper;
import su.nsk.iae.post.generator.st.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.st.vars.TempVarHelper;
import su.nsk.iae.post.generator.st.vars.VarHelper;
import su.nsk.iae.post.poST.ExternalVarDeclaration;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;
import su.nsk.iae.post.poST.InputVarDeclaration;
import su.nsk.iae.post.poST.OutputVarDeclaration;
import su.nsk.iae.post.poST.Program;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class ProgramGenerator {
  private Program program;
  
  private VarHelper inVarList = new InputVarHelper();
  
  private VarHelper outVarList = new OutputVarHelper();
  
  private VarHelper inOutVarList = new InputOutputVarHelper();
  
  private VarHelper externalVarList = new ExternalVarHelper();
  
  private VarHelper varList = new SimpleVarHelper();
  
  private VarHelper tempVarList = new TempVarHelper();
  
  private List<ProcessGenerator> processList = new LinkedList<ProcessGenerator>();
  
  public ProgramGenerator(final Program program) {
    this.program = program;
    EList<InputVarDeclaration> _progInVars = program.getProgInVars();
    for (final InputVarDeclaration v : _progInVars) {
      this.inVarList.add(v);
    }
    EList<OutputVarDeclaration> _progOutVars = program.getProgOutVars();
    for (final OutputVarDeclaration v_1 : _progOutVars) {
      this.outVarList.add(v_1);
    }
    EList<InputOutputVarDeclaration> _progInOutVars = program.getProgInOutVars();
    for (final InputOutputVarDeclaration v_2 : _progInOutVars) {
      this.inOutVarList.add(v_2);
    }
    EList<ExternalVarDeclaration> _progExternVars = program.getProgExternVars();
    for (final ExternalVarDeclaration v_3 : _progExternVars) {
      this.externalVarList.add(v_3);
    }
    EList<VarDeclaration> _progVars = program.getProgVars();
    for (final VarDeclaration v_4 : _progVars) {
      this.varList.add(v_4);
    }
    EList<TempVarDeclaration> _progTempVars = program.getProgTempVars();
    for (final TempVarDeclaration v_5 : _progTempVars) {
      this.tempVarList.add(v_5);
    }
    EList<su.nsk.iae.post.poST.Process> _processes = program.getProcesses();
    for (final su.nsk.iae.post.poST.Process p : _processes) {
      ProcessGenerator _processGenerator = new ProcessGenerator(this, p);
      this.processList.add(_processGenerator);
    }
    this.addVar(this.generateStopConstant(), "INT", "254", true);
    this.addVar(this.generateErrorConstant(), "INT", "255", true);
  }
  
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    String _lowerCase = this.program.getName().toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".st");
    fsa.generateFile(_builder.toString(), this.generateCode());
  }
  
  private String generateCode() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("PROGRAM ");
    String _name = this.program.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    String _generate = this.inVarList.generate();
    _builder.append(_generate, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generate_1 = this.outVarList.generate();
    _builder.append(_generate_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generate_2 = this.inOutVarList.generate();
    _builder.append(_generate_2, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generate_3 = this.externalVarList.generate();
    _builder.append(_generate_3, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generate_4 = this.varList.generate();
    _builder.append(_generate_4, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generate_5 = this.tempVarList.generate();
    _builder.append(_generate_5, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      for(final ProcessGenerator p : this.processList) {
        _builder.append("\t");
        String _generateBody = p.generateBody();
        _builder.append(_generateBody, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.append("END_PROGRAM");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  public String generateStopConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ALL_PROCESSES_STOP_CONSTANT");
    return _builder.toString();
  }
  
  public String generateErrorConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ALL_PROCESSES_ERROR_CONSTANT");
    return _builder.toString();
  }
  
  public String generateProcessEnum(final String processName) {
    final Function1<ProcessGenerator, Boolean> _function = (ProcessGenerator it) -> {
      String _name = it.getName();
      return Boolean.valueOf(Objects.equal(_name, processName));
    };
    return IterableExtensions.<ProcessGenerator>findFirst(this.processList, _function).generateEnumName();
  }
  
  public void addVar(final EObject varDecl) {
    this.varList.add(varDecl);
  }
  
  public void addVar(final String name, final String type) {
    this.varList.add(name, type);
  }
  
  public void addVar(final String name, final String type, final String value) {
    this.varList.add(name, type, value);
  }
  
  public void addVar(final String name, final String type, final String value, final boolean isConstant) {
    this.varList.add(name, type, value, isConstant);
  }
  
  public void addTempVar(final EObject varDecl) {
    this.tempVarList.add(varDecl);
  }
  
  public void addTempVar(final String name, final String type, final String value) {
    this.tempVarList.add(name, type, value);
  }
  
  public boolean isFirstProcess(final ProcessGenerator process) {
    return this.processList.isEmpty();
  }
}
