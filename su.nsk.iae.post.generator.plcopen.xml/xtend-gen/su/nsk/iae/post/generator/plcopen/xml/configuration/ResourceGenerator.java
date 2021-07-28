package su.nsk.iae.post.generator.plcopen.xml.configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.ProgramConfiguration;
import su.nsk.iae.post.poST.Resource;
import su.nsk.iae.post.poST.Task;

@SuppressWarnings("all")
public class ResourceGenerator {
  private Resource resource;
  
  private VarHelper resVarList = new GlobalVarHelper();
  
  private List<TaskGenerator> tasks = new LinkedList<TaskGenerator>();
  
  private List<ProgramConfigurationGenerator> programConfigurationGenerators = new LinkedList<ProgramConfigurationGenerator>();
  
  public ResourceGenerator(final Resource resource) {
    this.resource = resource;
    final Consumer<GlobalVarDeclaration> _function = (GlobalVarDeclaration v) -> {
      this.resVarList.add(v);
    };
    resource.getResGlobVars().stream().forEach(_function);
    final Consumer<Task> _function_1 = (Task t) -> {
      TaskGenerator _taskGenerator = new TaskGenerator(t);
      this.tasks.add(_taskGenerator);
    };
    resource.getResStatement().getTasks().stream().forEach(_function_1);
    final Consumer<ProgramConfiguration> _function_2 = (ProgramConfiguration p) -> {
      ProgramConfigurationGenerator _programConfigurationGenerator = new ProgramConfigurationGenerator(p);
      this.programConfigurationGenerators.add(_programConfigurationGenerator);
    };
    resource.getResStatement().getProgramConfs().stream().forEach(_function_2);
  }
  
  public CharSequence generateResource() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<resource name=\"");
    String _name = this.resource.getName();
    _builder.append(_name);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    {
      for(final TaskGenerator t : this.tasks) {
        _builder.append("\t");
        String _generateTask = t.generateTask();
        _builder.append(_generateTask, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      for(final ProgramConfigurationGenerator p : this.programConfigurationGenerators) {
        _builder.append("\t");
        String _generateProgramConfiguration = p.generateProgramConfiguration();
        _builder.append(_generateProgramConfiguration, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</resource>");
    _builder.newLine();
    return _builder;
  }
}
