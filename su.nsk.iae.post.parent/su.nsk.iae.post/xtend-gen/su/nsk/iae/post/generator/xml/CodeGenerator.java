package su.nsk.iae.post.generator.xml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import su.nsk.iae.post.generator.xml.ICodeGenerator;
import su.nsk.iae.post.generator.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.xml.common.vars.VarHelper;
import su.nsk.iae.post.generator.xml.common.vars.data.VarData;

@SuppressWarnings("all")
public class CodeGenerator extends ICodeGenerator {
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    String _lowerCase = this.codeName.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".xml");
    fsa.generateFile(_builder.toString(), this.generateCode());
  }
  
  public static String generateXMLStart() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    _builder.newLine();
    _builder.append("<project xmlns=\"http://www.plcopen.org/xml/tc6_0200\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<fileHeader companyName=\"\" productName=\"CODESYS\" productVersion=\"CODESYS V3.5 SP11\" creationDateTime=\"");
    String _generateCurrentTime = CodeGenerator.generateCurrentTime();
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
    return CodeGenerator.generateXMLEnd(null);
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
        String _generateVar = CodeGenerator.generateVar(globalVars, "GVL");
        _builder.append(_generateVar, "\t\t\t");
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
  
  public String generateXMLBody() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t\t\t");
    _builder.append("<pou name=\"");
    _builder.append(this.codeName, "\t\t\t");
    _builder.append("\" pouType=\"");
    String _lowerCase = this.type.toLowerCase();
    _builder.append(_lowerCase, "\t\t\t");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("<interface>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    String _generateVar = CodeGenerator.generateVar(this.varList);
    _builder.append(_generateVar, "\t\t\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("</interface>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<body>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<ST>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<xhtml xmlns=\"http://www.w3.org/1999/xhtml\">");
    String _generateGlobalTime = this.generateGlobalTime();
    _builder.append(_generateGlobalTime, "\t\t\t\t\t\t");
    _builder.append(" := TIME();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      for(final ProcessGenerator p : this.processList) {
        String _generateBody = p.generateBody();
        _builder.append(_generateBody);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("</xhtml>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("</ST>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</body>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</pou>");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  public String generateCode() {
    StringConcatenation _builder = new StringConcatenation();
    String _generateXMLStart = CodeGenerator.generateXMLStart();
    _builder.append(_generateXMLStart);
    _builder.newLineIfNotEmpty();
    String _generateXMLBody = this.generateXMLBody();
    _builder.append(_generateXMLBody);
    _builder.newLineIfNotEmpty();
    String _generateXMLEnd = CodeGenerator.generateXMLEnd();
    _builder.append(_generateXMLEnd);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private static String generateCurrentTime() {
    return new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSSSSS").format(Calendar.getInstance().getTime());
  }
  
  public static String generateVar(final VarHelper varHelper) {
    return CodeGenerator.generateVar(varHelper, null);
  }
  
  public static String generateVar(final VarHelper varHelper, final String name) {
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
                    String _generateSingleDeclaration = CodeGenerator.generateSingleDeclaration(v);
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
                    String _generateSingleDeclaration_1 = CodeGenerator.generateSingleDeclaration(v_1);
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
}
