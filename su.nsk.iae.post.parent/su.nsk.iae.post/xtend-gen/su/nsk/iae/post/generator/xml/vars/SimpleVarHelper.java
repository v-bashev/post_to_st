package su.nsk.iae.post.generator.xml.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.xml.vars.VarHelper;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class SimpleVarHelper extends VarHelper {
  public SimpleVarHelper() {
    this.varType = "VAR";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof VarDeclaration)) {
      this.parseSimpleVar(((VarDeclaration)varDecl).getVars(), ((VarDeclaration)varDecl).isConst());
    }
  }
}
