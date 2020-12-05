package su.nsk.iae.post.generator.xml.vars;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.xml.vars.VarHelper;
import su.nsk.iae.post.generator.xml.vars.data.VarData;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.GlobalVarInitDeclaration;
import su.nsk.iae.post.poST.SymbolicVariable;

@SuppressWarnings("all")
public class GlobalVarHelper extends VarHelper {
  public GlobalVarHelper() {
    this.varType = "VAR_GLOBAL";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof GlobalVarDeclaration)) {
      this.parseDirectVars(((GlobalVarDeclaration)varDecl).getVarsAs());
      this.parseSimpleVar(((GlobalVarDeclaration)varDecl).getVarsSimple(), ((GlobalVarDeclaration)varDecl).isConst());
    }
  }
  
  private void parseDirectVars(final EList<GlobalVarInitDeclaration> varList) {
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
