package su.nsk.iae.post.generator.plcopen.xml.configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.poST.Configuration;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.Resource;

@SuppressWarnings("all")
public class ConfigurationGenerator {
  private Configuration configuration;
  
  private VarHelper confVarList = new GlobalVarHelper();
  
  private List<ResourceGenerator> resources = new LinkedList<ResourceGenerator>();
  
  public ConfigurationGenerator(final Configuration configuration) {
    this.configuration = configuration;
    final Consumer<GlobalVarDeclaration> _function = (GlobalVarDeclaration v) -> {
      this.confVarList.add(v);
    };
    configuration.getConfGlobVars().stream().forEach(_function);
    final Consumer<Resource> _function_1 = (Resource r) -> {
      ResourceGenerator _resourceGenerator = new ResourceGenerator(r);
      this.resources.add(_resourceGenerator);
    };
    configuration.getResources().stream().forEach(_function_1);
  }
  
  public String generateConfiguration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CONFIGURATION ");
    String _name = this.configuration.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _generateVars = GeneratorUtil.generateVars(this.confVarList);
    _builder.append(_generateVars, "\t");
    _builder.newLineIfNotEmpty();
    {
      for(final ResourceGenerator r : this.resources) {
        _builder.append("\t");
        CharSequence _generateResource = r.generateResource();
        _builder.append(_generateResource, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.append("END_CONFIGURATION");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  public EList<Resource> getResources() {
    return this.configuration.getResources();
  }
}
