package su.nsk.iae.post.generator.st.common.statement

import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.StateGenerator
import su.nsk.iae.post.generator.st.common.StatementListGenerator
import su.nsk.iae.post.poST.StartProcessStatement
import su.nsk.iae.post.poST.Statement

class StartProcessStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof StartProcessStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as StartProcessStatement
		return '''«IF s.process !== null»«program.generateProcessStart(s.process.name)»«ELSE»«process.generateStart»«ENDIF»'''
	}
	
}