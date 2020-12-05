package su.nsk.iae.post.generator.xml.vars;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import su.nsk.iae.post.generator.xml.vars.data.VarData;
import su.nsk.iae.post.poST.ArrayInitialization;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.SimpleSpecificationInit;
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
            _builder.append("<localVars constant=\"true\">");
            _builder.newLine();
            {
              for(final VarData v : this.listDecl) {
                {
                  boolean _isConstant = v.isConstant();
                  if (_isConstant) {
                    _builder.append("\t");
                    String _generateSingleDeclaration = this.generateSingleDeclaration(v);
                    _builder.append(_generateSingleDeclaration, "\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("</localVars>");
            _builder.newLine();
          }
        }
        {
          boolean _hasNonConstant = this.hasNonConstant();
          if (_hasNonConstant) {
            _builder.append("<localVars>");
            _builder.newLine();
            {
              for(final VarData v_1 : this.listDecl) {
                {
                  boolean _isConstant_1 = v_1.isConstant();
                  boolean _not_1 = (!_isConstant_1);
                  if (_not_1) {
                    _builder.append("\t");
                    String _generateSingleDeclaration_1 = this.generateSingleDeclaration(v_1);
                    _builder.append(_generateSingleDeclaration_1, "\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder.append("</localVars>");
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
      SimpleSpecificationInit _spec = v.getSpec();
      boolean _tripleNotEquals = (_spec != null);
      if (_tripleNotEquals) {
        final String type = v.getSpec().getType();
        String value = null;
        Constant _value = v.getSpec().getValue();
        boolean _tripleNotEquals_1 = (_value != null);
        if (_tripleNotEquals_1) {
          value = NodeModelUtils.getNode(v.getSpec().getValue()).getText().trim();
        }
        EList<SymbolicVariable> _vars = v.getVarList().getVars();
        for (final SymbolicVariable e : _vars) {
          String _name = e.getName();
          VarData _varData = new VarData(_name, type, value, isConst);
          this.listDecl.add(_varData);
        }
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("ARRAY [");
        String _trim = NodeModelUtils.getNode(v.getArrSpec().getInit().getStart()).getText().trim();
        _builder.append(_trim);
        _builder.append("..");
        String _trim_1 = NodeModelUtils.getNode(v.getArrSpec().getInit().getEnd()).getText().trim();
        _builder.append(_trim_1);
        _builder.append("] OF ");
        String _type = v.getArrSpec().getInit().getType();
        _builder.append(_type);
        final String type_1 = _builder.toString();
        List<String> values = null;
        ArrayInitialization _values = v.getArrSpec().getValues();
        boolean _tripleNotEquals_2 = (_values != null);
        if (_tripleNotEquals_2) {
          LinkedList<String> _linkedList = new LinkedList<String>();
          values = _linkedList;
          EList<Constant> _elements = v.getArrSpec().getValues().getElements();
          for (final Constant e_1 : _elements) {
            values.add(NodeModelUtils.getNode(e_1).getText().trim());
          }
        }
        EList<SymbolicVariable> _vars_1 = v.getVarList().getVars();
        for (final SymbolicVariable e_2 : _vars_1) {
          String _name_1 = e_2.getName();
          VarData _varData_1 = new VarData(_name_1, type_1, isConst, values);
          this.listDecl.add(_varData_1);
        }
      }
    }
  }
  
  protected String generateSingleDeclaration(final VarData data) {
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
