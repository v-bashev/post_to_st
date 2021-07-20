package su.nsk.iae.post.generator.st.common.statement

import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.StateGenerator
import su.nsk.iae.post.generator.st.common.StatementListGenerator
import su.nsk.iae.post.poST.ErrorProcessStatement
import su.nsk.iae.post.poST.Statement

import static su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class ErrorProcessStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof ErrorProcessStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as ErrorProcessStatement
		return '''«IF s.process !== null»«program.generateProcessEnum(s.process.name)»«ELSE»«process.generateEnumName»«ENDIF» := «generateErrorConstant»;'''
	}
	
}