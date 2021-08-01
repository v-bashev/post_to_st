package su.nsk.iae.post.generator.plcopen.xml.common.util;

import com.google.common.base.Objects;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData;
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
import su.nsk.iae.post.poST.IntegerLiteral;
import su.nsk.iae.post.poST.MulExpression;
import su.nsk.iae.post.poST.MulOperator;
import su.nsk.iae.post.poST.NumericLiteral;
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
  
  public static String generateVars(final VarHelper varHelper) {
    return GeneratorUtil.generateVars(varHelper, null);
  }
  
  public static String generateVars(final VarHelper varHelper, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = varHelper.getList().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          boolean _hasConstant = varHelper.hasConstant();
          if (_hasConstant) {
            _builder.append("<");
            String _type = varHelper.getType();
            _builder.append(_type);
            {
              if ((name != null)) {
                _builder.append(" name=\"");
                _builder.append(name);
                _builder.append("\"");
              }
            }
            _builder.append(" constant=\"true\">");
            _builder.newLineIfNotEmpty();
            {
              List<VarData> _list = varHelper.getList();
              for(final VarData v : _list) {
                {
                  boolean _isConstant = v.isConstant();
                  if (_isConstant) {
                    _builder.append("\t");
                    String _generateSingleDeclaration = GeneratorUtil.generateSingleDeclaration(v);
                    _builder.append(_generateSingleDeclaration, "\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("</");
            String _type_1 = varHelper.getType();
            _builder.append(_type_1);
            _builder.append(">");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _hasNonConstant = varHelper.hasNonConstant();
          if (_hasNonConstant) {
            _builder.append("<");
            String _type_2 = varHelper.getType();
            _builder.append(_type_2);
            {
              if ((name != null)) {
                _builder.append(" name=\"");
                _builder.append(name);
                _builder.append("\"");
              }
            }
            _builder.append(">");
            _builder.newLineIfNotEmpty();
            {
              List<VarData> _list_1 = varHelper.getList();
              for(final VarData v_1 : _list_1) {
                {
                  boolean _isConstant_1 = v_1.isConstant();
                  boolean _not_1 = (!_isConstant_1);
                  if (_not_1) {
                    _builder.append("\t");
                    String _generateSingleDeclaration_1 = GeneratorUtil.generateSingleDeclaration(v_1);
                    _builder.append(_generateSingleDeclaration_1, "\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("</");
            String _type_3 = varHelper.getType();
            _builder.append(_type_3);
            _builder.append(">");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  private static String generateSingleDeclaration(final VarData data) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<variable name=\"");
    String _name = data.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<type>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<");
    String _type = data.getType();
    _builder.append(_type, "\t\t");
    _builder.append(" />");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</type>");
    _builder.newLine();
    {
      String _value = data.getValue();
      boolean _tripleNotEquals = (_value != null);
      if (_tripleNotEquals) {
        _builder.append("\t");
        _builder.append("<initialValue>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<simpleValue value=\"");
        String _value_1 = data.getValue();
        _builder.append(_value_1, "\t\t");
        _builder.append("\" />");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</initialValue>");
        _builder.newLine();
      }
    }
    _builder.append("</variable>");
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String generateXMLStart() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    _builder.newLine();
    _builder.append("<project xmlns=\"http://www.plcopen.org/xml/tc6_0200\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<fileHeader companyName=\"\" productName=\"CODESYS\" productVersion=\"CODESYS V3.5 SP11\" creationDateTime=\"");
    String _generateCurrentTime = GeneratorUtil.generateCurrentTime();
    _builder.append(_generateCurrentTime, "\t");
    _builder.append("\" />");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<contentHeader name=\"poST.project\">");
    _builder.newLine();
    _builder.append("\t\t ");
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
    return _builder.toString();
  }
  
  public static String generateXMLEnd() {
    return GeneratorUtil.generateXMLEndWithGlobalVars(null);
  }
  
  public static String generateXMLEndWithGlobalVars(final VarHelper globalVars) {
    StringConcatenation _builder = new StringConcatenation();
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
    {
      if ((globalVars != null)) {
        _builder.append("\t");
        _builder.append("<addData>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("<data name=\"http://www.3s-software.com/plcopenxml/globalvars\" handleUnknown=\"implementation\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        String _generateVars = GeneratorUtil.generateVars(globalVars, "GVL");
        _builder.append(_generateVars, "\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("</data>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</addData>");
        _builder.newLine();
      }
    }
    _builder.append("</project>");
    _builder.newLine();
    return _builder.toString();
  }
  
  private static String generateCurrentTime() {
    return new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSSSSS").format(Calendar.getInstance().getTime());
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
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("(");
              String _generateExpression_1 = GeneratorUtil.generateExpression(((PrimaryExpression)exp).getNestExpr(), gVar, gArray, gPStatus);
              _builder_2.append(_generateExpression_1);
              _builder_2.append(")");
              return _builder_2.toString();
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
