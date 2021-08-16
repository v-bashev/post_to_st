package su.nsk.iae.post.generator.plcopen.xml.common.statement;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator;
import su.nsk.iae.post.poST.FBInvocation;
import su.nsk.iae.post.poST.Statement;

@SuppressWarnings("all")
public class FBInvocationGenerator extends IStatementGenerator {
  public FBInvocationGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof FBInvocation);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final FBInvocation s = ((FBInvocation) statement);
    StringConcatenation _builder = new StringConcatenation();
    String _name = s.getFb().getName();
    _builder.append(_name);
    _builder.append("(");
    String _generateParamAssignmentElements = this.context.generateParamAssignmentElements(s.getArgs());
    _builder.append(_generateParamAssignmentElements);
    _builder.append(")");
    return _builder.toString();
  }
}
