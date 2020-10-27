package su.nsk.iae.post.generator.st.vars;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import su.nsk.iae.post.generator.st.vars.data.VarData;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.VarInitDeclaration;

@SuppressWarnings("all")
public abstract class VarHelper {
  protected String varType;
  
  protected List<VarData> listDecl = new LinkedList<VarData>();
  
  public abstract void add(final EObject varDecl);
  
  public void add(final String name, final String type) {
    this.add(name, type, null);
  }
  
  public void add(final String name, final String type, final String value) {
    this.add(name, type, value, false);
  }
  
  public void add(final String name, final String type, final String value, final boolean isConstant) {
    VarData _varData = new VarData(name, type, value, isConstant);
    this.listDecl.add(_varData);
  }
  
  public String generate() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.listDecl.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          boolean _hasConstant = this.hasConstant();
          if (_hasConstant) {
            _builder.append(this.varType);
            _builder.append(" CONSTANT");
            _builder.newLineIfNotEmpty();
            {
              for(final VarData v : this.listDecl) {
                {
                  boolean _isConstant = v.isConstant();
                  if (_isConstant) {
                    _builder.append("\t");
                    String _generateSingleDeclaration = this.generateSingleDeclaration(v);
                    _builder.append(_generateSingleDeclaration, "\t");
                    _builder.append(" := ");
                    String _value = v.getValue();
                    _builder.append(_value, "\t");
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
          boolean _hasNonConstant = this.hasNonConstant();
          if (_hasNonConstant) {
            _builder.append(this.varType);
            _builder.newLineIfNotEmpty();
            {
              for(final VarData v_1 : this.listDecl) {
                {
                  boolean _isConstant_1 = v_1.isConstant();
                  boolean _not_1 = (!_isConstant_1);
                  if (_not_1) {
                    _builder.append("\t");
                    String _generateSingleDeclaration_1 = this.generateSingleDeclaration(v_1);
                    _builder.append(_generateSingleDeclaration_1, "\t");
                    {
                      String _value_1 = v_1.getValue();
                      boolean _tripleNotEquals = (_value_1 != null);
                      if (_tripleNotEquals) {
                        _builder.append(" := ");
                        String _value_2 = v_1.getValue();
                        _builder.append(_value_2, "\t");
                      }
                    }
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
  
  public List<VarData> getList() {
    return this.listDecl;
  }
  
  public boolean contains(final String name) {
    for (final VarData v : this.listDecl) {
      String _name = v.getName();
      boolean _equals = Objects.equal(_name, name);
      if (_equals) {
        return true;
      }
    }
    return false;
  }
  
  protected void parseSimpleVar(final EList<VarInitDeclaration> varList) {
    this.parseSimpleVar(varList, false);
  }
  
  protected void parseSimpleVar(final EList<VarInitDeclaration> varList, final boolean isConst) {
    for (final VarInitDeclaration v : varList) {
      {
        final String type = v.getSpec().getType();
        String value = null;
        Constant _value = v.getSpec().getValue();
        boolean _tripleNotEquals = (_value != null);
        if (_tripleNotEquals) {
          value = NodeModelUtils.getNode(v.getSpec().getValue()).getText().trim();
        }
        EList<SymbolicVariable> _vars = v.getVarList().getVars();
        for (final SymbolicVariable e : _vars) {
          String _name = e.getName();
          VarData _varData = new VarData(_name, type, value, isConst);
          this.listDecl.add(_varData);
        }
      }
    }
  }
  
  protected String generateSingleDeclaration(final VarData data) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = data.getName();
    _builder.append(_name);
    _builder.append(" : ");
    String _type = data.getType();
    _builder.append(_type);
    return _builder.toString();
  }
  
  private boolean hasConstant() {
    for (final VarData v : this.listDecl) {
      boolean _isConstant = v.isConstant();
      if (_isConstant) {
        return true;
      }
    }
    return false;
  }
  
  private boolean hasNonConstant() {
    for (final VarData v : this.listDecl) {
      boolean _isConstant = v.isConstant();
      boolean _not = (!_isConstant);
      if (_not) {
        return true;
      }
    }
    return false;
  }
}
