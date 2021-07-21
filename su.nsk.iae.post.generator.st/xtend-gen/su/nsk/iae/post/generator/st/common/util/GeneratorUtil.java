package su.nsk.iae.post.generator.st.common.util;

import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.IntegerLiteral;
import su.nsk.iae.post.poST.NumericLiteral;
import su.nsk.iae.post.poST.RealLiteral;
import su.nsk.iae.post.poST.SignedInteger;
import su.nsk.iae.post.poST.TimeLiteral;

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
