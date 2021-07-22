package su.nsk.iae.post.generator.plcopen.xml.configuration;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.poST.AssignmentType;
import su.nsk.iae.post.poST.AttachVariableConfElement;
import su.nsk.iae.post.poST.ProgramConfElement;
import su.nsk.iae.post.poST.ProgramConfElements;
import su.nsk.iae.post.poST.ProgramConfiguration;
import su.nsk.iae.post.poST.Task;
import su.nsk.iae.post.poST.TemplateProcessConfElement;

@SuppressWarnings("all")
public class ProgramConfigurationGenerator {
  private ProgramConfiguration programConf;
  
  public ProgramConfigurationGenerator(final ProgramConfiguration programConfiguration) {
    this.programConf = programConfiguration;
  }
  
  public String generateProgramConfiguration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("PROGRAM ");
    String _name = this.programConf.getName();
    _builder.append(_name);
    _builder.append(" ");
    String _generateTask = this.generateTask();
    _builder.append(_generateTask);
    String _generateArgs = this.generateArgs();
    _builder.append(_generateArgs);
    _builder.append(";");
    return _builder.toString();
  }
  
  private String generateTask() {
    Task _task = this.programConf.getTask();
    boolean _tripleNotEquals = (_task != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("WITH ");
      String _name = this.programConf.getTask().getName();
      _builder.append(_name);
      _builder.append(" ");
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    return _builder_1.toString();
  }
  
  private String generateArgs() {
    ProgramConfElements _args = this.programConf.getArgs();
    boolean _tripleEquals = (_args == null);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      return _builder.toString();
    }
    final ArrayList<String> res = new ArrayList<String>();
    final Consumer<ProgramConfElement> _function = (ProgramConfElement e) -> {
      if ((e instanceof AttachVariableConfElement)) {
        String _name = ((AttachVariableConfElement)e).getProgramVar().getName();
        String _generateAssignmentSign = this.generateAssignmentSign(((AttachVariableConfElement)e).getAssig());
        String _plus = (_name + _generateAssignmentSign);
        String _name_1 = ((AttachVariableConfElement)e).getAttVar().getName();
        final String attach = (_plus + _name_1);
        res.add(attach);
      } else {
        final TemplateProcessConfElement processConf = ((TemplateProcessConfElement) e);
        final Consumer<AttachVariableConfElement> _function_1 = (AttachVariableConfElement p) -> {
          String _generateProcessVar = this.generateProcessVar(processConf.getProcess(), p.getProgramVar().getName());
          String _generateAssignmentSign_1 = this.generateAssignmentSign(p.getAssig());
          String _plus_1 = (_generateProcessVar + _generateAssignmentSign_1);
          String _name_2 = p.getAttVar().getName();
          final String attach_1 = (_plus_1 + _name_2);
          res.add(attach_1);
        };
        processConf.getArgs().getElements().stream().forEach(_function_1);
      }
    };
    this.programConf.getArgs().getElements().stream().forEach(_function);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(");
    String _join = IterableExtensions.join(res, ", ");
    _builder_1.append(_join);
    _builder_1.append(")");
    return _builder_1.toString();
  }
  
  private String generateAssignmentSign(final AssignmentType assig) {
    boolean _equals = Objects.equal(assig, AssignmentType.IN);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(" ");
      _builder.append(":= ");
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(" ");
    _builder_1.append("=> ");
    return _builder_1.toString();
  }
  
  private String generateProcessVar(final su.nsk.iae.post.poST.Process process, final String varName) {
    return GeneratorUtil.generateVarName(process, varName);
  }
}
