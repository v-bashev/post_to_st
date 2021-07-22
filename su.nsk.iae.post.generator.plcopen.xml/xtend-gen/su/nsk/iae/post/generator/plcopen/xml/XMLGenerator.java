package su.nsk.iae.post.generator.plcopen.xml;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import su.nsk.iae.post.generator.IPoSTGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.generator.plcopen.xml.configuration.ConfigurationGenerator;
import su.nsk.iae.post.poST.Configuration;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;
import su.nsk.iae.post.poST.ProgramConfElement;
import su.nsk.iae.post.poST.ProgramConfiguration;
import su.nsk.iae.post.poST.TemplateProcessConfElement;

@SuppressWarnings("all")
public class XMLGenerator implements IPoSTGenerator {
  private ConfigurationGenerator configuration;
  
  private VarHelper globVarList = new GlobalVarHelper();
  
  private List<ProgramGenerator> programs = new LinkedList<ProgramGenerator>();
  
  @Override
  public void setModel(final Model model) {
    Configuration _conf = model.getConf();
    ConfigurationGenerator _configurationGenerator = new ConfigurationGenerator(_conf);
    this.configuration = _configurationGenerator;
    this.globVarList.clear();
    this.programs.clear();
    final Consumer<GlobalVarDeclaration> _function = (GlobalVarDeclaration v) -> {
      this.globVarList.add(v);
    };
    model.getGlobVars().stream().forEach(_function);
    final Consumer<Program> _function_1 = (Program p) -> {
      ProgramPOUGenerator _programPOUGenerator = new ProgramPOUGenerator(p);
      this.programs.add(_programPOUGenerator);
    };
    model.getPrograms().stream().forEach(_function_1);
    final Consumer<FunctionBlock> _function_2 = (FunctionBlock fb) -> {
      FunctionBlockPOUGenerator _functionBlockPOUGenerator = new FunctionBlockPOUGenerator(fb);
      this.programs.add(_functionBlockPOUGenerator);
    };
    model.getFbs().stream().forEach(_function_2);
  }
  
  @Override
  public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
  }
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.generateSingleFile(fsa, "");
  }
  
  @Override
  public void afterGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
  }
  
  private void generateSingleFile(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    _builder.append("poST_code.xml");
    fsa.generateFile(_builder.toString(), this.generateXML());
  }
  
  private String generateXML() {
    StringConcatenation _builder = new StringConcatenation();
    String _generateXMLStart = GeneratorUtil.generateXMLStart();
    _builder.append(_generateXMLStart);
    _builder.newLineIfNotEmpty();
    {
      for(final ProgramGenerator c : this.programs) {
        String _generateProgram = c.generateProgram();
        _builder.append(_generateProgram);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      boolean _isEmpty = this.globVarList.getList().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        String _generateXMLEnd = GeneratorUtil.generateXMLEnd(this.globVarList);
        _builder.append(_generateXMLEnd);
        _builder.newLineIfNotEmpty();
      } else {
        String _generateXMLEnd_1 = GeneratorUtil.generateXMLEnd();
        _builder.append(_generateXMLEnd_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  private void preparePrograms() {
    final Function<su.nsk.iae.post.poST.Resource, EList<ProgramConfiguration>> _function = (su.nsk.iae.post.poST.Resource res) -> {
      return res.getResStatement().getProgramConfs();
    };
    final Function<EList<ProgramConfiguration>, Stream<ProgramConfiguration>> _function_1 = (EList<ProgramConfiguration> res) -> {
      return res.stream();
    };
    final Consumer<ProgramConfiguration> _function_2 = (ProgramConfiguration programConf) -> {
      final Consumer<ProgramConfElement> _function_3 = (ProgramConfElement processConf) -> {
        if ((processConf instanceof TemplateProcessConfElement)) {
          final Predicate<ProgramGenerator> _function_4 = (ProgramGenerator x) -> {
            String _name = x.getName();
            String _name_1 = programConf.getProgram().getName();
            return Objects.equal(_name, _name_1);
          };
          final ProgramGenerator programGen = this.programs.stream().filter(_function_4).findFirst().get();
          final su.nsk.iae.post.poST.Process process = EcoreUtil.<su.nsk.iae.post.poST.Process>copy(((TemplateProcessConfElement)processConf).getProcess());
          process.setName(((TemplateProcessConfElement)processConf).getName());
          programGen.addProcess(process);
        }
      };
      programConf.getArgs().getElements().stream().forEach(_function_3);
    };
    this.configuration.getResources().stream().<EList<ProgramConfiguration>>map(_function).<ProgramConfiguration>flatMap(_function_1).forEach(_function_2);
  }
}
