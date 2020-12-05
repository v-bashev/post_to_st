package su.nsk.iae.post.generator.xml;

import com.google.common.base.Objects;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.xml.ProcessGenerator;
import su.nsk.iae.post.generator.xml.vars.ExternalVarHelper;
import su.nsk.iae.post.generator.xml.vars.InputOutputVarHelper;
import su.nsk.iae.post.generator.xml.vars.InputVarHelper;
import su.nsk.iae.post.generator.xml.vars.OutputVarHelper;
import su.nsk.iae.post.generator.xml.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.xml.vars.TempVarHelper;
import su.nsk.iae.post.generator.xml.vars.VarHelper;

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
  
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    String _lowerCase = this.codeName.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".xml");
    fsa.generateFile(_builder.toString(), this.generateCode());
  }
  
  private String generateCode() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    _builder.newLine();
    _builder.append("<project xmlns=\"http://www.plcopen.org/xml/tc6_0200\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<fileHeader companyName=\"\" productName=\"CODESYS\" productVersion=\"CODESYS V3.5 SP11\" creationDateTime=\"");
    String _generateCurrentTime = this.generateCurrentTime();
    _builder.append(_generateCurrentTime, "\t");
    _builder.append("\" />");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<contentHeader name=\"");
    _builder.append(this.codeName, "\t");
    _builder.append("_poST.project\">");
    _builder.newLineIfNotEmpty();
    _builder.append(" \t\t");
    _builder.append("<coordinateInfo>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<fbd>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<scaling x=\"1\" y=\"1\" />");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</fbd>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<ld>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<scaling x=\"1\" y=\"1\" />");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</ld>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<sfc>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<scaling x=\"1\" y=\"1\" />");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</sfc>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</coordinateInfo>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<addData>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<data name=\"http://www.3s-software.com/plcopenxml/projectinformation\" handleUnknown=\"implementation\">");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<ProjectInformation />");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</data>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</addData>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</contentHeader>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<types>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<dataTypes />");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<pous>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<pou name=\"");
    _builder.append(this.codeName, "\t\t\t");
    _builder.append("\" pouType=\"");
    String _lowerCase = this.type.toLowerCase();
    _builder.append(_lowerCase, "\t\t\t");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("<interface>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    String _generate = this.varList.generate();
    _builder.append(_generate, "\t\t\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("</interface>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<body>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<ST>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<xhtml xmlns=\"http://www.w3.org/1999/xhtml\">");
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
    _builder.append("</xhtml>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("</ST>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</body>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</pou>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</pous>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</types>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<instances>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<configurations />");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</instances>");
    _builder.newLine();
    _builder.append("</project>");
    _builder.newLine();
    return _builder.toString();
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
  
  private String generateCurrentTime() {
    return new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSSSSS").format(Calendar.getInstance().getTime());
  }
}
