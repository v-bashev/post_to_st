package su.nsk.iae.post.generator.st.common

import java.util.LinkedList
import java.util.List
import su.nsk.iae.post.generator.st.common.vars.SimpleVarHelper
import su.nsk.iae.post.generator.st.common.vars.TempVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.poST.Process
import su.nsk.iae.post.generator.st.ICodeGenerator

class ProcessGenerator {
	
	ICodeGenerator program
	Process process
	
	VarHelper varList = new SimpleVarHelper
	VarHelper tempVarList = new TempVarHelper
	
	List<StateGenerator> stateList = new LinkedList
	
	new(ICodeGenerator program, Process process) {
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
			for (n : v.vars) {
				for (e : n.varList.vars) {
					e.name = e.name.substring("".varName.length)
				}
			}
		}
		for (v : process.procTempVars) {
			tempVarList.add(v)
			for (n : v.vars) {
				for (e : n.varList.vars) {
					e.name = e.name.varName
				}
			}
			program.addTempVar(v)
			for (n : v.vars) {
				for (e : n.varList.vars) {
					e.name = e.name.substring("".varName.length)
				}
			}
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
		return '''_P_«this.name.toUpperCase»_S_«name.toUpperCase»'''
	}
	
	def boolean containsVar(String name) {
		return varList.contains(name) || tempVarList.contains(name)
	}
	
	def String getVarName(String variable) {
		return '''_p_«name»_v_«variable»'''
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
		return '''_g_p_«name»_state'''
	}
	
	def String generateTimeoutName() {
		return '''_g_p_«name»_time'''
	}
	
	def String generateStart() '''
		«FOR v : varList.list»
			«IF v.value !== null && ! v.isConstant»
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