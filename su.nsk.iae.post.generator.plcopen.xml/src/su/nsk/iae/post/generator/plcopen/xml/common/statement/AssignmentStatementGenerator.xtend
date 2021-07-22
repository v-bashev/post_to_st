package su.nsk.iae.post.generator.plcopen.xml.common.statement

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator
import su.nsk.iae.post.poST.AssignmentStatement
import su.nsk.iae.post.poST.Statement

class AssignmentStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof AssignmentStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as AssignmentStatement
		if (s.variable !== null){
			return '''«context.generateVar(s.variable)» := «context.generateExpression(s.value)»;'''
		}
		return '''«context.generateArray(s.array)» := «context.generateExpression(s.value)»;'''		
	}
	
}