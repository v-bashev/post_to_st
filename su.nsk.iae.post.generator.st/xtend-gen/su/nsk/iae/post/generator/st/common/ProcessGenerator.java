package su.nsk.iae.post.generator.st.common;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.st.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.st.common.vars.InputOutputVarHelper;
import su.nsk.iae.post.generator.st.common.vars.InputVarHelper;
import su.nsk.iae.post.generator.st.common.vars.OutputVarHelper;
import su.nsk.iae.post.generator.st.common.vars.SimpleVarHelper;
import su.nsk.iae.post.generator.st.common.vars.TempVarHelper;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;
import su.nsk.iae.post.poST.InputVarDeclaration;
import su.nsk.iae.post.poST.OutputVarDeclaration;
import su.nsk.iae.post.poST.State;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class ProcessGenerator {
  private ProgramGenerator program;
  
  private su.nsk.iae.post.poST.Process process;
  
  private VarHelper inVarList = new InputVarHelper();
  
  private VarHelper outVarList = new OutputVarHelper();
  
  private VarHelper inOutVarList = new InputOutputVarHelper();
  
  private VarHelper varList = new SimpleVarHelper();
  
  private VarHelper tempVarList = new TempVarHelper();
  
  private List<StateGenerator> stateList = new LinkedList<StateGenerator>();
  
  public ProcessGenerator(final ProgramGenerator program, final su.nsk.iae.post.poST.Process process) {
    this.program = program;
    this.process = process;
    final Consumer<State> _function = (State s) -> {
      StateGenerator _stateGenerator = new StateGenerator(program, this, s);
      this.stateList.add(_stateGenerator);
    };
    process.getStates().stream().forEach(_function);
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
  
  public String getName() {
    return this.process.getName();
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
  
  public String getVarName(final String variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_p_");
    String _name = this.getName();
    _builder.append(_name);
    _builder.append("_v_");
    _builder.append(variable);
    return _builder.toString();
  }
  
  public boolean containsVar(final String name) {
    return ((((this.varList.contains(name) || this.tempVarList.contains(name)) || 
      this.inVarList.contains(name)) || this.outVarList.contains(name)) || this.inOutVarList.contains(name));
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
        String _generateGlobalTime = GeneratorUtil.generateGlobalTime();
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
          String _generateGlobalTime = GeneratorUtil.generateGlobalTime();
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
        String _generateGlobalTime_1 = GeneratorUtil.generateGlobalTime();
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
        String _generateGlobalTime = GeneratorUtil.generateGlobalTime();
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
  
  public boolean isTemplate() {
    return (((!this.process.getProcInVars().isEmpty()) || (!this.process.getProcOutVars().isEmpty())) || (!this.process.getProcInOutVars().isEmpty()));
  }
  
  public void prepareStateVars() {
    for (int i = 0; (i < this.stateList.size()); i++) {
      this.program.addVar(this.getEnumStateName(this.stateList.get(i).getName()), "INT", Integer.valueOf(i).toString(), true);
    }
    boolean _isFirstProcess = this.program.isFirstProcess(this);
    if (_isFirstProcess) {
      this.program.addVar(this.generateEnumName(), "INT", this.getEnumStateName(this.stateList.get(0).getName()));
    } else {
      this.program.addVar(this.generateEnumName(), "INT", GeneratorUtil.generateStopConstant());
    }
  }
  
  public void prepareTimeVars() {
    boolean _hasTimeouts = this.hasTimeouts();
    if (_hasTimeouts) {
      this.program.addVar(this.generateTimeoutName(), "TIME");
    }
  }
  
  public void prepareProcessVars() {
    this.prepareVars();
    this.prepareTempVars();
    boolean _isTemplate = this.isTemplate();
    if (_isTemplate) {
      this.prepareInVars();
      this.prepareOutVars();
      this.prepareInOutVars();
    }
  }
  
  private void prepareVars() {
    final Consumer<VarDeclaration> _function = (VarDeclaration varDecl) -> {
      this.varList.add(varDecl);
      this.program.addVar(varDecl, this.getVarName(""));
    };
    this.process.getProcVars().stream().forEach(_function);
  }
  
  private void prepareTempVars() {
    final Consumer<TempVarDeclaration> _function = (TempVarDeclaration varDecl) -> {
      this.tempVarList.add(varDecl);
      this.program.addTempVar(varDecl, this.getVarName(""));
    };
    this.process.getProcTempVars().stream().forEach(_function);
  }
  
  private void prepareInVars() {
    final Consumer<InputVarDeclaration> _function = (InputVarDeclaration varDecl) -> {
      this.inVarList.add(varDecl);
      this.program.addInVar(varDecl, this.getVarName(""));
    };
    this.process.getProcInVars().stream().forEach(_function);
  }
  
  private void prepareOutVars() {
    final Consumer<OutputVarDeclaration> _function = (OutputVarDeclaration varDecl) -> {
      this.outVarList.add(varDecl);
      this.program.addOutVar(varDecl, this.getVarName(""));
    };
    this.process.getProcOutVars().stream().forEach(_function);
  }
  
  private void prepareInOutVars() {
    final Consumer<InputOutputVarDeclaration> _function = (InputOutputVarDeclaration varDecl) -> {
      this.inOutVarList.add(varDecl);
      this.program.addInOutVar(varDecl, this.getVarName(""));
    };
    this.process.getProcInOutVars().stream().forEach(_function);
  }
  
  private boolean hasTimeouts() {
    final Predicate<StateGenerator> _function = (StateGenerator x) -> {
      return x.hasTimeout();
    };
    return this.stateList.stream().anyMatch(_function);
  }
}
