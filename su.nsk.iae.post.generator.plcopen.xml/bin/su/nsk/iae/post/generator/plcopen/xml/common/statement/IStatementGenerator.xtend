package su.nsk.iae.post.generator.plcopen.xml.common.statement

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator
import su.nsk.iae.post.poST.Statement

abstract class IStatementGenerator {
	
	protected ProgramGenerator program
	protected ProcessGenerator process
	protected StateGenerator state
	protected StatementListGenerator context
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		this.program = program
		this.process = process
		this.state = state
		this.context = context
	}
	
	def boolean checkStatement(Statement statement)
	
	def String generateStatement(Statement statement)
	
}