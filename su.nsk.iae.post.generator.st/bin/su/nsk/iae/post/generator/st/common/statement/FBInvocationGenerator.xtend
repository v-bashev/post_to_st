package su.nsk.iae.post.generator.st.common.statement

import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.StateGenerator
import su.nsk.iae.post.generator.st.common.StatementListGenerator
import su.nsk.iae.post.poST.Statement
import su.nsk.iae.post.poST.FBInvocation

class FBInvocationGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof FBInvocation
	}
	
	override generateStatement(Statement statement) {
		val s = statement as FBInvocation
		return '''«s.fb.name»(«context.generateParamAssignmentElements(s.args)»)'''
	}
	
}