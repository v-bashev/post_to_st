package su.nsk.iae.post.generator.st.common;

import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.List;
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
import su.nsk.iae.post.poST.AddExpression;
import su.nsk.iae.post.poST.AddOperator;
import su.nsk.iae.post.poST.AndExpression;
import su.nsk.iae.post.poST.ArrayVariable;
import su.nsk.iae.post.poST.CompExpression;
import su.nsk.iae.post.poST.CompOperator;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.EquExpression;
import su.nsk.iae.post.poST.EquOperator;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.MulExpression;
import su.nsk.iae.post.poST.MulOperator;
import su.nsk.iae.post.poST.PowerExpression;
import su.nsk.iae.post.poST.PrimaryExpression;
import su.nsk.iae.post.poST.ProcessStatusExpression;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.StatementList;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.UnaryExpression;
import su.nsk.iae.post.poST.UnaryOperator;
import su.nsk.iae.post.poST.XorExpression;

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
    boolean _matched = false;
    if (exp instanceof PrimaryExpression) {
      _matched=true;
      Constant _const = ((PrimaryExpression)exp).getConst();
      boolean _tripleNotEquals = (_const != null);
      if (_tripleNotEquals) {
        return GeneratorUtil.generateConstant(((PrimaryExpression)exp).getConst());
      } else {
        SymbolicVariable _variable = ((PrimaryExpression)exp).getVariable();
        boolean _tripleNotEquals_1 = (_variable != null);
        if (_tripleNotEquals_1) {
          return this.generateVar(((PrimaryExpression)exp).getVariable());
        } else {
          ArrayVariable _array = ((PrimaryExpression)exp).getArray();
          boolean _tripleNotEquals_2 = (_array != null);
          if (_tripleNotEquals_2) {
            return this.generateArray(((PrimaryExpression)exp).getArray());
          } else {
            ProcessStatusExpression _procStatus = ((PrimaryExpression)exp).getProcStatus();
            boolean _tripleNotEquals_3 = (_procStatus != null);
            if (_tripleNotEquals_3) {
              StringConcatenation _builder = new StringConcatenation();
              String _generateProcessStatus = this.generateProcessStatus(((PrimaryExpression)exp).getProcStatus());
              _builder.append(_generateProcessStatus);
              return _builder.toString();
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("(");
              String _generateExpression = this.generateExpression(((PrimaryExpression)exp).getNestExpr());
              _builder_1.append(_generateExpression);
              _builder_1.append(")");
              return _builder_1.toString();
            }
          }
        }
      }
    }
    if (!_matched) {
      if (exp instanceof UnaryExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          UnaryOperator _unOp = ((UnaryExpression)exp).getUnOp();
          boolean _equals = Objects.equal(_unOp, UnaryOperator.NOT);
          if (_equals) {
            _builder.append("NOT ");
          } else {
            UnaryOperator _unOp_1 = ((UnaryExpression)exp).getUnOp();
            boolean _equals_1 = Objects.equal(_unOp_1, UnaryOperator.UNMINUS);
            if (_equals_1) {
              _builder.append("-");
            }
          }
        }
        String _generateExpression = this.generateExpression(((UnaryExpression)exp).getRight());
        _builder.append(_generateExpression);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof PowerExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((PowerExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" ** ");
        String _generateExpression_1 = this.generateExpression(((PowerExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof MulExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((MulExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" ");
        String _generateMulOperators = this.generateMulOperators(((MulExpression)exp).getMulOp());
        _builder.append(_generateMulOperators);
        _builder.append(" ");
        String _generateExpression_1 = this.generateExpression(((MulExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof AddExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((AddExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" ");
        {
          AddOperator _addOp = ((AddExpression)exp).getAddOp();
          boolean _equals = Objects.equal(_addOp, AddOperator.PLUS);
          if (_equals) {
            _builder.append("+");
          } else {
            _builder.append("-");
          }
        }
        _builder.append(" ");
        String _generateExpression_1 = this.generateExpression(((AddExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof EquExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((EquExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" ");
        String _generateEquOperators = this.generateEquOperators(((EquExpression)exp).getEquOp());
        _builder.append(_generateEquOperators);
        _builder.append(" ");
        String _generateExpression_1 = this.generateExpression(((EquExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof CompExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((CompExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" ");
        {
          CompOperator _compOp = ((CompExpression)exp).getCompOp();
          boolean _equals = Objects.equal(_compOp, CompOperator.EQUAL);
          if (_equals) {
            _builder.append("=");
          } else {
            _builder.append("<>");
          }
        }
        _builder.append(" ");
        String _generateExpression_1 = this.generateExpression(((CompExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof AndExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((AndExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" AND ");
        String _generateExpression_1 = this.generateExpression(((AndExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof XorExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(((XorExpression)exp).getLeft());
        _builder.append(_generateExpression);
        _builder.append(" XOR ");
        String _generateExpression_1 = this.generateExpression(((XorExpression)exp).getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof Expression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = this.generateExpression(exp.getLeft());
        _builder.append(_generateExpression);
        _builder.append(" OR ");
        String _generateExpression_1 = this.generateExpression(exp.getRight());
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    return null;
  }
  
  public String generateVar(final SymbolicVariable varName) {
    boolean _containsVar = this.process.containsVar(varName.getName());
    if (_containsVar) {
      return this.process.getVarName(varName.getName());
    }
    return varName.getName();
  }
  
  public String generateArray(final ArrayVariable varDecl) {
    StringConcatenation _builder = new StringConcatenation();
    String _generateVar = this.generateVar(varDecl.getVarName());
    _builder.append(_generateVar);
    _builder.append("[");
    String _generateExpression = this.generateExpression(varDecl.getIndex());
    _builder.append(_generateExpression);
    _builder.append("]");
    return _builder.toString();
  }
  
  public String generateEquOperators(final EquOperator op) {
    if (op != null) {
      switch (op) {
        case LESS:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("<");
          return _builder.toString();
        case LESS_EQU:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("<=");
          return _builder_1.toString();
        case GREATER:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(">");
          return _builder_2.toString();
        case GREATER_EQU:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append(">=");
          return _builder_3.toString();
        default:
          break;
      }
    }
    return null;
  }
  
  public String generateMulOperators(final MulOperator op) {
    if (op != null) {
      switch (op) {
        case MUL:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("*");
          return _builder.toString();
        case DIV:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("/");
          return _builder_1.toString();
        case MOD:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("MOD");
          return _builder_2.toString();
        default:
          break;
      }
    }
    return null;
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
