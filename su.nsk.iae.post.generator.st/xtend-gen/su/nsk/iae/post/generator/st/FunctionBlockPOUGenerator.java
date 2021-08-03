package su.nsk.iae.post.generator.st;

import java.util.function.Consumer;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.poST.ExternalVarDeclaration;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;
import su.nsk.iae.post.poST.InputVarDeclaration;
import su.nsk.iae.post.poST.OutputVarDeclaration;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.VarDeclaration;

@SuppressWarnings("all")
public class FunctionBlockPOUGenerator extends ProgramGenerator {
  public FunctionBlockPOUGenerator(final FunctionBlock fb) {
    this.object = fb;
    this.programName = fb.getName();
    this.type = "FUNCTION_BLOCK";
    final Consumer<InputVarDeclaration> _function = (InputVarDeclaration v) -> {
      this.inVarList.add(v);
    };
    fb.getFbInVars().stream().forEach(_function);
    final Consumer<OutputVarDeclaration> _function_1 = (OutputVarDeclaration v) -> {
      this.outVarList.add(v);
    };
    fb.getFbOutVars().stream().forEach(_function_1);
    final Consumer<InputOutputVarDeclaration> _function_2 = (InputOutputVarDeclaration v) -> {
      this.inOutVarList.add(v);
    };
    fb.getFbInOutVars().stream().forEach(_function_2);
    final Consumer<ExternalVarDeclaration> _function_3 = (ExternalVarDeclaration v) -> {
      this.externalVarList.add(v);
    };
    fb.getFbExternVars().stream().forEach(_function_3);
    final Consumer<VarDeclaration> _function_4 = (VarDeclaration v) -> {
      this.varList.add(v);
    };
    fb.getFbVars().stream().forEach(_function_4);
    final Consumer<TempVarDeclaration> _function_5 = (TempVarDeclaration v) -> {
      this.tempVarList.add(v);
    };
    fb.getFbTempVars().stream().forEach(_function_5);
    this.parseProcesses(fb.getProcesses());
  }
}
