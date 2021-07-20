package su.nsk.iae.post.generator.st.common.statement

import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.StateGenerator
import su.nsk.iae.post.generator.st.common.StatementListGenerator
import su.nsk.iae.post.poST.ForStatement
import su.nsk.iae.post.poST.Statement

class ForStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof ForStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as ForStatement
		return '''
			FOR «context.generateVar(s.variable)» := «context.generateExpression(s.forList.start)» TO «context.generateExpression(s.forList.end)»«IF s.forList.step !== null» BY «context.generateExpression(s.forList.step)»«ENDIF» DO
				«context.generateStatementList(s.statement)»
			END_FOR
		'''
	}
	
}