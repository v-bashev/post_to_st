package su.nsk.iae.post.generator.plcopen.xml.common.statement;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator;
import su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil;
import su.nsk.iae.post.poST.CaseElement;
import su.nsk.iae.post.poST.CaseStatement;
import su.nsk.iae.post.poST.SignedInteger;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.StatementList;

@SuppressWarnings("all")
public class CaseStatementGenerator extends IStatementGenerator {
  public CaseStatementGenerator(final ProgramGenerator program, final ProcessGenerator process, final StateGenerator state, final StatementListGenerator context) {
    super(program, process, state, context);
  }
  
  @Override
  public boolean checkStatement(final Statement statement) {
    return (statement instanceof CaseStatement);
  }
  
  @Override
  public String generateStatement(final Statement statement) {
    final CaseStatement s = ((CaseStatement) statement);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CASE ");
    String _generateExpression = this.context.generateExpression(s.getCond());
    _builder.append(_generateExpression);
    _builder.append(" OF");
    _builder.newLineIfNotEmpty();
    {
      EList<CaseElement> _caseElements = s.getCaseElements();
      for(final CaseElement e : _caseElements) {
        {
          EList<SignedInteger> _caseListElement = e.getCaseList().getCaseListElement();
          for(final SignedInteger c : _caseListElement) {
            _builder.append("\t");
            String _generateSignedInteger = GeneratorUtil.generateSignedInteger(c);
            _builder.append(_generateSignedInteger, "\t");
            {
              int _indexOf = e.getCaseList().getCaseListElement().indexOf(c);
              int _size = e.getCaseList().getCaseListElement().size();
              int _minus = (_size - 1);
              boolean _lessThan = (_indexOf < _minus);
              if (_lessThan) {
                _builder.append(", ");
              } else {
                _builder.append(":");
              }
            }
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        String _generateStatementList = this.context.generateStatementList(e.getStatement());
        _builder.append(_generateStatementList, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      StatementList _elseStatement = s.getElseStatement();
      boolean _tripleNotEquals = (_elseStatement != null);
      if (_tripleNotEquals) {
        _builder.append("\t");
        _builder.append("ELSE");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        String _generateStatementList_1 = this.context.generateStatementList(s.getElseStatement());
        _builder.append(_generateStatementList_1, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("END_CASE");
    _builder.newLine();
    return _builder.toString();
  }
}
