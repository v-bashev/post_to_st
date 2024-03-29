package su.nsk.iae.post.generator.plcopen.xml.configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import su.nsk.iae.post.generator.plcopen.xml.XMLGenerator;
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
  
  public ConfigurationGenerator(final Configuration configuration, final XMLGenerator xmlGenerator) {
    this.configuration = configuration;
    final Consumer<GlobalVarDeclaration> _function = (GlobalVarDeclaration v) -> {
      this.confVarList.add(v);
      xmlGenerator.addGlobalVar(v);
    };
    configuration.getConfGlobVars().stream().forEach(_function);
    final Consumer<Resource> _function_1 = (Resource r) -> {
      ResourceGenerator _resourceGenerator = new ResourceGenerator(r, xmlGenerator);
      this.resources.add(_resourceGenerator);
    };
    configuration.getResources().stream().forEach(_function_1);
  }
  
  public EList<Resource> getResources() {
    return this.configuration.getResources();
  }
}
