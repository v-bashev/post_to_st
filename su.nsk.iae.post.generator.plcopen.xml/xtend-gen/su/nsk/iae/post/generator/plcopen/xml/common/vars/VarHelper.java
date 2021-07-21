package su.nsk.iae.post.generator.plcopen.xml.common.vars;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData;
import su.nsk.iae.post.poST.ArrayInitialization;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.SignedInteger;
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
  
  public String getType() {
    return this.varType;
  }
  
  public List<VarData> getList() {
    return this.listDecl;
  }
  
  public void clear() {
    this.listDecl.clear();
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
  
  public boolean hasConstant() {
    for (final VarData v : this.listDecl) {
      boolean _isConstant = v.isConstant();
      if (_isConstant) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hasNonConstant() {
    for (final VarData v : this.listDecl) {
      boolean _isConstant = v.isConstant();
      boolean _not = (!_isConstant);
      if (_not) {
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
          value = GeneratorUtil.generateConstant(v.getSpec().getValue());
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
        SignedInteger _start = v.getArrSpec().getInit().getStart();
        _builder.append(_start);
        _builder.append("..");
        SignedInteger _end = v.getArrSpec().getInit().getEnd();
        _builder.append(_end);
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
            values.add(GeneratorUtil.generateConstant(e_1));
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
}
