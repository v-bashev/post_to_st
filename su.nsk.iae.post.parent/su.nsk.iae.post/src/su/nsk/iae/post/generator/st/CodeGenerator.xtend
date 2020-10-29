package su.nsk.iae.post.generator.st

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.generator.IFileSystemAccess2
import su.nsk.iae.post.generator.st.vars.ExternalVarHelper
import su.nsk.iae.post.generator.st.vars.InputOutputVarHelper
import su.nsk.iae.post.generator.st.vars.InputVarHelper
import su.nsk.iae.post.generator.st.vars.OutputVarHelper
import su.nsk.iae.post.generator.st.vars.SimpleVarHelper
import su.nsk.iae.post.generator.st.vars.TempVarHelper
import su.nsk.iae.post.generator.st.vars.VarHelper
import su.nsk.iae.post.poST.Process

class CodeGenerator {
	
	protected String codeName
	protected String type
	
	protected VarHelper inVarList = new InputVarHelper
	protected VarHelper outVarList = new OutputVarHelper
	protected VarHelper inOutVarList = new InputOutputVarHelper
	protected VarHelper externalVarList = new ExternalVarHelper
	protected VarHelper varList = new SimpleVarHelper
	protected VarHelper tempVarList = new TempVarHelper
	
	List<ProcessGenerator> processList = new LinkedList
	
	protected def parseProcesses(EList<Process> processes) {
		addVar(generateGlobalTime, "TIME")
		for (p: processes) {
			processList.add(new ProcessGenerator(this, p))
		}
		addVar(generateStopConstant, "INT", "254", true)
		addVar(generateErrorConstant, "INT", "255", true)
	}
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«codeName.toLowerCase».st''', generateCode)
	}
	
	private def String generateCode() '''
		«type» «codeName»

		«inVarList.generate»
		«outVarList.generate»
		«inOutVarList.generate»
		«externalVarList.generate»
		«varList.generate»
		«tempVarList.generate»
		
		«generateGlobalTime» := TIME();
		
		«FOR p : processList»
			«p.generateBody»
			
		«ENDFOR»
		END_«type»
	'''
	
	def String generateStopConstant() {
		return '''ALL_PROCESSES_STOP_CONSTANT'''
	}
	
	def String generateErrorConstant() {
		return '''ALL_PROCESSES_ERROR_CONSTANT'''
	}
	
	def String generateGlobalTime() {
		return '''cycle_global_time'''
	}
	
	def String generateProcessEnum(String processName) {
		return processList.findFirst[name == processName].generateEnumName
	}
	
	def String generateProcessStart(String processName) {
		return processList.findFirst[name == processName].generateStart
	}
	
	def void addVar(EObject varDecl) {
		varList.add(varDecl)
	}
	
	def void addVar(String name, String type) {
		varList.add(name, type)
	}
	
	def void addVar(String name, String type, String value) {
		varList.add(name, type, value)
	}
	
	def void addVar(String name, String type, String value, boolean isConstant) {
		varList.add(name, type, value, isConstant)
	}
	
	def void addTempVar(EObject varDecl) {
		tempVarList.add(varDecl)
	}
	
	def void addTempVar(String name, String type, String value) {
		tempVarList.add(name, type, value)
	}
	
	def boolean isFirstProcess(ProcessGenerator process) {
		return processList.empty
	}
}