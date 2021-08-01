package su.nsk.iae.post.generator.plcopen.xml;

import java.util.function.Consumer;
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator;
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
    this.object = program;
    this.programName = program.getName();
    this.type = "PROGRAM";
    final Consumer<InputVarDeclaration> _function = (InputVarDeclaration v) -> {
      this.inVarList.add(v);
    };
    program.getProgInVars().stream().forEach(_function);
    final Consumer<OutputVarDeclaration> _function_1 = (OutputVarDeclaration v) -> {
      this.outVarList.add(v);
    };
    program.getProgOutVars().stream().forEach(_function_1);
    final Consumer<InputOutputVarDeclaration> _function_2 = (InputOutputVarDeclaration v) -> {
      this.inOutVarList.add(v);
    };
    program.getProgInOutVars().stream().forEach(_function_2);
    final Consumer<ExternalVarDeclaration> _function_3 = (ExternalVarDeclaration v) -> {
      this.externalVarList.add(v);
    };
    program.getProgExternVars().stream().forEach(_function_3);
    final Consumer<VarDeclaration> _function_4 = (VarDeclaration v) -> {
      this.varList.add(v);
    };
    program.getProgVars().stream().forEach(_function_4);
    final Consumer<TempVarDeclaration> _function_5 = (TempVarDeclaration v) -> {
      this.tempVarList.add(v);
    };
    program.getProgTempVars().stream().forEach(_function_5);
    this.parseProcesses(program.getProcesses());
  }
}
