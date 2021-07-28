package su.nsk.iae.post.generator.st.common.statement

import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.StateGenerator
import su.nsk.iae.post.generator.st.common.StatementListGenerator
import su.nsk.iae.post.poST.Statement
import su.nsk.iae.post.poST.WhileStatement

class WhileStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof WhileStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as WhileStatement
		return '''
			WHILE «context.generateExpression(s.cond)» DO
				«context.generateStatementList(s.statement)»
			END_WHILE
		'''
	}
	
}