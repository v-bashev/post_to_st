package su.nsk.iae.post.generator.xml.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.xml.vars.VarHelper;
import su.nsk.iae.post.poST.TempVarDeclaration;

@SuppressWarnings("all")
public class TempVarHelper extends VarHelper {
  public TempVarHelper() {
    this.varType = "VAR_TEMP";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof TempVarDeclaration)) {
      this.parseSimpleVar(((TempVarDeclaration)varDecl).getVars());
    }
  }
}
