package su.nsk.iae.post.generator.st;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import su.nsk.iae.post.generator.IPoSTGenerator;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.generator.st.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.st.common.vars.VarHelper;
import su.nsk.iae.post.generator.st.configuration.ConfigurationGenerator;
import su.nsk.iae.post.poST.Configuration;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.Program;
import su.nsk.iae.post.poST.ProgramConfElement;
import su.nsk.iae.post.poST.ProgramConfiguration;
import su.nsk.iae.post.poST.Resource;
import su.nsk.iae.post.poST.TemplateProcessConfElement;

@SuppressWarnings("all")
public class STGenerator implements IPoSTGenerator {
  private ConfigurationGenerator configuration = null;
  
  private VarHelper globVarList = new GlobalVarHelper();
  
  private List<ProgramGenerator> programs = new LinkedList<ProgramGenerator>();
  
  @Override
  public void setModel(final Model model) {
    this.globVarList.clear();
    this.programs.clear();
    final Consumer<GlobalVarDeclaration> _function = (GlobalVarDeclaration v) -> {
      this.globVarList.add(v);
    };
    model.getGlobVars().stream().forEach(_function);
    Configuration _conf = model.getConf();
    boolean _tripleNotEquals = (_conf != null);
    if (_tripleNotEquals) {
      Configuration _conf_1 = model.getConf();
      ConfigurationGenerator _configurationGenerator = new ConfigurationGenerator(_conf_1);
      this.configuration = _configurationGenerator;
      final Function<Resource, EList<ProgramConfiguration>> _function_1 = (Resource res) -> {
        return res.getResStatement().getProgramConfs();
      };
      final Function<EList<ProgramConfiguration>, Stream<ProgramConfiguration>> _function_2 = (EList<ProgramConfiguration> res) -> {
        return res.stream();
      };
      final Consumer<ProgramConfiguration> _function_3 = (ProgramConfiguration programConf) -> {
        final Program program = EcoreUtil.<Program>copy(programConf.getProgram());
        program.setName(programConf.getName());
        ProgramPOUGenerator _programPOUGenerator = new ProgramPOUGenerator(program);
        this.programs.add(_programPOUGenerator);
      };
      this.configuration.getResources().stream().<EList<ProgramConfiguration>>map(_function_1).<ProgramConfiguration>flatMap(_function_2).forEach(_function_3);
    } else {
      final Consumer<Program> _function_4 = (Program p) -> {
        ProgramPOUGenerator _programPOUGenerator = new ProgramPOUGenerator(p);
        this.programs.add(_programPOUGenerator);
      };
      model.getPrograms().stream().forEach(_function_4);
      final Consumer<FunctionBlock> _function_5 = (FunctionBlock fb) -> {
        FunctionBlockPOUGenerator _functionBlockPOUGenerator = new FunctionBlockPOUGenerator(fb);
        this.programs.add(_functionBlockPOUGenerator);
      };
      model.getFbs().stream().forEach(_function_5);
    }
  }
  
  @Override
  public void beforeGenerate(final org.eclipse.emf.ecore.resource.Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.preparePrograms();
  }
  
  @Override
  public void doGenerate(final org.eclipse.emf.ecore.resource.Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.generateSingleFile(fsa, "");
  }
  
  @Override
  public void afterGenerate(final org.eclipse.emf.ecore.resource.Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
  }
  
  private void generateSingleFile(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    _builder.append("poST_code.st");
    fsa.generateFile(_builder.toString(), this.generateSingleFileBody());
  }
  
  private String generateSingleFileBody() {
    StringConcatenation _builder = new StringConcatenation();
    String _generateVars = GeneratorUtil.generateVars(this.globVarList);
    _builder.append(_generateVars);
    _builder.newLineIfNotEmpty();
    String _generateConfiguration = this.configuration.generateConfiguration();
    _builder.append(_generateConfiguration);
    _builder.newLineIfNotEmpty();
    {
      for(final ProgramGenerator c : this.programs) {
        String _generateProgram = c.generateProgram();
        _builder.append(_generateProgram);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  private void preparePrograms() {
    if ((this.configuration == null)) {
      return;
    }
    final Function<Resource, EList<ProgramConfiguration>> _function = (Resource res) -> {
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
            String _name_1 = programConf.getName();
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
