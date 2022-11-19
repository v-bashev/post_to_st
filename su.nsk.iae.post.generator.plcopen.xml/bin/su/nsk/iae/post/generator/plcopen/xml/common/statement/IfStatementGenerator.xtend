package su.nsk.iae.post.generator.plcopen.xml.common.statement

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StateGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.StatementListGenerator
import su.nsk.iae.post.poST.IfStatement
import su.nsk.iae.post.poST.Statement

class IfStatementGenerator extends IStatementGenerator {
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state, StatementListGenerator context) {
		super(program, process, state, context)
	}
	
	override checkStatement(Statement statement) {
		return statement instanceof IfStatement
	}
	
	override generateStatement(Statement statement) {
		val s = statement as IfStatement
		return '''
			IF «context.generateExpression(s.mainCond)» THEN
				«context.generateStatementList(s.mainStatement)»
			«IF !s.elseIfCond.empty»
				«FOR i : 0..(s.elseIfCond.size - 1)»
					ELSIF «context.generateExpression(s.elseIfCond.get(i))» THEN
						«context.generateStatementList(s.elseIfStatements.get(i))»
				«ENDFOR»
			«ENDIF»
			«IF s.elseStatement !== null»
				ELSE
					«context.generateStatementList(s.elseStatement)»
			«ENDIF»
			END_IF
		'''
	}
	
}