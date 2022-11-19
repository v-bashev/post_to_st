package su.nsk.iae.post.generator.plcopen.xml.common.statement

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator
import su.nsk.iae.post.poST.ErrorProcessStatement
import su.nsk.iae.post.poST.Statement

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

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