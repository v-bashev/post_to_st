package su.nsk.iae.post.generator.plcopen.xml.common.statement;

import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.WhileStatement;

@SuppressWarnings("all")
public class WhileStatementGenerator extends IStatementGenerator {
  public WhileStatementGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof WhileStatement);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final WhileStatement s = ((WhileStatement) statement);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("WHILE ");
    String _generateExpression = this.context.generateExpression(s.getCond());
    _builder.append(_generateExpression);
    _builder.append(" DO");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateStatementList = this.context.generateStatementList(s.getStatement());
    _builder.append(_generateStatementList, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("END_WHILE");
    _builder.newLine();
    return _builder.toString();
  }
}
