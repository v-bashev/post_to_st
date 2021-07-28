package su.nsk.iae.post.generator.st.common.statement;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.st.common.ProcessGenerator;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.generator.st.common.StateGenerator;
import su.nsk.iae.post.generator.st.common.StatementListGenerator;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.ForStatement;
import su.nsk.iae.post.poST.Statement;

@SuppressWarnings("all")
public class ForStatementGenerator extends IStatementGenerator {
  public ForStatementGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof ForStatement);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final ForStatement s = ((ForStatement) statement);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FOR ");
    String _generateVar = this.context.generateVar(s.getVariable());
    _builder.append(_generateVar);
    _builder.append(" := ");
    String _generateExpression = this.context.generateExpression(s.getForList().getStart());
    _builder.append(_generateExpression);
    _builder.append(" TO ");
    String _generateExpression_1 = this.context.generateExpression(s.getForList().getEnd());
    _builder.append(_generateExpression_1);
    {
      Expression _step = s.getForList().getStep();
      boolean _tripleNotEquals = (_step != null);
      if (_tripleNotEquals) {
        _builder.append(" BY ");
        String _generateExpression_2 = this.context.generateExpression(s.getForList().getStep());
        _builder.append(_generateExpression_2);
      }
    }
    _builder.append(" DO");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateStatementList = this.context.generateStatementList(s.getStatement());
    _builder.append(_generateStatementList, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("END_FOR");
    _builder.newLine();
    return _builder.toString();
  }
}
