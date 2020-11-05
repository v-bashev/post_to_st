package su.nsk.iae.post.generator.st;

import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import su.nsk.iae.post.generator.st.CodeGenerator;
import su.nsk.iae.post.generator.st.ProcessGenerator;
import su.nsk.iae.post.poST.AddExpression;
import su.nsk.iae.post.poST.AddOperator;
import su.nsk.iae.post.poST.AndExpression;
import su.nsk.iae.post.poST.AssignmentStatement;
import su.nsk.iae.post.poST.CaseElement;
import su.nsk.iae.post.poST.CaseStatement;
import su.nsk.iae.post.poST.CompExpression;
import su.nsk.iae.post.poST.CompOperator;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.EquExpression;
import su.nsk.iae.post.poST.EquOperator;
import su.nsk.iae.post.poST.ErrorProcessStatement;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.ForStatement;
import su.nsk.iae.post.poST.IfStatement;
import su.nsk.iae.post.poST.MulExpression;
import su.nsk.iae.post.poST.MulOperator;
import su.nsk.iae.post.poST.PowerExpression;
import su.nsk.iae.post.poST.PrimaryExpression;
import su.nsk.iae.post.poST.ProcessStatusExpression;
import su.nsk.iae.post.poST.RepeatStatement;
import su.nsk.iae.post.poST.ResetTimerStatement;
import su.nsk.iae.post.poST.SetStateStatement;
import su.nsk.iae.post.poST.SignedInteger;
import su.nsk.iae.post.poST.StartProcessStatement;
import su.nsk.iae.post.poST.State;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.StatementList;
import su.nsk.iae.post.poST.StopProcessStatement;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.TimeoutStatement;
import su.nsk.iae.post.poST.UnaryExpression;
import su.nsk.iae.post.poST.UnaryOperator;
import su.nsk.iae.post.poST.WhileStatement;
import su.nsk.iae.post.poST.XorExpression;

@SuppressWarnings("all")
public class StateGenerator {
  private CodeGenerator program;
  
  private ProcessGenerator process;
  
  private State state;
  
  public StateGenerator(final CodeGenerator program, final ProcessGenerator process, final State state) {
    this.program = program;
    this.process = process;
    this.state = state;
  }
  
  public String generateBody() {
    StringConcatenation _builder = new StringConcatenation();
    String _generateStatementList = this.generateStatementList(this.state.getStatement());
    _builder.append(_generateStatementList);
    _builder.newLineIfNotEmpty();
    {
      TimeoutStatement _timeout = this.state.getTimeout();
      boolean _tripleNotEquals = (_timeout != null);
      if (_tripleNotEquals) {
        _builder.append("IF (");
        String _generateGlobalTime = this.program.generateGlobalTime();
        _builder.append(_generateGlobalTime);
        _builder.append(" - ");
        String _generateTimeoutName = this.process.generateTimeoutName();
        _builder.append(_generateTimeoutName);
        _builder.append(") >= ");
        {
          SymbolicVariable _variable = this.state.getTimeout().getVariable();
          boolean _tripleNotEquals_1 = (_variable != null);
          if (_tripleNotEquals_1) {
            String _generateVar = this.generateVar(this.state.getTimeout().getVariable());
            _builder.append(_generateVar);
          } else {
            String _trim = NodeModelUtils.getNode(this.state.getTimeout().getConst()).getText().trim();
            _builder.append(_trim);
          }
        }
        _builder.append(" THEN");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _generateStatementList_1 = this.generateStatementList(this.state.getTimeout().getStatement());
        _builder.append(_generateStatementList_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("END_IF");
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  public String getName() {
    return this.state.getName();
  }
  
  public boolean hasTimeout() {
    TimeoutStatement _timeout = this.state.getTimeout();
    return (_timeout != null);
  }
  
  private String generateStatementList(final StatementList statementList) {
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
  
  private String generateStatement(final Statement s) {
    boolean _matched = false;
    if (s instanceof AssignmentStatement) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _generateVar = this.generateVar(((AssignmentStatement)s).getVariable());
      _builder.append(_generateVar);
      _builder.append(" := ");
      String _generateExpression = this.generateExpression(((AssignmentStatement)s).getValue());
      _builder.append(_generateExpression);
      _builder.append(";");
      return _builder.toString();
    }
    if (!_matched) {
      if (s instanceof IfStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("IF ");
        String _generateExpression = this.generateExpression(((IfStatement)s).getMainCond());
        _builder.append(_generateExpression);
        _builder.append(" THEN");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _generateStatementList = this.generateStatementList(((IfStatement)s).getMainStatement());
        _builder.append(_generateStatementList, "\t");
        _builder.newLineIfNotEmpty();
        {
          boolean _isEmpty = ((IfStatement)s).getElseIfCond().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            {
              int _size = ((IfStatement)s).getElseIfCond().size();
              int _minus = (_size - 1);
              IntegerRange _upTo = new IntegerRange(0, _minus);
              for(final Integer i : _upTo) {
                _builder.append("ELSIF ");
                String _generateExpression_1 = this.generateExpression(((IfStatement)s).getElseIfCond().get((i).intValue()));
                _builder.append(_generateExpression_1);
                _builder.append(" THEN");
                _builder.newLineIfNotEmpty();
                _builder.append("\t");
                String _generateStatementList_1 = this.generateStatementList(((IfStatement)s).getElseIfStatements().get((i).intValue()));
                _builder.append(_generateStatementList_1, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          StatementList _elseStatement = ((IfStatement)s).getElseStatement();
          boolean _tripleNotEquals = (_elseStatement != null);
          if (_tripleNotEquals) {
            _builder.append("ELSE");
            _builder.newLine();
            _builder.append("\t");
            String _generateStatementList_2 = this.generateStatementList(((IfStatement)s).getElseStatement());
            _builder.append(_generateStatementList_2, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("END_IF");
        _builder.newLine();
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof CaseStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("CASE ");
        String _generateExpression = this.generateExpression(((CaseStatement)s).getCond());
        _builder.append(_generateExpression);
        _builder.append(" OF");
        _builder.newLineIfNotEmpty();
        {
          EList<CaseElement> _caseElements = ((CaseStatement)s).getCaseElements();
          for(final CaseElement e : _caseElements) {
            {
              EList<SignedInteger> _caseListElement = e.getCaseList().getCaseListElement();
              for(final SignedInteger c : _caseListElement) {
                _builder.append("\t");
                String _generateSignedInteger = this.generateSignedInteger(c);
                _builder.append(_generateSignedInteger, "\t");
                {
                  int _indexOf = e.getCaseList().getCaseListElement().indexOf(c);
                  int _size = e.getCaseList().getCaseListElement().size();
                  int _minus = (_size - 1);
                  boolean _lessThan = (_indexOf < _minus);
                  if (_lessThan) {
                    _builder.append(", ");
                  } else {
                    _builder.append(":");
                  }
                }
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t");
            _builder.append("\t");
            String _generateStatementList = this.generateStatementList(e.getStatement());
            _builder.append(_generateStatementList, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          StatementList _elseStatement = ((CaseStatement)s).getElseStatement();
          boolean _tripleNotEquals = (_elseStatement != null);
          if (_tripleNotEquals) {
            _builder.append("\t");
            _builder.append("ELSE");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            String _generateStatementList_1 = this.generateStatementList(((CaseStatement)s).getElseStatement());
            _builder.append(_generateStatementList_1, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("END_CASE");
        _builder.newLine();
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof ForStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("FOR ");
        String _generateVar = this.generateVar(((ForStatement)s).getVariable());
        _builder.append(_generateVar);
        _builder.append(" := ");
        String _generateExpression = this.generateExpression(((ForStatement)s).getForList().getStart());
        _builder.append(_generateExpression);
        _builder.append(" TO ");
        String _generateExpression_1 = this.generateExpression(((ForStatement)s).getForList().getEnd());
        _builder.append(_generateExpression_1);
        {
          Expression _step = ((ForStatement)s).getForList().getStep();
          boolean _tripleNotEquals = (_step != null);
          if (_tripleNotEquals) {
            _builder.append(" BY ");
            String _generateExpression_2 = this.generateExpression(((ForStatement)s).getForList().getStep());
            _builder.append(_generateExpression_2);
          }
        }
        _builder.append(" DO");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _generateStatementList = this.generateStatementList(((ForStatement)s).getStatement());
        _builder.append(_generateStatementList, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("END_FOR");
        _builder.newLine();
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof WhileStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("WHILE ");
        String _generateExpression = this.generateExpression(((WhileStatement)s).getCond());
        _builder.append(_generateExpression);
        _builder.append(" DO");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _generateStatementList = this.generateStatementList(((WhileStatement)s).getStatement());
        _builder.append(_generateStatementList, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("END_WHILE");
        _builder.newLine();
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof RepeatStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("REPEAT");
        _builder.newLine();
        _builder.append("\t");
        String _generateStatementList = this.generateStatementList(((RepeatStatement)s).getStatement());
        _builder.append(_generateStatementList, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("UNTIL ");
        String _generateExpression = this.generateExpression(((RepeatStatement)s).getCond());
        _builder.append(_generateExpression);
        _builder.append(" END_REPEAT");
        _builder.newLineIfNotEmpty();
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof StartProcessStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          su.nsk.iae.post.poST.Process _process = ((StartProcessStatement)s).getProcess();
          boolean _tripleNotEquals = (_process != null);
          if (_tripleNotEquals) {
            String _generateProcessStart = this.program.generateProcessStart(((StartProcessStatement)s).getProcess().getName());
            _builder.append(_generateProcessStart);
          } else {
            String _generateStart = this.process.generateStart();
            _builder.append(_generateStart);
          }
        }
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof StopProcessStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          su.nsk.iae.post.poST.Process _process = ((StopProcessStatement)s).getProcess();
          boolean _tripleNotEquals = (_process != null);
          if (_tripleNotEquals) {
            String _generateProcessEnum = this.program.generateProcessEnum(((StopProcessStatement)s).getProcess().getName());
            _builder.append(_generateProcessEnum);
          } else {
            String _generateEnumName = this.process.generateEnumName();
            _builder.append(_generateEnumName);
          }
        }
        _builder.append(" := ");
        String _generateStopConstant = this.program.generateStopConstant();
        _builder.append(_generateStopConstant);
        _builder.append(";");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof ErrorProcessStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          su.nsk.iae.post.poST.Process _process = ((ErrorProcessStatement)s).getProcess();
          boolean _tripleNotEquals = (_process != null);
          if (_tripleNotEquals) {
            String _generateProcessEnum = this.program.generateProcessEnum(((ErrorProcessStatement)s).getProcess().getName());
            _builder.append(_generateProcessEnum);
          } else {
            String _generateEnumName = this.process.generateEnumName();
            _builder.append(_generateEnumName);
          }
        }
        _builder.append(" := ");
        String _generateErrorConstant = this.program.generateErrorConstant();
        _builder.append(_generateErrorConstant);
        _builder.append(";");
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof SetStateStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _isNext = ((SetStateStatement)s).isNext();
          if (_isNext) {
            String _generateNextState = this.process.generateNextState(this);
            _builder.append(_generateNextState);
          } else {
            String _generateSetState = this.process.generateSetState(((SetStateStatement)s).getState().getName());
            _builder.append(_generateSetState);
          }
        }
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (s instanceof ResetTimerStatement) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateTimeoutName = this.process.generateTimeoutName();
        _builder.append(_generateTimeoutName);
        _builder.append(" := ");
        String _generateGlobalTime = this.program.generateGlobalTime();
        _builder.append(_generateGlobalTime);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        return _builder.toString();
      }
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("RETURN");
    return _builder.toString();
  }
  
  private String generateExpression(final Expression exp) {
    boolean _matched = false;
    if (exp instanceof PrimaryExpression) {
      _matched=true;
      Constant _const = ((PrimaryExpression)exp).getConst();
      boolean _tripleNotEquals = (_const != null);
      if (_tripleNotEquals) {
        return NodeModelUtils.getNode(((PrimaryExpression)exp).getConst()).getText().trim();
      } else {
        SymbolicVariable _variable = ((PrimaryExpression)exp).getVariable();
        boolean _tripleNotEquals_1 = (_variable != null);
        if (_tripleNotEquals_1) {
          return this.generateVar(((PrimaryExpression)exp).getVariable());
        } else {
          ProcessStatusExpression _procStatus = ((PrimaryExpression)exp).getProcStatus();
          boolean _tripleNotEquals_2 = (_procStatus != null);
          if (_tripleNotEquals_2) {
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
  
  private String generateVar(final SymbolicVariable varName) {
    boolean _containsVar = this.process.containsVar(varName.getName());
    if (_containsVar) {
      return this.process.getVarName(varName.getName());
    }
    return varName.getName();
  }
  
  private String generateEquOperators(final EquOperator op) {
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
  
  private String generateMulOperators(final MulOperator op) {
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
  
  private String generateProcessStatus(final ProcessStatusExpression exp) {
    boolean _isActive = exp.isActive();
    if (_isActive) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("((");
      String _generateProcessEnum = this.program.generateProcessEnum(exp.getProcess().getName());
      _builder.append(_generateProcessEnum);
      _builder.append(" <> ");
      String _generateStopConstant = this.program.generateStopConstant();
      _builder.append(_generateStopConstant);
      _builder.append(") AND (");
      String _generateProcessEnum_1 = this.program.generateProcessEnum(exp.getProcess().getName());
      _builder.append(_generateProcessEnum_1);
      _builder.append(" <> ");
      String _generateErrorConstant = this.program.generateErrorConstant();
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
        String _generateStopConstant_1 = this.program.generateStopConstant();
        _builder_1.append(_generateStopConstant_1);
        _builder_1.append(") OR (");
        String _generateProcessEnum_3 = this.program.generateProcessEnum(exp.getProcess().getName());
        _builder_1.append(_generateProcessEnum_3);
        _builder_1.append(" = ");
        String _generateErrorConstant_1 = this.program.generateErrorConstant();
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
          String _generateStopConstant_2 = this.program.generateStopConstant();
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
    String _generateErrorConstant_2 = this.program.generateErrorConstant();
    _builder_3.append(_generateErrorConstant_2);
    _builder_3.append(")");
    return _builder_3.toString();
  }
  
  private String generateSignedInteger(final SignedInteger sint) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isISig = sint.isISig();
      if (_isISig) {
        _builder.append("-");
      }
    }
    int _value = sint.getValue();
    _builder.append(_value);
    return _builder.toString();
  }
}
