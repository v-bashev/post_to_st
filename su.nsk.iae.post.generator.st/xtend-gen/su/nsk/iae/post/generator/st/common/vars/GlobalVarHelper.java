package su.nsk.iae.post.generator.st.common.vars;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.st.common.vars.data.VarData;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.GlobalVarInitDeclaration;
import su.nsk.iae.post.poST.SymbolicVariable;

@SuppressWarnings("all")
public class GlobalVarHelper extends VarHelper {
  public GlobalVarHelper() {
    this.varType = "VAR_GLOBAL";
  }
  
  @Override
  public void add(final EObject varDecl, final String pref) {
    if ((varDecl instanceof GlobalVarDeclaration)) {
      this.parseDirectVars(((GlobalVarDeclaration)varDecl).getVarsAs(), pref);
      this.parseSimpleVar(((GlobalVarDeclaration)varDecl).getVarsSimple(), pref, ((GlobalVarDeclaration)varDecl).isConst());
    }
  }
  
  private void parseDirectVars(final EList<GlobalVarInitDeclaration> varList, final String pref) {
    for (final GlobalVarInitDeclaration v : varList) {
      {
        final String type = v.getType();
        EList<SymbolicVariable> _vars = v.getVarList().getVars();
        for (final SymbolicVariable e : _vars) {
          String _name = e.getName();
          VarData _varData = new VarData(_name, type, null, false);
          this.listDecl.add(_varData);
        }
      }
    }
  }
}
