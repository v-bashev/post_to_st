package su.nsk.iae.post.generator.xml;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.xml.common.vars.ExternalVarHelper;
import su.nsk.iae.post.generator.xml.common.vars.InputOutputVarHelper;
import su.nsk.iae.post.generator.xml.common.vars.InputVarHelper;
import su.nsk.iae.post.generator.xml.common.vars.OutputVarHelper;
import su.nsk.iae.post.generator.xml.common.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.xml.common.vars.TempVarHelper;
import su.nsk.iae.post.generator.xml.common.vars.VarHelper;

@SuppressWarnings("all")
public abstract class ICodeGenerator {
  protected String codeName;
  
  protected String type;
  
  protected VarHelper inVarList = new InputVarHelper();
  
  protected VarHelper outVarList = new OutputVarHelper();
  
  protected VarHelper inOutVarList = new InputOutputVarHelper();
  
  protected VarHelper externalVarList = new ExternalVarHelper();
  
  protected VarHelper varList = new SimpleVarHelper();
  
  protected VarHelper tempVarList = new TempVarHelper();
  
  protected List<ProcessGenerator> processList = new LinkedList<ProcessGenerator>();
  
  public abstract String generateCode();
  
  protected void parseProcesses(final EList<su.nsk.iae.post.poST.Process> processes) {
    for (final su.nsk.iae.post.poST.Process p : processes) {
      ProcessGenerator _processGenerator = new ProcessGenerator(this, p);
      this.processList.add(_processGenerator);
    }
    this.addVar(this.generateGlobalTime(), "TIME");
    for (final ProcessGenerator p_1 : this.processList) {
      p_1.addTimeVars();
    }
    for (final ProcessGenerator p_2 : this.processList) {
      p_2.addStateVars();
    }
    this.addVar(this.generateStopConstant(), "INT", "254", true);
    this.addVar(this.generateErrorConstant(), "INT", "255", true);
  }
  
  public String generateStopConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_STOP");
    return _builder.toString();
  }
  
  public String generateErrorConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_ERROR");
    return _builder.toString();
  }
  
  public String generateGlobalTime() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_global_time");
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
    ProcessGenerator _get = this.processList.get(0);
    return Objects.equal(_get, process);
  }
}
