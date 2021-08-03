package su.nsk.iae.post.generator.plcopen.xml.common.vars;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData;
import su.nsk.iae.post.poST.ArrayInitialization;
import su.nsk.iae.post.poST.ArrayInterval;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.SimpleSpecificationInit;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.VarInitDeclaration;

@SuppressWarnings("all")
public abstract class VarHelper {
  protected String varType;
  
  protected List<VarData> listDecl = new LinkedList<VarData>();
  
  public void add(final EObject varDecl) {
    this.add(varDecl, "");
  }
  
  public abstract void add(final EObject varDecl, final String pref);
  
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
    final Predicate<VarData> _function = (VarData v) -> {
      String _name = v.getName();
      return Objects.equal(_name, name);
    };
    return this.listDecl.stream().anyMatch(_function);
  }
  
  public boolean hasConstant() {
    final Predicate<VarData> _function = (VarData v) -> {
      return v.isConstant();
    };
    return this.listDecl.stream().anyMatch(_function);
  }
  
  public boolean hasNonConstant() {
    final Predicate<VarData> _function = (VarData v) -> {
      boolean _isConstant = v.isConstant();
      return (!_isConstant);
    };
    return this.listDecl.stream().anyMatch(_function);
  }
  
  protected void parseSimpleVar(final EList<VarInitDeclaration> varList, final String pref) {
    this.parseSimpleVar(varList, pref, false);
  }
  
  protected void parseSimpleVar(final EList<VarInitDeclaration> varList, final String pref, final boolean isConst) {
    for (final VarInitDeclaration v : varList) {
      SimpleSpecificationInit _spec = v.getSpec();
      boolean _tripleNotEquals = (_spec != null);
      if (_tripleNotEquals) {
        final String type = v.getSpec().getType();
        String value = null;
        Expression _value = v.getSpec().getValue();
        boolean _tripleNotEquals_1 = (_value != null);
        if (_tripleNotEquals_1) {
          value = GeneratorUtil.generateExpression(v.getSpec().getValue());
        }
        EList<SymbolicVariable> _vars = v.getVarList().getVars();
        for (final SymbolicVariable e : _vars) {
          String _name = e.getName();
          String _plus = (pref + _name);
          VarData _varData = new VarData(_plus, type, value, isConst);
          this.listDecl.add(_varData);
        }
      } else {
        final String type_1 = v.getArrSpec().getInit().getType();
        String start = null;
        String end = null;
        ArrayInterval _interval = v.getArrSpec().getInit().getInterval();
        boolean _tripleNotEquals_2 = (_interval != null);
        if (_tripleNotEquals_2) {
          start = GeneratorUtil.generateExpression(v.getArrSpec().getInit().getInterval().getStart());
          end = GeneratorUtil.generateExpression(v.getArrSpec().getInit().getInterval().getEnd());
        }
        List<String> values = null;
        ArrayInitialization _values = v.getArrSpec().getValues();
        boolean _tripleNotEquals_3 = (_values != null);
        if (_tripleNotEquals_3) {
          LinkedList<String> _linkedList = new LinkedList<String>();
          values = _linkedList;
          EList<Expression> _elements = v.getArrSpec().getValues().getElements();
          for (final Expression e_1 : _elements) {
            values.add(GeneratorUtil.generateExpression(e_1));
          }
        }
        EList<SymbolicVariable> _vars_1 = v.getVarList().getVars();
        for (final SymbolicVariable e_2 : _vars_1) {
          String _name_1 = e_2.getName();
          String _plus_1 = (pref + _name_1);
          VarData _varData_1 = new VarData(_plus_1, type_1, start, end, isConst, values);
          this.listDecl.add(_varData_1);
        }
      }
    }
  }
}
