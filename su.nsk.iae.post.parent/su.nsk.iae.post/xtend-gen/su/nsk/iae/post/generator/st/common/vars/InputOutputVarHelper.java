package su.nsk.iae.post.generator.st.common.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;

@SuppressWarnings("all")
public class InputOutputVarHelper extends VarHelper {
  public InputOutputVarHelper() {
    this.varType = "VAR_IN_OUT";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof InputOutputVarDeclaration)) {
      this.parseSimpleVar(((InputOutputVarDeclaration)varDecl).getVars());
    }
  }
}
