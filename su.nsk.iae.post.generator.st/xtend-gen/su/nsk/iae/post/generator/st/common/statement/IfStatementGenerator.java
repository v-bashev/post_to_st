package su.nsk.iae.post.generator.st.common.statement;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import su.nsk.iae.post.generator.st.common.ProcessGenerator;
import su.nsk.iae.post.generator.st.common.ProgramGenerator;
import su.nsk.iae.post.generator.st.common.StateGenerator;
import su.nsk.iae.post.generator.st.common.StatementListGenerator;
import su.nsk.iae.post.poST.IfStatement;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.StatementList;

@SuppressWarnings("all")
public class IfStatementGenerator extends IStatementGenerator {
  public IfStatementGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof IfStatement);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final IfStatement s = ((IfStatement) statement);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("IF ");
    String _generateExpression = this.context.generateExpression(s.getMainCond());
    _builder.append(_generateExpression);
    _builder.append(" THEN");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateStatementList = this.context.generateStatementList(s.getMainStatement());
    _builder.append(_generateStatementList, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = s.getElseIfCond().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          int _size = s.getElseIfCond().size();
          int _minus = (_size - 1);
          IntegerRange _upTo = new IntegerRange(0, _minus);
          for(final Integer i : _upTo) {
            _builder.append("ELSIF ");
            String _generateExpression_1 = this.context.generateExpression(s.getElseIfCond().get((i).intValue()));
            _builder.append(_generateExpression_1);
            _builder.append(" THEN");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            String _generateStatementList_1 = this.context.generateStatementList(s.getElseIfStatements().get((i).intValue()));
            _builder.append(_generateStatementList_1, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      StatementList _elseStatement = s.getElseStatement();
      boolean _tripleNotEquals = (_elseStatement != null);
      if (_tripleNotEquals) {
        _builder.append("ELSE");
        _builder.newLine();
        _builder.append("\t");
        String _generateStatementList_2 = this.context.generateStatementList(s.getElseStatement());
        _builder.append(_generateStatementList_2, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("END_IF");
    _builder.newLine();
    return _builder.toString();
  }
}
