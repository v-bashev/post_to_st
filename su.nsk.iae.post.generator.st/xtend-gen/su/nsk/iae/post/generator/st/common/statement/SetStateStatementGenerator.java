package su.nsk.iae.post.generator.st.common.statement;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.st.common.ProcessGenerator;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.generator.st.common.StateGenerator;
import su.nsk.iae.post.generator.st.common.StatementListGenerator;
import su.nsk.iae.post.poST.SetStateStatement;
import su.nsk.iae.post.poST.Statement;

@SuppressWarnings("all")
public class SetStateStatementGenerator extends IStatementGenerator {
  public SetStateStatementGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof SetStateStatement);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final SetStateStatement s = ((SetStateStatement) statement);
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isNext = s.isNext();
      if (_isNext) {
        String _generateNextState = this.process.generateNextState(this.state);
        _builder.append(_generateNextState);
      } else {
        String _generateSetState = this.process.generateSetState(s.getState().getName());
        _builder.append(_generateSetState);
      }
    }
    return _builder.toString();
  }
}
