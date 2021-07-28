package su.nsk.iae.post.generator.st.configuration;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.st.common.util.GeneratorUtil;
import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.Task;
import su.nsk.iae.post.poST.TaskInitialization;

@SuppressWarnings("all")
public class TaskGenerator {
  private Task task;
  
  public TaskGenerator(final Task task) {
    this.task = task;
  }
  
  public String generateTask() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("TASK ");
    String _name = this.task.getName();
    _builder.append(_name);
    _builder.append(" (");
    String _generateFirstArg = this.generateFirstArg();
    _builder.append(_generateFirstArg);
    _builder.append(", PRIORITY := ");
    String _priority = this.task.getInit().getPriority();
    _builder.append(_priority);
    _builder.append(");");
    return _builder.toString();
  }
  
  private String generateFirstArg() {
    final TaskInitialization init = this.task.getInit();
    Constant _interval = init.getInterval();
    boolean _tripleNotEquals = (_interval != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("INTERVAL := ");
      String _generateConstant = GeneratorUtil.generateConstant(init.getInterval());
      _builder.append(_generateConstant);
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("SINGLE := ");
    String _generateConstant_1 = GeneratorUtil.generateConstant(init.getSingle());
    _builder_1.append(_generateConstant_1);
    return _builder_1.toString();
  }
}
