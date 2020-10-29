package su.nsk.iae.post.generator.st;

import org.eclipse.emf.common.util.EList;
import su.nsk.iae.post.generator.st.CodeGenerator;
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
    EList<InputVarDeclaration> _progInVars = fb.getProgInVars();
    for (final InputVarDeclaration v : _progInVars) {
      this.inVarList.add(v);
    }
    EList<OutputVarDeclaration> _progOutVars = fb.getProgOutVars();
    for (final OutputVarDeclaration v_1 : _progOutVars) {
      this.outVarList.add(v_1);
    }
    EList<InputOutputVarDeclaration> _progInOutVars = fb.getProgInOutVars();
    for (final InputOutputVarDeclaration v_2 : _progInOutVars) {
      this.inOutVarList.add(v_2);
    }
    EList<ExternalVarDeclaration> _progExternVars = fb.getProgExternVars();
    for (final ExternalVarDeclaration v_3 : _progExternVars) {
      this.externalVarList.add(v_3);
    }
    EList<VarDeclaration> _progVars = fb.getProgVars();
    for (final VarDeclaration v_4 : _progVars) {
      this.varList.add(v_4);
    }
    EList<TempVarDeclaration> _progTempVars = fb.getProgTempVars();
    for (final TempVarDeclaration v_5 : _progTempVars) {
      this.tempVarList.add(v_5);
    }
    this.parseProcesses(fb.getProcesses());
  }
}
