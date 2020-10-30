package su.nsk.iae.post.generator.st

import java.util.LinkedList
import java.util.List
import su.nsk.iae.post.generator.st.vars.SimpleVarHelper
import su.nsk.iae.post.generator.st.vars.TempVarHelper
import su.nsk.iae.post.generator.st.vars.VarHelper
import su.nsk.iae.post.poST.Process

class ProcessGenerator {
	
	CodeGenerator program
	Process process
	
	VarHelper varList = new SimpleVarHelper
	VarHelper tempVarList = new TempVarHelper
	
	List<StateGenerator> stateList = new LinkedList
	
	new(CodeGenerator program, Process process) {
		this.program = program
		this.process = process
		
		for (v : process.procVars) {
			varList.add(v)
			for (n : v.vars) {
				for (e : n.varList.vars) {
					e.name = e.name.varName
				}
			}
			program.addVar(v)
		}
		for (v : process.procTempVars) {
			tempVarList.add(v)
			for (n : v.vars) {
				for (e : n.varList.vars) {
					e.name = e.name.varName
				}
			}
			program.addTempVar(v)
		}
		
		for (s : process.states) {
			stateList.add(new StateGenerator(program, this, s))
		}
	}
	
	def void addStateVars() {
		for (var i = 0; i < stateList.size; i++) {
			program.addVar(stateList.get(i).name.enumStateName, "INT", i.toString, true)
		}
		if (program.isFirstProcess(this)) {
			program.addVar(generateEnumName, "INT", stateList.get(0).name.enumStateName)
		} else {
			program.addVar(generateEnumName, "INT", program.generateStopConstant)
		}
	}
	
	def void addTimeVars() {
		if (hasTimeouts) {
			program.addVar(generateTimeoutName, "TIME")
		}
	}
	
	def String getName() {
		return process.name
	}
	
	def String getEnumStateName(String name) {
		return '''PROCESS_«this.name.toUpperCase»_STATE_«name.toUpperCase»'''
	}
	
	def boolean containsVar(String name) {
		return varList.contains(name) || tempVarList.contains(name)
	}
	
	def String getVarName(String variable) {
		return '''process_«name.toLowerCase»_var_«variable»'''
	}
	
	def String generateSetState(String stateName) '''
		«IF stateList.findFirst[name == stateName].hasTimeout»«generateTimeoutName» := «program.generateGlobalTime»;«ENDIF»
		«generateEnumName» := «stateName.enumStateName»;
	'''
	
	def String generateNextState(StateGenerator state) {
		if (stateList.indexOf(state) + 1 < stateList.size) {
			val s = stateList.get(stateList.indexOf(state) + 1)
			return '''
				«IF s.hasTimeout»«generateTimeoutName» := «program.generateGlobalTime»;«ENDIF»
				«generateEnumName» := «s.name.enumStateName»;
			'''
		}
		val s = stateList.get(0)
		return '''
			«IF s.hasTimeout»«generateTimeoutName» := «program.generateGlobalTime»;«ENDIF»
			«generateEnumName» := «s.name.enumStateName»;
		'''
	}
	
	def String generateEnumName() {
		return '''g_process_«name.toLowerCase»_current_state'''
	}
	
	def String generateTimeoutName() {
		return '''g_process_«name.toLowerCase»_start_time'''
	}
	
	def String generateStart() '''
		«FOR v : varList.list»
			«IF v.value !== null»
				«v.name.varName» := «v.value»;
			«ENDIF»
		«ENDFOR»
		«IF stateList.get(0).hasTimeout»«generateTimeoutName» := «program.generateGlobalTime»;«ENDIF»
		«generateEnumName» := «stateList.get(0).name.enumStateName»;
	'''
	
	def String generateBody() '''
		CASE «generateEnumName» OF
			«FOR s : stateList»
				«s.name.enumStateName»:
					«s.generateBody»
			«ENDFOR»
		END_CASE
	'''
	
	private def boolean hasTimeouts() {
		for (s : stateList) {
			if (s.hasTimeout) {
				return true;
			}
		}
		return false;
	}
}