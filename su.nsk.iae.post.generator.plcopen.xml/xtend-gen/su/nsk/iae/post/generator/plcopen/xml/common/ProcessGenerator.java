package su.nsk.iae.post.generator.plcopen.xml.common;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.plcopen.xml.ICodeGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.TempVarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData;
import su.nsk.iae.post.poST.State;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;
import su.nsk.iae.post.poST.VarInitDeclaration;

@SuppressWarnings("all")
public class ProcessGenerator {
  private ICodeGenerator program;
  
  private su.nsk.iae.post.poST.Process process;
  
  private VarHelper varList = new SimpleVarHelper();
  
  private VarHelper tempVarList = new TempVarHelper();
  
  private List<StateGenerator> stateList = new LinkedList<StateGenerator>();
  
  public ProcessGenerator(final ICodeGenerator program, final su.nsk.iae.post.poST.Process process) {
    this.program = program;
    this.process = process;
    EList<VarDeclaration> _procVars = process.getProcVars();
    for (final VarDeclaration v : _procVars) {
      {
        this.varList.add(v);
        EList<VarInitDeclaration> _vars = v.getVars();
        for (final VarInitDeclaration n : _vars) {
          EList<SymbolicVariable> _vars_1 = n.getVarList().getVars();
          for (final SymbolicVariable e : _vars_1) {
            e.setName(this.getVarName(e.getName()));
          }
        }
        program.addVar(v);
        EList<VarInitDeclaration> _vars_2 = v.getVars();
        for (final VarInitDeclaration n_1 : _vars_2) {
          EList<SymbolicVariable> _vars_3 = n_1.getVarList().getVars();
          for (final SymbolicVariable e_1 : _vars_3) {
            e_1.setName(e_1.getName().substring(this.getVarName("").length()));
          }
        }
      }
    }
    EList<TempVarDeclaration> _procTempVars = process.getProcTempVars();
    for (final TempVarDeclaration v_1 : _procTempVars) {
      {
        this.tempVarList.add(v_1);
        EList<VarInitDeclaration> _vars = v_1.getVars();
        for (final VarInitDeclaration n : _vars) {
          EList<SymbolicVariable> _vars_1 = n.getVarList().getVars();
          for (final SymbolicVariable e : _vars_1) {
            e.setName(this.getVarName(e.getName()));
          }
        }
        program.addTempVar(v_1);
        EList<VarInitDeclaration> _vars_2 = v_1.getVars();
        for (final VarInitDeclaration n_1 : _vars_2) {
          EList<SymbolicVariable> _vars_3 = n_1.getVarList().getVars();
          for (final SymbolicVariable e_1 : _vars_3) {
            e_1.setName(e_1.getName().substring(this.getVarName("").length()));
          }
        }
      }
    }
    EList<State> _states = process.getStates();
    for (final State s : _states) {
      StateGenerator _stateGenerator = new StateGenerator(program, this, s);
      this.stateList.add(_stateGenerator);
    }
  }
  
  public void addStateVars() {
    for (int i = 0; (i < this.stateList.size()); i++) {
      this.program.addVar(this.getEnumStateName(this.stateList.get(i).getName()), "INT", Integer.valueOf(i).toString(), true);
    }
    boolean _isFirstProcess = this.program.isFirstProcess(this);
    if (_isFirstProcess) {
      this.program.addVar(this.generateEnumName(), "INT", this.getEnumStateName(this.stateList.get(0).getName()));
    } else {
      this.program.addVar(this.generateEnumName(), "INT", this.program.generateStopConstant());
    }
  }
  
  public void addTimeVars() {
    boolean _hasTimeouts = this.hasTimeouts();
    if (_hasTimeouts) {
      this.program.addVar(this.generateTimeoutName(), "TIME");
    }
  }
  
  public String getName() {
    return this.process.getName();
  }
  
  public String getEnumStateName(final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_P_");
    String _upperCase = this.getName().toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_S_");
    String _upperCase_1 = name.toUpperCase();
    _builder.append(_upperCase_1);
    return _builder.toString();
  }
  
  public boolean containsVar(final String name) {
    return (this.varList.contains(name) || this.tempVarList.contains(name));
  }
  
  public String getVarName(final String variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_p_");
    String _name = this.getName();
    _builder.append(_name);
    _builder.append("_v_");
    _builder.append(variable);
    return _builder.toString();
  }
  
  public String generateSetState(final String stateName) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<StateGenerator, Boolean> _function = (StateGenerator it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, stateName));
      };
      boolean _hasTimeout = IterableExtensions.<StateGenerator>findFirst(this.stateList, _function).hasTimeout();
      if (_hasTimeout) {
        String _generateTimeoutName = this.generateTimeoutName();
        _builder.append(_generateTimeoutName);
        _builder.append(" := ");
        String _generateGlobalTime = this.program.generateGlobalTime();
        _builder.append(_generateGlobalTime);
        _builder.append(";");
      }
    }
    _builder.newLineIfNotEmpty();
    String _generateEnumName = this.generateEnumName();
    _builder.append(_generateEnumName);
    _builder.append(" := ");
    String _enumStateName = this.getEnumStateName(stateName);
    _builder.append(_enumStateName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String generateNextState(final StateGenerator state) {
    int _indexOf = this.stateList.indexOf(state);
    int _plus = (_indexOf + 1);
    int _size = this.stateList.size();
    boolean _lessThan = (_plus < _size);
    if (_lessThan) {
      int _indexOf_1 = this.stateList.indexOf(state);
      int _plus_1 = (_indexOf_1 + 1);
      final StateGenerator s = this.stateList.get(_plus_1);
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _hasTimeout = s.hasTimeout();
        if (_hasTimeout) {
          String _generateTimeoutName = this.generateTimeoutName();
          _builder.append(_generateTimeoutName);
          _builder.append(" := ");
          String _generateGlobalTime = this.program.generateGlobalTime();
          _builder.append(_generateGlobalTime);
          _builder.append(";");
        }
      }
      _builder.newLineIfNotEmpty();
      String _generateEnumName = this.generateEnumName();
      _builder.append(_generateEnumName);
      _builder.append(" := ");
      String _enumStateName = this.getEnumStateName(s.getName());
      _builder.append(_enumStateName);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    }
    final StateGenerator s_1 = this.stateList.get(0);
    StringConcatenation _builder_1 = new StringConcatenation();
    {
      boolean _hasTimeout_1 = s_1.hasTimeout();
      if (_hasTimeout_1) {
        String _generateTimeoutName_1 = this.generateTimeoutName();
        _builder_1.append(_generateTimeoutName_1);
        _builder_1.append(" := ");
        String _generateGlobalTime_1 = this.program.generateGlobalTime();
        _builder_1.append(_generateGlobalTime_1);
        _builder_1.append(";");
      }
    }
    _builder_1.newLineIfNotEmpty();
    String _generateEnumName_1 = this.generateEnumName();
    _builder_1.append(_generateEnumName_1);
    _builder_1.append(" := ");
    String _enumStateName_1 = this.getEnumStateName(s_1.getName());
    _builder_1.append(_enumStateName_1);
    _builder_1.append(";");
    _builder_1.newLineIfNotEmpty();
    return _builder_1.toString();
  }
  
  public String generateEnumName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_g_p_");
    String _name = this.getName();
    _builder.append(_name);
    _builder.append("_state");
    return _builder.toString();
  }
  
  public String generateTimeoutName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_g_p_");
    String _name = this.getName();
    _builder.append(_name);
    _builder.append("_time");
    return _builder.toString();
  }
  
  public String generateStart() {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<VarData> _list = this.varList.getList();
      for(final VarData v : _list) {
        {
          if (((v.getValue() != null) && (!v.isConstant()))) {
            String _varName = this.getVarName(v.getName());
            _builder.append(_varName);
            _builder.append(" := ");
            String _value = v.getValue();
            _builder.append(_value);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasTimeout = this.stateList.get(0).hasTimeout();
      if (_hasTimeout) {
        String _generateTimeoutName = this.generateTimeoutName();
        _builder.append(_generateTimeoutName);
        _builder.append(" := ");
        String _generateGlobalTime = this.program.generateGlobalTime();
        _builder.append(_generateGlobalTime);
        _builder.append(";");
      }
    }
    _builder.newLineIfNotEmpty();
    String _generateEnumName = this.generateEnumName();
    _builder.append(_generateEnumName);
    _builder.append(" := ");
    String _enumStateName = this.getEnumStateName(this.stateList.get(0).getName());
    _builder.append(_enumStateName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String generateBody() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CASE ");
    String _generateEnumName = this.generateEnumName();
    _builder.append(_generateEnumName);
    _builder.append(" OF");
    _builder.newLineIfNotEmpty();
    {
      for(final StateGenerator s : this.stateList) {
        _builder.append("\t");
        String _enumStateName = this.getEnumStateName(s.getName());
        _builder.append(_enumStateName, "\t");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        String _generateBody = s.generateBody();
        _builder.append(_generateBody, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("END_CASE");
    _builder.newLine();
    return _builder.toString();
  }
  
  private boolean hasTimeouts() {
    for (final StateGenerator s : this.stateList) {
      boolean _hasTimeout = s.hasTimeout();
      if (_hasTimeout) {
        return true;
      }
    }
    return false;
  }
}
