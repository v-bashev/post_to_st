package su.nsk.iae.post.generator.plcopen.xml.common.vars;

import org.eclipse.emf.ecore.EObject;
import su.nsk.iae.post.poST.OutputVarDeclaration;

@SuppressWarnings("all")
public class OutputVarHelper extends VarHelper {
  public OutputVarHelper() {
    this.varType = "outputVars";
  }
  
  @Override
  public void add(final EObject varDecl) {
    if ((varDecl instanceof OutputVarDeclaration)) {
      this.parseSimpleVar(((OutputVarDeclaration)varDecl).getVars());
    }
  }
}
