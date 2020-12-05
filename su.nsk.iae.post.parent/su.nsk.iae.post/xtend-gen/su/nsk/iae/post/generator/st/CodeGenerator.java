package su.nsk.iae.post.generator.st;

import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import su.nsk.iae.post.generator.st.ICodeGenerator;
import su.nsk.iae.post.generator.st.common.ProcessGenerator;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;

@SuppressWarnings("all")
public class CodeGenerator extends ICodeGenerator {
  public void generate(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    String _lowerCase = this.codeName.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".st");
    fsa.generateFile(_builder.toString(), this.generateCode());
  }
  
  @Override
  protected String generateCode() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.type);
    _builder.append(" ");
    _builder.append(this.codeName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateVar = this.generateVar(this.inVarList);
    _builder.append(_generateVar);
    _builder.newLineIfNotEmpty();
    String _generateVar_1 = this.generateVar(this.outVarList);
    _builder.append(_generateVar_1);
    _builder.newLineIfNotEmpty();
    String _generateVar_2 = this.generateVar(this.inOutVarList);
    _builder.append(_generateVar_2);
    _builder.newLineIfNotEmpty();
    String _generateVar_3 = this.generateVar(this.externalVarList);
    _builder.append(_generateVar_3);
    _builder.newLineIfNotEmpty();
    String _generateVar_4 = this.generateVar(this.varList);
    _builder.append(_generateVar_4);
    _builder.newLineIfNotEmpty();
    String _generateVar_5 = this.generateVar(this.tempVarList);
    _builder.append(_generateVar_5);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateGlobalTime = this.generateGlobalTime();
    _builder.append(_generateGlobalTime);
    _builder.append(" := TIME();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      for(final ProcessGenerator p : this.processList) {
        String _generateBody = p.generateBody();
        _builder.append(_generateBody);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("END_");
    _builder.append(this.type);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String generateVar(final VarHelper helper) {
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
                    String _generateSingleDeclaration = this.generateSingleDeclaration(v);
                    _builder.append(_generateSingleDeclaration, "\t");
                    String _generateValue = this.generateValue(v);
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
                    String _generateSingleDeclaration_1 = this.generateSingleDeclaration(v_1);
                    _builder.append(_generateSingleDeclaration_1, "\t");
                    String _generateValue_1 = this.generateValue(v_1);
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
  
  private String generateSingleDeclaration(final VarData data) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = data.getName();
    _builder.append(_name);
    _builder.append(" : ");
    String _type = data.getType();
    _builder.append(_type);
    return _builder.toString();
  }
  
  private String generateValue(final VarData v) {
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
