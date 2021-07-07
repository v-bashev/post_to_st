package su.nsk.iae.post.generator.xml.common.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class SimpleVarHelper extends VarHelper {
  public SimpleVarHelper() {
    this.varType = "localVars";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof VarDeclaration)) {
      this.parseSimpleVar(((VarDeclaration)varDecl).getVars(), ((VarDeclaration)varDecl).isConst());
    }
  }
}
