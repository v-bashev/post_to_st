package su.nsk.iae.post.generator.plcopen.xml.configuration;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.poST.Task;

@SuppressWarnings("all")
public class TaskGenerator {
  private Task task;
  
  public TaskGenerator(final Task task) {
    this.task = task;
  }
  
  public String generateTask() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<task name=\"");
    String _name = this.task.getName();
    _builder.append(_name);
    _builder.append("\" interval=\"PT0.01S\" priority=\"");
    String _priority = this.task.getInit().getPriority();
    _builder.append(_priority);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
}
