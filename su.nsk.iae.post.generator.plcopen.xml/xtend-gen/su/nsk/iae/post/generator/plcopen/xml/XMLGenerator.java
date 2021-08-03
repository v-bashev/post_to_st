package su.nsk.iae.post.generator.plcopen.xml;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import su.nsk.iae.post.generator.IPoSTGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper;
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper;
import su.nsk.iae.post.generator.plcopen.xml.configuration.ConfigurationGenerator;
import su.nsk.iae.post.poST.ArrayVariable;
import su.nsk.iae.post.poST.AssignmentStatement;
import su.nsk.iae.post.poST.AttachVariableConfElement;
import su.nsk.iae.post.poST.Configuration;
import su.nsk.iae.post.poST.ForStatement;
import su.nsk.iae.post.poST.FunctionBlock;
import su.nsk.iae.post.poST.GlobalVarDeclaration;
import su.nsk.iae.post.poST.Model;
import su.nsk.iae.post.poST.PrimaryExpression;
import su.nsk.iae.post.poST.ProcessStatements;
import su.nsk.iae.post.poST.Program;
import su.nsk.iae.post.poST.ProgramConfElement;
import su.nsk.iae.post.poST.ProgramConfiguration;
import su.nsk.iae.post.poST.Resource;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.TemplateProcessAttachVariableConfElement;
import su.nsk.iae.post.poST.TemplateProcessConfElement;
import su.nsk.iae.post.poST.TimeoutStatement;
import su.nsk.iae.post.poST.Variable;

@SuppressWarnings("all")
public class XMLGenerator implements IPoSTGenerator {
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
      ConfigurationGenerator _configurationGenerator = new ConfigurationGenerator(_conf_1, this);
      this.configuration = _configurationGenerator;
      final Function<Resource, EList<ProgramConfiguration>> _function_1 = (Resource res) -> {
        return res.getResStatement().getProgramConfs();
      };
      final Function<EList<ProgramConfiguration>, Stream<ProgramConfiguration>> _function_2 = (EList<ProgramConfiguration> res) -> {
        return res.stream();
      };
      final Consumer<ProgramConfiguration> _function_3 = (ProgramConfiguration programConf) -> {
        final Program program = EcoreUtil.<Program>copy(programConf.getProgram());
        program.setName(this.capitalizeFirst(programConf.getName()));
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
  
  public void addGlobalVar(final EObject varDecl) {
    this.globVarList.add(varDecl);
  }
  
  private void generateSingleFile(final IFileSystemAccess2 fsa, final String path) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(path);
    _builder.append("poST_code.xml");
    fsa.generateFile(_builder.toString(), this.generateSingleXMLFile());
  }
  
  private String generateSingleXMLFile() {
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
        String _generateXMLEndWithGlobalVars = GeneratorUtil.generateXMLEndWithGlobalVars(this.globVarList);
        _builder.append(_generateXMLEndWithGlobalVars);
        _builder.newLineIfNotEmpty();
      } else {
        String _generateXMLEnd = GeneratorUtil.generateXMLEnd();
        _builder.append(_generateXMLEnd);
        _builder.newLineIfNotEmpty();
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
      final String programConfName = this.capitalizeFirst(programConf.getName());
      final Predicate<ProgramGenerator> _function_3 = (ProgramGenerator x) -> {
        String _name = x.getName();
        return Objects.equal(_name, programConfName);
      };
      final ProgramGenerator programGen = this.programs.stream().filter(_function_3).findFirst().get();
      final Consumer<ProgramConfElement> _function_4 = (ProgramConfElement confElement) -> {
        if ((confElement instanceof TemplateProcessConfElement)) {
          final su.nsk.iae.post.poST.Process process = EcoreUtil.<su.nsk.iae.post.poST.Process>copy(((TemplateProcessConfElement)confElement).getProcess());
          process.setName(this.capitalizeFirst(((TemplateProcessConfElement)confElement).getName()));
          final Consumer<TemplateProcessAttachVariableConfElement> _function_5 = (TemplateProcessAttachVariableConfElement e) -> {
            this.changeAllVars(e, process);
          };
          ((TemplateProcessConfElement)confElement).getArgs().getElements().stream().forEach(_function_5);
          programGen.addProcess(process);
        } else {
          if ((confElement instanceof AttachVariableConfElement)) {
            this.changeAllVars(((AttachVariableConfElement)confElement), programGen.getEObject());
          }
        }
      };
      programConf.getArgs().getElements().stream().forEach(_function_4);
    };
    this.configuration.getResources().stream().<EList<ProgramConfiguration>>map(_function).<ProgramConfiguration>flatMap(_function_1).forEach(_function_2);
  }
  
  public void changeAllVars(final AttachVariableConfElement element, final EObject root) {
    this.changeAllVars(element.getProgramVar(), element.getAttVar(), root);
  }
  
  public void changeAllVars(final TemplateProcessAttachVariableConfElement element, final EObject root) {
    this.changeAllVars(element.getProgramVar(), element.getAttVar(), root);
  }
  
  public void changeAllVars(final Variable programVar, final Variable attVar, final EObject root) {
    final Predicate<PrimaryExpression> _function = (PrimaryExpression v) -> {
      return ((v.getVariable() != null) && Objects.equal(v.getVariable().getName(), programVar.getName()));
    };
    final Consumer<PrimaryExpression> _function_1 = (PrimaryExpression v) -> {
      v.setVariable(EcoreUtil.<SymbolicVariable>copy(((SymbolicVariable) attVar)));
    };
    EcoreUtil2.<PrimaryExpression>getAllContentsOfType(root, PrimaryExpression.class).stream().filter(_function).forEach(_function_1);
    final Predicate<AssignmentStatement> _function_2 = (AssignmentStatement v) -> {
      return ((v.getVariable() != null) && Objects.equal(v.getVariable().getName(), programVar.getName()));
    };
    final Consumer<AssignmentStatement> _function_3 = (AssignmentStatement v) -> {
      v.setVariable(EcoreUtil.<SymbolicVariable>copy(((SymbolicVariable) attVar)));
    };
    EcoreUtil2.<AssignmentStatement>getAllContentsOfType(root, AssignmentStatement.class).stream().filter(_function_2).forEach(_function_3);
    final Predicate<ForStatement> _function_4 = (ForStatement v) -> {
      String _name = v.getVariable().getName();
      String _name_1 = programVar.getName();
      return Objects.equal(_name, _name_1);
    };
    final Consumer<ForStatement> _function_5 = (ForStatement v) -> {
      v.setVariable(EcoreUtil.<SymbolicVariable>copy(((SymbolicVariable) attVar)));
    };
    EcoreUtil2.<ForStatement>getAllContentsOfType(root, ForStatement.class).stream().filter(_function_4).forEach(_function_5);
    final Predicate<ArrayVariable> _function_6 = (ArrayVariable v) -> {
      String _name = v.getVariable().getName();
      String _name_1 = programVar.getName();
      return Objects.equal(_name, _name_1);
    };
    final Consumer<ArrayVariable> _function_7 = (ArrayVariable v) -> {
      v.setVariable(EcoreUtil.<SymbolicVariable>copy(((SymbolicVariable) attVar)));
    };
    EcoreUtil2.<ArrayVariable>getAllContentsOfType(root, ArrayVariable.class).stream().filter(_function_6).forEach(_function_7);
    final Predicate<TimeoutStatement> _function_8 = (TimeoutStatement v) -> {
      return ((v.getVariable() != null) && Objects.equal(v.getVariable().getName(), programVar.getName()));
    };
    final Consumer<TimeoutStatement> _function_9 = (TimeoutStatement v) -> {
      v.setVariable(EcoreUtil.<SymbolicVariable>copy(((SymbolicVariable) attVar)));
    };
    EcoreUtil2.<TimeoutStatement>getAllContentsOfType(root, TimeoutStatement.class).stream().filter(_function_8).forEach(_function_9);
    final Predicate<ProcessStatements> _function_10 = (ProcessStatements v) -> {
      return ((v.getProcess() != null) && Objects.equal(v.getProcess().getName(), programVar.getName()));
    };
    final Consumer<ProcessStatements> _function_11 = (ProcessStatements v) -> {
      Variable _process = v.getProcess();
      _process.setName(this.capitalizeFirst(v.getProcess().getName()));
    };
    EcoreUtil2.<ProcessStatements>getAllContentsOfType(root, ProcessStatements.class).stream().filter(_function_10).forEach(_function_11);
  }
  
  private String capitalizeFirst(final String str) {
    String _upperCase = str.substring(0, 1).toUpperCase();
    String _substring = str.substring(1);
    return (_upperCase + _substring);
  }
}
