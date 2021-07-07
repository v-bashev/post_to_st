package su.nsk.iae.post.generator.xml;

import org.eclipse.emf.common.util.EList;
import su.nsk.iae.post.poST.ExternalVarDeclaration;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;
import su.nsk.iae.post.poST.InputVarDeclaration;
import su.nsk.iae.post.poST.OutputVarDeclaration;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class FBGenerator extends CodeGenerator {
  public FBGenerator(final FunctionBlock fb) {
    this.codeName = fb.getName();
    this.type = "FUNCTION_BLOCK";
    EList<InputVarDeclaration> _fbInVars = fb.getFbInVars();
    for (final InputVarDeclaration v : _fbInVars) {
      this.inVarList.add(v);
    }
    EList<OutputVarDeclaration> _fbOutVars = fb.getFbOutVars();
    for (final OutputVarDeclaration v_1 : _fbOutVars) {
      this.outVarList.add(v_1);
    }
    EList<InputOutputVarDeclaration> _fbInOutVars = fb.getFbInOutVars();
    for (final InputOutputVarDeclaration v_2 : _fbInOutVars) {
      this.inOutVarList.add(v_2);
    }
    EList<ExternalVarDeclaration> _fbExternVars = fb.getFbExternVars();
    for (final ExternalVarDeclaration v_3 : _fbExternVars) {
      this.externalVarList.add(v_3);
    }
    EList<VarDeclaration> _fbVars = fb.getFbVars();
    for (final VarDeclaration v_4 : _fbVars) {
      this.varList.add(v_4);
    }
    EList<TempVarDeclaration> _fbTempVars = fb.getFbTempVars();
    for (final TempVarDeclaration v_5 : _fbTempVars) {
      this.tempVarList.add(v_5);
    }
    this.parseProcesses(fb.getProcesses());
  }
}
