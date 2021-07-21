package su.nsk.iae.post.generator.st.common

import su.nsk.iae.post.poST.State

import static extension su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class StateGenerator {
	
	ProcessGenerator process
	State state
	
	StatementListGenerator statementList
	
	new(ProgramGenerator program, ProcessGenerator process, State state) {
		this.process = process
		this.state = state
		statementList = new StatementListGenerator(program, process, this)
	}
	
	def String generateBody() '''
		«statementList.generateStatementList(state.statement)»
		«IF state.timeout !== null»
			«generateTimeout»
		«ENDIF»
	'''
	
	def String getName() {
		return state.name
	}
	
	def boolean hasTimeout() {
		return state.timeout !== null
	}
	
	private def String generateTimeout() '''
		IF («generateGlobalTime» - «process.generateTimeoutName») >= «IF state.timeout.variable !== null»«statementList.generateVar(state.timeout.variable)»«ELSE»«state.timeout.const.generateConstant»«ENDIF» THEN
			«statementList.generateStatementList(state.timeout.statement)»
		END_IF
	'''
	
}