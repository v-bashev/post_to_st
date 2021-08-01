package su.nsk.iae.post.generator.st.common;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.st.common.statement.AssignmentStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.CaseStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.ErrorProcessStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.ForStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.IStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.IfStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.RepeatStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.ResetTimerStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.SetStateStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.StartProcessStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.StopProcessStatementGenerator;
import su.nsk.iae.post.generator.st.common.statement.WhileStatementGenerator;
import su.nsk.iae.post.generator.st.common.util.GeneratorUtil;
import su.nsk.iae.post.poST.ArrayVariable;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.ProcessStatusExpression;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.StatementList;
import su.nsk.iae.post.poST.SymbolicVariable;

@SuppressWarnings("all")
public class StatementListGenerator {
  private ProgramGenerator program;
  
  private ProcessGenerator process;
  
  private List<IStatementGenerator> statementGenerators;
  
  public StatementListGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state) {
    this.program = program;
    this.process = process;
    AssignmentStatementGenerator _assignmentStatementGenerator = new AssignmentStatementGenerator(program, process, state, this);
    IfStatementGenerator _ifStatementGenerator = new IfStatementGenerator(program, process, state, this);
    CaseStatementGenerator _caseStatementGenerator = new CaseStatementGenerator(program, process, state, this);
    ForStatementGenerator _forStatementGenerator = new ForStatementGenerator(program, process, state, this);
    WhileStatementGenerator _whileStatementGenerator = new WhileStatementGenerator(program, process, state, this);
    RepeatStatementGenerator _repeatStatementGenerator = new RepeatStatementGenerator(program, process, state, this);
    StartProcessStatementGenerator _startProcessStatementGenerator = new StartProcessStatementGenerator(program, process, state, this);
    StopProcessStatementGenerator _stopProcessStatementGenerator = new StopProcessStatementGenerator(program, process, state, this);
    ErrorProcessStatementGenerator _errorProcessStatementGenerator = new ErrorProcessStatementGenerator(program, process, state, this);
    SetStateStatementGenerator _setStateStatementGenerator = new SetStateStatementGenerator(program, process, state, this);
    ResetTimerStatementGenerator _resetTimerStatementGenerator = new ResetTimerStatementGenerator(program, process, state, this);
    this.statementGenerators = Arrays.<IStatementGenerator>asList(_assignmentStatementGenerator, _ifStatementGenerator, _caseStatementGenerator, _forStatementGenerator, _whileStatementGenerator, _repeatStatementGenerator, _startProcessStatementGenerator, _stopProcessStatementGenerator, _errorProcessStatementGenerator, _setStateStatementGenerator, _resetTimerStatementGenerator);
  }
  
  public String generateStatementList(final StatementList statementList) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Statement> _statements = statementList.getStatements();
      for(final Statement s : _statements) {
        String _generateStatement = this.generateStatement(s);
        _builder.append(_generateStatement);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  public String generateStatement(final Statement statement) {
    for (final IStatementGenerator sg : this.statementGenerators) {
      boolean _checkStatement = sg.checkStatement(statement);
      if (_checkStatement) {
        return sg.generateStatement(statement);
      }
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("RETURN");
    return _builder.toString();
  }
  
  public String generateExpression(final Expression exp) {
    final Function<SymbolicVariable, String> _function = (SymbolicVariable x) -> {
      return this.generateVar(x);
    };
    final Function<ArrayVariable, String> _function_1 = (ArrayVariable x) -> {
      return this.generateArray(x);
    };
    final Function<ProcessStatusExpression, String> _function_2 = (ProcessStatusExpression x) -> {
      return this.generateProcessStatus(x);
    };
    return GeneratorUtil.generateExpression(exp, _function, _function_1, _function_2);
  }
  
  public String generateVar(final SymbolicVariable varName) {
    boolean _containsVar = this.process.containsVar(varName.getName());
    if (_containsVar) {
      return GeneratorUtil.generateVarName(this.process, varName.getName());
    }
    return varName.getName();
  }
  
  public String generateArray(final ArrayVariable varDecl) {
    StringConcatenation _builder = new StringConcatenation();
    String _generateVar = this.generateVar(varDecl.getVariable());
    _builder.append(_generateVar);
    _builder.append("[");
    String _generateExpression = this.generateExpression(varDecl.getIndex());
    _builder.append(_generateExpression);
    _builder.append("]");
    return _builder.toString();
  }
  
  public String generateProcessStatus(final ProcessStatusExpression exp) {
    boolean _isActive = exp.isActive();
    if (_isActive) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("((");
      String _generateProcessEnum = this.program.generateProcessEnum(exp.getProcess().getName());
      _builder.append(_generateProcessEnum);
      _builder.append(" <> ");
      String _generateStopConstant = GeneratorUtil.generateStopConstant();
      _builder.append(_generateStopConstant);
      _builder.append(") AND (");
      String _generateProcessEnum_1 = this.program.generateProcessEnum(exp.getProcess().getName());
      _builder.append(_generateProcessEnum_1);
      _builder.append(" <> ");
      String _generateErrorConstant = GeneratorUtil.generateErrorConstant();
      _builder.append(_generateErrorConstant);
      _builder.append("))");
      return _builder.toString();
    } else {
      boolean _isInactive = exp.isInactive();
      if (_isInactive) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("((");
        String _generateProcessEnum_2 = this.program.generateProcessEnum(exp.getProcess().getName());
        _builder_1.append(_generateProcessEnum_2);
        _builder_1.append(" = ");
        String _generateStopConstant_1 = GeneratorUtil.generateStopConstant();
        _builder_1.append(_generateStopConstant_1);
        _builder_1.append(") OR (");
        String _generateProcessEnum_3 = this.program.generateProcessEnum(exp.getProcess().getName());
        _builder_1.append(_generateProcessEnum_3);
        _builder_1.append(" = ");
        String _generateErrorConstant_1 = GeneratorUtil.generateErrorConstant();
        _builder_1.append(_generateErrorConstant_1);
        _builder_1.append("))");
        return _builder_1.toString();
      } else {
        boolean _isStop = exp.isStop();
        if (_isStop) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("(");
          String _generateProcessEnum_4 = this.program.generateProcessEnum(exp.getProcess().getName());
          _builder_2.append(_generateProcessEnum_4);
          _builder_2.append(" = ");
          String _generateStopConstant_2 = GeneratorUtil.generateStopConstant();
          _builder_2.append(_generateStopConstant_2);
          _builder_2.append(")");
          return _builder_2.toString();
        }
      }
    }
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("(");
    String _generateProcessEnum_5 = this.program.generateProcessEnum(exp.getProcess().getName());
    _builder_3.append(_generateProcessEnum_5);
    _builder_3.append(" = ");
    String _generateErrorConstant_2 = GeneratorUtil.generateErrorConstant();
    _builder_3.append(_generateErrorConstant_2);
    _builder_3.append(")");
    return _builder_3.toString();
  }
}
