package su.nsk.iae.post.generator.plcopen.xml.common.statement

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator
import su.nsk.iae.post.poST.ResetTimerStatement
import su.nsk.iae.post.poST.Statement

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

class ResetTimerStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof ResetTimerStatement
	}
	
	override generateStatement(Statement statement) {
		return '''«process.generateTimeoutName» := «generateGlobalTime»;'''
	}
	
}