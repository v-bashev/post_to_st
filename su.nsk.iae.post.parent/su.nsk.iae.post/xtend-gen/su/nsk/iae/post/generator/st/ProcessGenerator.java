package su.nsk.iae.post.generator.st;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.st.ProgramGenerator;
import su.nsk.iae.post.generator.st.StateGenerator;
import su.nsk.iae.post.generator.st.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.st.vars.TempVarHelper;
import su.nsk.iae.post.generator.st.vars.VarHelper;
import su.nsk.iae.post.generator.st.vars.data.VarData;
import su.nsk.iae.post.poST.State;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;
import su.nsk.iae.post.poST.VarInitDeclaration;

@SuppressWarnings("all")
public class ProcessGenerator {
  private su.nsk.iae.post.poST.Process process;
  
  private VarHelper varList = new SimpleVarHelper();
  
  private VarHelper tempVarList = new TempVarHelper();
  
  private List<StateGenerator> stateList = new LinkedList<StateGenerator>();
  
  public ProcessGenerator(final ProgramGenerator program, final su.nsk.iae.post.poST.Process process) {
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
      }
    }
    EList<State> _states = process.getStates();
    for (final State s : _states) {
      StateGenerator _stateGenerator = new StateGenerator(program, this, s);
      this.stateList.add(_stateGenerator);
    }
    int _size = this.stateList.size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      for (int i = 0; (i < this.stateList.size()); i++) {
        program.addVar(this.getEnumStateName(this.stateList.get(i).getName()), "INT", Integer.valueOf(i).toString(), true);
      }
      boolean _isFirstProcess = program.isFirstProcess(this);
      if (_isFirstProcess) {
        program.addVar(this.generateEnumName(), "INT", this.getEnumStateName(this.stateList.get(0).getName()));
      } else {
        program.addVar(this.generateEnumName(), "INT", program.generateStopConstant());
      }
    }
    boolean _hasTimeouts = this.hasTimeouts();
    if (_hasTimeouts) {
      program.addVar(this.generateTimeoutName(), "TON");
    }
  }
  
  public String getName() {
    return this.process.getName();
  }
  
  public String getEnumStateName(final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("PROCESS_");
    String _upperCase = this.getName().toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_STATE_");
    String _upperCase_1 = name.toUpperCase();
    _builder.append(_upperCase_1);
    return _builder.toString();
  }
  
  public boolean containsVar(final String name) {
    return (this.varList.contains(name) || this.tempVarList.contains(name));
  }
  
  public String getVarName(final String variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("process_");
    String _lowerCase = this.getName().toLowerCase();
    _builder.append(_lowerCase);
    _builder.append("_var_");
    _builder.append(variable);
    return _builder.toString();
  }
  
  public String getNextState(final StateGenerator state) {
    int _indexOf = this.stateList.indexOf(state);
    int _plus = (_indexOf + 1);
    int _size = this.stateList.size();
    boolean _lessThan = (_plus < _size);
    if (_lessThan) {
      int _indexOf_1 = this.stateList.indexOf(state);
      int _plus_1 = (_indexOf_1 + 1);
      return this.getEnumStateName(this.stateList.get(_plus_1).getName());
    }
    return this.getEnumStateName(this.stateList.get(0).getName());
  }
  
  public String generateEnumName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("g_process_");
    String _lowerCase = this.getName().toLowerCase();
    _builder.append(_lowerCase);
    _builder.append("_current_state");
    return _builder.toString();
  }
  
  public String generateTimeoutName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("g_process_");
    String _lowerCase = this.getName().toLowerCase();
    _builder.append(_lowerCase);
    _builder.append("_timeout_TON");
    return _builder.toString();
  }
  
  public String generateStart() {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<VarData> _list = this.varList.getList();
      for(final VarData v : _list) {
        {
          String _value = v.getValue();
          boolean _tripleNotEquals = (_value != null);
          if (_tripleNotEquals) {
            String _varName = this.getVarName(v.getName());
            _builder.append(_varName);
            _builder.append(" := ");
            String _value_1 = v.getValue();
            _builder.append(_value_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    String _generateEnumName = this.generateEnumName();
    _builder.append(_generateEnumName);
    _builder.append(" = ");
    String _enumStateName = this.getEnumStateName(this.stateList.get(0).getName());
    _builder.append(_enumStateName);
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
    _builder.append("\t");
    _builder.append("ELSE");
    _builder.newLine();
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
