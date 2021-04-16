package su.nsk.iae.post.generator.xml.common.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.xml.common.vars.VarHelper;
import su.nsk.iae.post.poST.InputVarDeclaration;

@SuppressWarnings("all")
public class InputVarHelper extends VarHelper {
  public InputVarHelper() {
    this.varType = "inputVars";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof InputVarDeclaration)) {
      this.parseSimpleVar(((InputVarDeclaration)varDecl).getVars(), false);
    }
  }
}
