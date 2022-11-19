package su.nsk.iae.post.generator.st.common.statement

import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.StateGenerator
import su.nsk.iae.post.generator.st.common.StatementListGenerator
import su.nsk.iae.post.poST.RepeatStatement
import su.nsk.iae.post.poST.Statement

class RepeatStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof RepeatStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as RepeatStatement
		return '''
			REPEAT
				�context.generateStatementList(s.statement)�
			UNTIL �context.generateExpression(s.cond)� END_REPEAT
		'''
	}
	
}