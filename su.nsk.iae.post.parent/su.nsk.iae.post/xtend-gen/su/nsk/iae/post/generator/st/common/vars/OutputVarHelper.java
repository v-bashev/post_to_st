package su.nsk.iae.post.generator.st.common.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.poST.OutputVarDeclaration;

@SuppressWarnings("all")
public class OutputVarHelper extends VarHelper {
  public OutputVarHelper() {
    this.varType = "VAR_OUTPUT";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof OutputVarDeclaration)) {
      this.parseSimpleVar(((OutputVarDeclaration)varDecl).getVars());
    }
  }
}
