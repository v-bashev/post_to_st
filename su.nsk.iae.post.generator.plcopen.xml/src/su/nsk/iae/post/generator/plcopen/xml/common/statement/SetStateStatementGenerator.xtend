package su.nsk.iae.post.generator.plcopen.xml.common.statement

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator
import su.nsk.iae.post.poST.SetStateStatement
import su.nsk.iae.post.poST.Statement

class SetStateStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof SetStateStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as SetStateStatement
		return '''«IF s.next»«process.generateNextState(state)»«ELSE»«process.generateSetState(s.state.name)»«ENDIF»'''
	}
	
}