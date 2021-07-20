package su.nsk.iae.post.generator.st;

import org.eclipse.emf.common.util.EList;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.poST.ExternalVarDeclaration;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;
import su.nsk.iae.post.poST.InputVarDeclaration;
import su.nsk.iae.post.poST.OutputVarDeclaration;
import su.nsk.iae.post.poST.Program;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class ProgramPOUGenerator extends ProgramGenerator {
  public ProgramPOUGenerator(final Program program) {
    this.programName = program.getName();
    this.type = "PROGRAM";
    EList<InputVarDeclaration> _progInVars = program.getProgInVars();
    for (final InputVarDeclaration v : _progInVars) {
      this.inVarList.add(v);
    }
    EList<OutputVarDeclaration> _progOutVars = program.getProgOutVars();
    for (final OutputVarDeclaration v_1 : _progOutVars) {
      this.outVarList.add(v_1);
    }
    EList<InputOutputVarDeclaration> _progInOutVars = program.getProgInOutVars();
    for (final InputOutputVarDeclaration v_2 : _progInOutVars) {
      this.inOutVarList.add(v_2);
    }
    EList<ExternalVarDeclaration> _progExternVars = program.getProgExternVars();
    for (final ExternalVarDeclaration v_3 : _progExternVars) {
      this.externalVarList.add(v_3);
    }
    EList<VarDeclaration> _progVars = program.getProgVars();
    for (final VarDeclaration v_4 : _progVars) {
      this.varList.add(v_4);
    }
    EList<TempVarDeclaration> _progTempVars = program.getProgTempVars();
    for (final TempVarDeclaration v_5 : _progTempVars) {
      this.tempVarList.add(v_5);
    }
    this.parseProcesses(program.getProcesses());
  }
}
