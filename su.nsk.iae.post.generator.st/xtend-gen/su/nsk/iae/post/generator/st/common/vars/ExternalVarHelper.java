package su.nsk.iae.post.generator.st.common.vars;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;
import su.nsk.iae.post.poST.ExternalVarDeclaration;
import su.nsk.iae.post.poST.ExternalVarInitDeclaration;
import su.nsk.iae.post.poST.SymbolicVariable;

@SuppressWarnings("all")
public class ExternalVarHelper extends VarHelper {
  public ExternalVarHelper() {
    this.varType = "VAR_EXTERNAL";
  }
  
  @Override
  public void add(final EObject varDecl, final String pref) {
    if ((varDecl instanceof ExternalVarDeclaration)) {
      this.parseExternVar(((ExternalVarDeclaration)varDecl).getVars(), pref, ((ExternalVarDeclaration)varDecl).isConst());
    }
  }
  
  private void parseExternVar(final EList<ExternalVarInitDeclaration> varList, final String pref, final boolean isConst) {
    for (final ExternalVarInitDeclaration v : varList) {
      {
        final String type = v.getType();
        EList<SymbolicVariable> _vars = v.getVarList().getVars();
        for (final SymbolicVariable e : _vars) {
          String _name = e.getName();
          String _plus = (pref + _name);
          VarData _varData = new VarData(_plus, type, null, isConst);
          this.listDecl.add(_varData);
        }
      }
    }
  }
}
