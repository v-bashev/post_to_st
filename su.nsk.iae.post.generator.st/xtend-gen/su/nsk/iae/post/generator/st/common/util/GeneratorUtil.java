package su.nsk.iae.post.generator.st.common.util;

import com.google.common.base.Objects;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.st.common.ProcessGenerator;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;
import su.nsk.iae.post.poST.AddExpression;
import su.nsk.iae.post.poST.AddOperator;
import su.nsk.iae.post.poST.AndExpression;
import su.nsk.iae.post.poST.ArrayVariable;
import su.nsk.iae.post.poST.AssignmentType;
import su.nsk.iae.post.poST.CompExpression;
import su.nsk.iae.post.poST.CompOperator;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.EquExpression;
import su.nsk.iae.post.poST.EquOperator;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.FunctionCall;
import su.nsk.iae.post.poST.IntegerLiteral;
import su.nsk.iae.post.poST.MulExpression;
import su.nsk.iae.post.poST.MulOperator;
import su.nsk.iae.post.poST.NumericLiteral;
import su.nsk.iae.post.poST.ParamAssignment;
import su.nsk.iae.post.poST.ParamAssignmentElements;
import su.nsk.iae.post.poST.PowerExpression;
import su.nsk.iae.post.poST.PrimaryExpression;
import su.nsk.iae.post.poST.ProcessStatusExpression;
import su.nsk.iae.post.poST.RealLiteral;
import su.nsk.iae.post.poST.SignedInteger;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.TimeLiteral;
import su.nsk.iae.post.poST.UnaryExpression;
import su.nsk.iae.post.poST.UnaryOperator;
import su.nsk.iae.post.poST.XorExpression;

@SuppressWarnings("all")
public class GeneratorUtil {
  public static String generateStopConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_STOP");
    return _builder.toString();
  }
  
  public static String generateErrorConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_ERROR");
    return _builder.toString();
  }
  
  public static String generateGlobalTime() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_global_time");
    return _builder.toString();
  }
  
  public static String generateVarName(final String process, final String variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_p_");
    _builder.append(process);
    _builder.append("_v_");
    _builder.append(variable);
    return _builder.toString();
  }
  
  public static String generateVarName(final ProcessGenerator process, final String variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_p_");
    String _name = process.getName();
    _builder.append(_name);
    _builder.append("_v_");
    _builder.append(variable);
    return _builder.toString();
  }
  
  public static String generateTimeoutName(final ProcessGenerator process) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_g_p_");
    String _name = process.getName();
    _builder.append(_name);
    _builder.append("_time");
    return _builder.toString();
  }
  
  public static String generateEnumName(final su.nsk.iae.post.poST.Process process) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_g_p_");
    String _name = process.getName();
    _builder.append(_name);
    _builder.append("_state");
    return _builder.toString();
  }
  
  public static String generateEnumName(final ProcessGenerator process) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_g_p_");
    String _name = process.getName();
    _builder.append(_name);
    _builder.append("_state");
    return _builder.toString();
  }
  
  public static String generateEnumStateConstant(final ProcessGenerator process, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_P_");
    String _upperCase = process.getName().toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_S_");
    String _upperCase_1 = name.toUpperCase();
    _builder.append(_upperCase_1);
    return _builder.toString();
  }
  
  public static String generateConstant(final Constant constant) {
    NumericLiteral _num = constant.getNum();
    boolean _tripleNotEquals = (_num != null);
    if (_tripleNotEquals) {
      final NumericLiteral num = constant.getNum();
      if ((num instanceof IntegerLiteral)) {
        StringConcatenation _builder = new StringConcatenation();
        {
          String _type = ((IntegerLiteral)num).getType();
          boolean _tripleNotEquals_1 = (_type != null);
          if (_tripleNotEquals_1) {
            String _type_1 = ((IntegerLiteral)num).getType();
            _builder.append(_type_1);
            _builder.append("#");
          }
        }
        String _generateSignedInteger = GeneratorUtil.generateSignedInteger(((IntegerLiteral)num).getValue());
        _builder.append(_generateSignedInteger);
        return _builder.toString();
      }
      final RealLiteral dNum = ((RealLiteral) num);
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        String _type_2 = dNum.getType();
        boolean _tripleNotEquals_2 = (_type_2 != null);
        if (_tripleNotEquals_2) {
          String _type_3 = dNum.getType();
          _builder_1.append(_type_3);
          _builder_1.append("#");
        }
      }
      {
        boolean _isRSig = dNum.isRSig();
        if (_isRSig) {
          _builder_1.append("-");
        }
      }
      String _value = dNum.getValue();
      _builder_1.append(_value);
      return _builder_1.toString();
    }
    TimeLiteral _time = constant.getTime();
    boolean _tripleNotEquals_3 = (_time != null);
    if (_tripleNotEquals_3) {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("T#");
      String _interval = constant.getTime().getInterval();
      _builder_2.append(_interval);
      return _builder_2.toString();
    }
    return constant.getOth();
  }
  
  public static String generateSignedInteger(final SignedInteger sint) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isISig = sint.isISig();
      if (_isISig) {
        _builder.append("-");
      }
    }
    String _value = sint.getValue();
    _builder.append(_value);
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
                    String _generateSingleDeclaration = GeneratorUtil.generateSingleDeclaration(v);
                    _builder.append(_generateSingleDeclaration, "\t");
                    String _generateValue = GeneratorUtil.generateValue(v);
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
                    String _generateSingleDeclaration_1 = GeneratorUtil.generateSingleDeclaration(v_1);
                    _builder.append(_generateSingleDeclaration_1, "\t");
                    String _generateValue_1 = GeneratorUtil.generateValue(v_1);
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
      String _join = IterableExtensions.join(v.getArraValues(), ", ");
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
  
  public static String generateExpression(final Expression exp) {
    return GeneratorUtil.generateExpression(exp, null, null, null);
  }
  
  public static String generateExpression(final Expression exp, final Function<SymbolicVariable, String> gVar, final Function<ArrayVariable, String> gArray, final Function<ProcessStatusExpression, String> gPStatus) {
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
          if ((gVar != null)) {
            return gVar.apply(((PrimaryExpression)exp).getVariable());
          }
          return ((PrimaryExpression)exp).getVariable().getName();
        } else {
          ArrayVariable _array = ((PrimaryExpression)exp).getArray();
          boolean _tripleNotEquals_2 = (_array != null);
          if (_tripleNotEquals_2) {
            if ((gArray != null)) {
              return gArray.apply(((PrimaryExpression)exp).getArray());
            }
            StringConcatenation _builder = new StringConcatenation();
            String _name = ((PrimaryExpression)exp).getArray().getVariable().getName();
            _builder.append(_name);
            _builder.append("[");
            String _generateExpression = GeneratorUtil.generateExpression(((PrimaryExpression)exp).getArray().getIndex(), gVar, gArray, gPStatus);
            _builder.append(_generateExpression);
            _builder.append("]");
            return _builder.toString();
          } else {
            ProcessStatusExpression _procStatus = ((PrimaryExpression)exp).getProcStatus();
            boolean _tripleNotEquals_3 = (_procStatus != null);
            if (_tripleNotEquals_3) {
              if ((gPStatus != null)) {
                return gPStatus.apply(((PrimaryExpression)exp).getProcStatus());
              }
              StringConcatenation _builder_1 = new StringConcatenation();
              return _builder_1.toString();
            } else {
              FunctionCall _funCall = ((PrimaryExpression)exp).getFunCall();
              boolean _tripleNotEquals_4 = (_funCall != null);
              if (_tripleNotEquals_4) {
                StringConcatenation _builder_2 = new StringConcatenation();
                String _name_1 = ((PrimaryExpression)exp).getFunCall().getFunction().getName();
                _builder_2.append(_name_1);
                _builder_2.append("(");
                final Function<Expression, String> _function = (Expression x) -> {
                  return GeneratorUtil.generateExpression(x, gVar, gArray, gPStatus);
                };
                String _generateParamAssignmentElements = GeneratorUtil.generateParamAssignmentElements(((PrimaryExpression)exp).getFunCall().getArgs(), _function);
                _builder_2.append(_generateParamAssignmentElements);
                _builder_2.append(")");
                return _builder_2.toString();
              } else {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("(");
                String _generateExpression_1 = GeneratorUtil.generateExpression(((PrimaryExpression)exp).getNestExpr(), gVar, gArray, gPStatus);
                _builder_3.append(_generateExpression_1);
                _builder_3.append(")");
                return _builder_3.toString();
              }
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
        String _generateExpression = GeneratorUtil.generateExpression(((UnaryExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof PowerExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((PowerExpression)exp).getLeft(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        _builder.append(" ** ");
        String _generateExpression_1 = GeneratorUtil.generateExpression(((PowerExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof MulExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((MulExpression)exp).getLeft(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        _builder.append(" ");
        String _generateMulOperators = GeneratorUtil.generateMulOperators(((MulExpression)exp).getMulOp());
        _builder.append(_generateMulOperators);
        _builder.append(" ");
        String _generateExpression_1 = GeneratorUtil.generateExpression(((MulExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof AddExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((AddExpression)exp).getLeft(), gVar, gArray, gPStatus);
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
        String _generateExpression_1 = GeneratorUtil.generateExpression(((AddExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof EquExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((EquExpression)exp).getLeft(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        _builder.append(" ");
        String _generateEquOperators = GeneratorUtil.generateEquOperators(((EquExpression)exp).getEquOp());
        _builder.append(_generateEquOperators);
        _builder.append(" ");
        String _generateExpression_1 = GeneratorUtil.generateExpression(((EquExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof CompExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((CompExpression)exp).getLeft(), gVar, gArray, gPStatus);
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
        String _generateExpression_1 = GeneratorUtil.generateExpression(((CompExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof AndExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((AndExpression)exp).getLeft(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        _builder.append(" AND ");
        String _generateExpression_1 = GeneratorUtil.generateExpression(((AndExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof XorExpression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(((XorExpression)exp).getLeft(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        _builder.append(" XOR ");
        String _generateExpression_1 = GeneratorUtil.generateExpression(((XorExpression)exp).getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    if (!_matched) {
      if (exp instanceof Expression) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _generateExpression = GeneratorUtil.generateExpression(exp.getLeft(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression);
        _builder.append(" OR ");
        String _generateExpression_1 = GeneratorUtil.generateExpression(exp.getRight(), gVar, gArray, gPStatus);
        _builder.append(_generateExpression_1);
        return _builder.toString();
      }
    }
    return null;
  }
  
  public static String generateParamAssignmentElements(final ParamAssignmentElements elements) {
    final Function<Expression, String> _function = (Expression x) -> {
      return GeneratorUtil.generateExpression(x);
    };
    return GeneratorUtil.generateParamAssignmentElements(elements, _function);
  }
  
  public static String generateParamAssignmentElements(final ParamAssignmentElements elements, final Function<Expression, String> gExp) {
    final Function<ParamAssignment, String> _function = (ParamAssignment x) -> {
      return GeneratorUtil.generateParamAssignment(x, gExp);
    };
    return IterableExtensions.join(elements.getElements().stream().<String>map(_function).collect(Collectors.<String>toList()), ", ");
  }
  
  private static String generateParamAssignment(final ParamAssignment ele, final Function<Expression, String> gExp) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = ele.getVariable().getName();
    _builder.append(_name);
    _builder.append(" ");
    {
      AssignmentType _assig = ele.getAssig();
      boolean _equals = Objects.equal(_assig, AssignmentType.IN);
      if (_equals) {
        _builder.append(":=");
      } else {
        _builder.append("=>");
      }
    }
    _builder.append(" ");
    String _apply = gExp.apply(ele.getValue());
    _builder.append(_apply);
    return _builder.toString();
  }
  
  private static String generateEquOperators(final EquOperator op) {
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
  
  private static String generateMulOperators(final MulOperator op) {
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
}
