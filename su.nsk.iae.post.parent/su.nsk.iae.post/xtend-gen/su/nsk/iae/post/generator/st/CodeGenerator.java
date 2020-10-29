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

@SuppressWarnings("all")
public class CodeGenerator {
  protected String codeName;
  
  protected String type;
  
  protected VarHelper inVarList = new InputVarHelper();
  
  protected VarHelper outVarList = new OutputVarHelper();
  
  protected VarHelper inOutVarList = new InputOutputVarHelper();
  
  protected VarHelper externalVarList = new ExternalVarHelper();
  
  protected VarHelper varList = new SimpleVarHelper();
  
  protected VarHelper tempVarList = new TempVarHelper();
  
  private List<ProcessGenerator> processList = new LinkedList<ProcessGenerator>();
  
  protected void parseProcesses(final EList<su.nsk.iae.post.poST.Process> processes) {
    this.addVar(this.generateGlobalTime(), "TIME");
    for (final su.nsk.iae.post.poST.Process p : processes) {
      ProcessGenerator _processGenerator = new ProcessGenerator(this, p);
      this.processList.add(_processGenerator);
    }
    this.addVar(this.generateStopConstant(), "INT", "254", true);
    this.addVar(this.generateErrorConstant(), "INT", "255", true);
  }
  
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    String _lowerCase = this.codeName.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".st");
    fsa.generateFile(_builder.toString(), this.generateCode());
  }
  
  private String generateCode() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.type);
    _builder.append(" ");
    _builder.append(this.codeName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generate = this.inVarList.generate();
    _builder.append(_generate);
    _builder.newLineIfNotEmpty();
    String _generate_1 = this.outVarList.generate();
    _builder.append(_generate_1);
    _builder.newLineIfNotEmpty();
    String _generate_2 = this.inOutVarList.generate();
    _builder.append(_generate_2);
    _builder.newLineIfNotEmpty();
    String _generate_3 = this.externalVarList.generate();
    _builder.append(_generate_3);
    _builder.newLineIfNotEmpty();
    String _generate_4 = this.varList.generate();
    _builder.append(_generate_4);
    _builder.newLineIfNotEmpty();
    String _generate_5 = this.tempVarList.generate();
    _builder.append(_generate_5);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateGlobalTime = this.generateGlobalTime();
    _builder.append(_generateGlobalTime);
    _builder.append(" := TIME();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      for(final ProcessGenerator p : this.processList) {
        String _generateBody = p.generateBody();
        _builder.append(_generateBody);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("END_");
    _builder.append(this.type);
    _builder.newLineIfNotEmpty();
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
  
  public String generateGlobalTime() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("cycle_global_time");
    return _builder.toString();
  }
  
  public String generateProcessEnum(final String processName) {
    final Function1<ProcessGenerator, Boolean> _function = (ProcessGenerator it) -> {
      String _name = it.getName();
      return Boolean.valueOf(Objects.equal(_name, processName));
    };
    return IterableExtensions.<ProcessGenerator>findFirst(this.processList, _function).generateEnumName();
  }
  
  public String generateProcessStart(final String processName) {
    final Function1<ProcessGenerator, Boolean> _function = (ProcessGenerator it) -> {
      String _name = it.getName();
      return Boolean.valueOf(Objects.equal(_name, processName));
    };
    return IterableExtensions.<ProcessGenerator>findFirst(this.processList, _function).generateStart();
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
