package su.nsk.iae.post.generator.st.common

import java.util.LinkedList
import java.util.List
import su.nsk.iae.post.generator.st.common.vars.InputOutputVarHelper
import su.nsk.iae.post.generator.st.common.vars.InputVarHelper
import su.nsk.iae.post.generator.st.common.vars.OutputVarHelper
import su.nsk.iae.post.generator.st.common.vars.SimpleVarHelper
import su.nsk.iae.post.generator.st.common.vars.TempVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.poST.Process

import static su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class ProcessGenerator {
	
	ProgramGenerator program
	Process process
	
	VarHelper inVarList = new InputVarHelper
	VarHelper outVarList = new OutputVarHelper
	VarHelper inOutVarList = new InputOutputVarHelper
	VarHelper varList = new SimpleVarHelper
	VarHelper tempVarList = new TempVarHelper
	
	List<StateGenerator> stateList = new LinkedList
	
	new(ProgramGenerator program, Process process) {
		this.program = program
		this.process = process
		process.states.stream.forEach([s | stateList.add(new StateGenerator(program, this, s))])
	}
	
	def String generateBody() '''
		CASE «generateEnumName» OF
			«FOR s : stateList»
				«s.name.enumStateName»:
					«s.generateBody»
			«ENDFOR»
		END_CASE
	'''
	
	def String getName() {
		return process.name
	}
	
	def String generateEnumName() {
		return '''_g_p_«name»_state'''
	}
	
	def String generateTimeoutName() {
		return '''_g_p_«name»_time'''
	}
	
	def String getEnumStateName(String name) {
		return '''_P_«this.name.toUpperCase»_S_«name.toUpperCase»'''
	}
	
	def String getVarName(String variable) {
		return '''_p_«name»_v_«variable»'''
	}
	
	def boolean containsVar(String name) {
		return varList.contains(name) || tempVarList.contains(name) || 
			   inVarList.contains(name) || outVarList.contains(name) || inOutVarList.contains(name)
	}
	
	def String generateSetState(String stateName) '''
		«IF stateList.findFirst[name == stateName].hasTimeout»«generateTimeoutName» := «generateGlobalTime»;«ENDIF»
		«generateEnumName» := «stateName.enumStateName»;
	'''
	
	def String generateNextState(StateGenerator state) {
		if (stateList.indexOf(state) + 1 < stateList.size) {
			val s = stateList.get(stateList.indexOf(state) + 1)
			return '''
				«IF s.hasTimeout»«generateTimeoutName» := «generateGlobalTime»;«ENDIF»
				«generateEnumName» := «s.name.enumStateName»;
			'''
		}
		val s = stateList.get(0)
		return '''
			«IF s.hasTimeout»«generateTimeoutName» := «generateGlobalTime»;«ENDIF»
			«generateEnumName» := «s.name.enumStateName»;
		'''
	}
	
	def String generateStart() '''
		«FOR v : varList.list»
			«IF v.value !== null && ! v.isConstant»
				«v.name.varName» := «v.value»;
			«ENDIF»
		«ENDFOR»
		«IF stateList.get(0).hasTimeout»«generateTimeoutName» := «generateGlobalTime»;«ENDIF»
		«generateEnumName» := «stateList.get(0).name.enumStateName»;
	'''
	
	def boolean isTemplate() {
		return (!process.procInVars.empty) || (!process.procOutVars.empty) || (!process.procInOutVars.empty)
	}
	
	def void prepareStateVars() {
		for (var i = 0; i < stateList.size; i++) {
			program.addVar(stateList.get(i).name.enumStateName, "INT", i.toString, true)
		}
		if (program.isFirstProcess(this)) {
			program.addVar(generateEnumName, "INT", stateList.get(0).name.enumStateName)
		} else {
			program.addVar(generateEnumName, "INT", generateStopConstant)
		}
	}
	
	def void prepareTimeVars() {
		if (hasTimeouts) {
			program.addVar(generateTimeoutName, "TIME")
		}
	}
	
	def void prepareProcessVars() {
		prepareVars()
		prepareTempVars()
		if (isTemplate()) {
			prepareInVars()
			prepareOutVars()
			prepareInOutVars()
		}
	}
	
	private def void prepareVars() {
		process.procVars.stream.forEach([varDecl | 
			varList.add(varDecl)
			program.addVar(varDecl, "".varName)
		])
	}
	
	private def void prepareTempVars() {
		process.procTempVars.stream.forEach([varDecl | 
			tempVarList.add(varDecl)
			program.addTempVar(varDecl, "".varName)
		])
	}
	
	private def void prepareInVars() {
		process.procInVars.stream.forEach([varDecl | 
			inVarList.add(varDecl)
			program.addInVar(varDecl, "".varName)
		])
	}
	
	private def void prepareOutVars() {
		process.procOutVars.stream.forEach([varDecl | 
			outVarList.add(varDecl)
			program.addOutVar(varDecl, "".varName)
		])
	}
	
	private def void prepareInOutVars() {
		process.procInOutVars.stream.forEach([varDecl | 
			inOutVarList.add(varDecl)
			program.addInOutVar(varDecl, "".varName)
		])
	}
	
	private def boolean hasTimeouts() {
		return stateList.stream.anyMatch([x | x.hasTimeout])
	}
	
}