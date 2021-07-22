package su.nsk.iae.post.generator.plcopen.xml.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData;
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
  
  public static String generateVarName(final su.nsk.iae.post.poST.Process process, final String variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_p_");
    String _name = process.getName();
    _builder.append(_name);
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
    return GeneratorUtil.generateXMLEnd(null);
  }
  
  public static String generateXMLEnd(final VarHelper globalVars) {
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
}
