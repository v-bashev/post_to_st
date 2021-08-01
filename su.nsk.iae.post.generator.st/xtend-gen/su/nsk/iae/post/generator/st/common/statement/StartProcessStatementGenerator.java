package su.nsk.iae.post.generator.st.common.statement;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.st.common.ProcessGenerator;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.generator.st.common.StateGenerator;
import su.nsk.iae.post.generator.st.common.StatementListGenerator;
import su.nsk.iae.post.poST.StartProcessStatement;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.Variable;

@SuppressWarnings("all")
public class StartProcessStatementGenerator extends IStatementGenerator {
  public StartProcessStatementGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof StartProcessStatement);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final StartProcessStatement s = ((StartProcessStatement) statement);
    StringConcatenation _builder = new StringConcatenation();
    {
      Variable _process = s.getProcess();
      boolean _tripleNotEquals = (_process != null);
      if (_tripleNotEquals) {
        String _generateProcessStart = this.program.generateProcessStart(s.getProcess().getName());
        _builder.append(_generateProcessStart);
      } else {
        String _generateStart = this.process.generateStart();
        _builder.append(_generateStart);
      }
    }
    return _builder.toString();
  }
}
