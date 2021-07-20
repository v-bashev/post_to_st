package su.nsk.iae.post.generator.st.common;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import su.nsk.iae.post.generator.st.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.st.common.vars.ExternalVarHelper;
import su.nsk.iae.post.generator.st.common.vars.InputOutputVarHelper;
import su.nsk.iae.post.generator.st.common.vars.InputVarHelper;
import su.nsk.iae.post.generator.st.common.vars.OutputVarHelper;
import su.nsk.iae.post.generator.st.common.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.st.common.vars.TempVarHelper;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;

@SuppressWarnings("all")
public class ProgramGenerator {
  protected String programName;
  
  protected String type;
  
  protected VarHelper inVarList = new InputVarHelper();
  
  protected VarHelper outVarList = new OutputVarHelper();
  
  protected VarHelper inOutVarList = new InputOutputVarHelper();
  
  protected VarHelper externalVarList = new ExternalVarHelper();
  
  protected VarHelper varList = new SimpleVarHelper();
  
  protected VarHelper tempVarList = new TempVarHelper();
  
  protected List<ProcessGenerator> processList = new LinkedList<ProcessGenerator>();
  
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    String _lowerCase = this.programName.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".st");
    fsa.generateFile(_builder.toString(), this.generateProgram());
  }
  
  public String generateProgram() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.type);
    _builder.append(" ");
    _builder.append(this.programName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateVars = ProgramGenerator.generateVars(this.inVarList);
    _builder.append(_generateVars);
    _builder.newLineIfNotEmpty();
    String _generateVars_1 = ProgramGenerator.generateVars(this.outVarList);
    _builder.append(_generateVars_1);
    _builder.newLineIfNotEmpty();
    String _generateVars_2 = ProgramGenerator.generateVars(this.inOutVarList);
    _builder.append(_generateVars_2);
    _builder.newLineIfNotEmpty();
    String _generateVars_3 = ProgramGenerator.generateVars(this.externalVarList);
    _builder.append(_generateVars_3);
    _builder.newLineIfNotEmpty();
    String _generateVars_4 = ProgramGenerator.generateVars(this.varList);
    _builder.append(_generateVars_4);
    _builder.newLineIfNotEmpty();
    String _generateVars_5 = ProgramGenerator.generateVars(this.tempVarList);
    _builder.append(_generateVars_5);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateGlobalTime = GeneratorUtil.generateGlobalTime();
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
  
  public static String generateVars(final VarHelper helper) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = helper.getList().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          boolean _hasConstant = helper.hasConstant();
          if (_hasConstant) {
            String _type = helper.getType();
            _builder.append(_type);
            _builder.append(" CONSTANT");
            _builder.newLineIfNotEmpty();
            {
              List<VarData> _list = helper.getList();
              for(final VarData v : _list) {
                {
                  boolean _isConstant = v.isConstant();
                  if (_isConstant) {
                    _builder.append("\t");
                    String _generateSingleDeclaration = ProgramGenerator.generateSingleDeclaration(v);
                    _builder.append(_generateSingleDeclaration, "\t");
                    String _generateValue = ProgramGenerator.generateValue(v);
                    _builder.append(_generateValue, "\t");
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("END_VAR");
            _builder.newLine();
            _builder.newLine();
          }
        }
        {
          boolean _hasNonConstant = helper.hasNonConstant();
          if (_hasNonConstant) {
            String _type_1 = helper.getType();
            _builder.append(_type_1);
            _builder.newLineIfNotEmpty();
            {
              List<VarData> _list_1 = helper.getList();
              for(final VarData v_1 : _list_1) {
                {
                  boolean _isConstant_1 = v_1.isConstant();
                  boolean _not_1 = (!_isConstant_1);
                  if (_not_1) {
                    _builder.append("\t");
                    String _generateSingleDeclaration_1 = ProgramGenerator.generateSingleDeclaration(v_1);
                    _builder.append(_generateSingleDeclaration_1, "\t");
                    String _generateValue_1 = ProgramGenerator.generateValue(v_1);
                    _builder.append(_generateValue_1, "\t");
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("END_VAR");
            _builder.newLine();
            _builder.newLine();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  protected void parseProcesses(final EList<su.nsk.iae.post.poST.Process> processes) {
    for (final su.nsk.iae.post.poST.Process p : processes) {
      ProcessGenerator _processGenerator = new ProcessGenerator(this, p);
      this.processList.add(_processGenerator);
    }
    this.addVar(GeneratorUtil.generateGlobalTime(), "TIME");
    for (final ProcessGenerator p_1 : this.processList) {
      p_1.addTimeVars();
    }
    for (final ProcessGenerator p_2 : this.processList) {
      p_2.addStateVars();
    }
    this.addVar(GeneratorUtil.generateStopConstant(), "INT", "254", true);
    this.addVar(GeneratorUtil.generateErrorConstant(), "INT", "255", true);
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
  
  private static String generateSingleDeclaration(final VarData data) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = data.getName();
    _builder.append(_name);
    _builder.append(" : ");
    String _type = data.getType();
    _builder.append(_type);
    return _builder.toString();
  }
  
  private static String generateValue(final VarData v) {
    if (((v.getValue() == null) && (v.getArraValues() == null))) {
      StringConcatenation _builder = new StringConcatenation();
      return _builder.toString();
    }
    boolean _isArray = v.isArray();
    if (_isArray) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(" ");
      _builder_1.append(":= [");
      final Function1<String, String> _function = (String it) -> {
        return it;
      };
      String _join = IterableExtensions.join(ListExtensions.<String, String>map(v.getArraValues(), _function), ", ");
      _builder_1.append(_join, " ");
      _builder_1.append("]");
      return _builder_1.toString();
    }
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append(" ");
    _builder_2.append(":= ");
    String _value = v.getValue();
    _builder_2.append(_value, " ");
    return _builder_2.toString();
  }
}
