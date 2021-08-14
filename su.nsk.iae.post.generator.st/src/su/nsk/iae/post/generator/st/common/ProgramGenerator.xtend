package su.nsk.iae.post.generator.st.common

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.generator.IFileSystemAccess2
import su.nsk.iae.post.generator.st.common.vars.ExternalVarHelper
import su.nsk.iae.post.generator.st.common.vars.InputOutputVarHelper
import su.nsk.iae.post.generator.st.common.vars.InputVarHelper
import su.nsk.iae.post.generator.st.common.vars.OutputVarHelper
import su.nsk.iae.post.generator.st.common.vars.SimpleVarHelper
import su.nsk.iae.post.generator.st.common.vars.TempVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.poST.Process

import static extension su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class ProgramGenerator {
	
	protected EObject object
	protected String programName
	protected String type
	boolean templateProcess
	
	protected VarHelper inVarList = new InputVarHelper
	protected VarHelper outVarList = new OutputVarHelper
	protected VarHelper inOutVarList = new InputOutputVarHelper
	protected VarHelper externalVarList = new ExternalVarHelper
	protected VarHelper varList = new SimpleVarHelper
	protected VarHelper tempVarList = new TempVarHelper
	
	protected List<ProcessGenerator> processList = new LinkedList
	
	new (boolean templateProcess) {
		this.templateProcess = templateProcess
	}
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«programName.toLowerCase».st''', generateProgram)
	}
	
	def String generateProgram() {
		prepareProgramVars
		return generateBody
	}
	
	def String generateBody() '''
		«type» «programName»
		
		«IF !templateProcess»
			«inVarList.generateVars»
			«outVarList.generateVars»
			«inOutVarList.generateVars»
		«ENDIF»
		«externalVarList.generateVars»
		«varList.generateVars»
		«tempVarList.generateVars»
		
		«generateGlobalTime» := TIME();
		
		«FOR p : processList»
			«p.generateBody»
			
		«ENDFOR»
		END_«type»
	'''
	
	def String getName() {
		return programName
	}
	
	def EObject getEObject() {
		return object
	}
	
	protected def parseProcesses(EList<Process> processes) {
		processes.stream.forEach([p |
			val process = new ProcessGenerator(this, p)
			if (!process.isTemplate()) {
				processList.add(process)
			}
		])
	}
	
	def prepareProgramVars() {
		processList.stream.forEach([x | x.prepareProcessVars])
		addVar(generateGlobalTime, "TIME")
		processList.stream.forEach([x | x.prepareTimeVars])
		processList.stream.forEach([x | x.prepareStateVars(templateProcess)])
		addVar(generateStopConstant, "INT", "254", true)
		addVar(generateErrorConstant, "INT", "255", true)
	}
	
	def void addProcess(Process process) {
		processList.add(new ProcessGenerator(this, process))
	}
	
	def void addProcess(Process process, boolean active) {
		processList.add(new ProcessGenerator(this, process, active))
	}
	
	def void addVar(EObject varDecl) {
		varList.add(varDecl)
	}
	
	def void addVar(EObject varDecl, String pref) {
		varList.add(varDecl, pref)
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
	
	def void addTempVar(EObject varDecl, String pref) {
		tempVarList.add(varDecl, pref)
	}
	
	def void addTempVar(String name, String type, String value) {
		tempVarList.add(name, type, value)
	}
	
	def boolean isFirstProcess(ProcessGenerator process) {
		return processList.get(0) == process
	}
	
	def void addInVar(EObject varDecl) {
		inVarList.add(varDecl)
	}
	
	def void addInVar(EObject varDecl, String pref) {
		inVarList.add(varDecl, pref)
	}
	
	def void addOutVar(EObject varDecl) {
		outVarList.add(varDecl)
	}
	
	def void addOutVar(EObject varDecl, String pref) {
		outVarList.add(varDecl, pref)
	}
	
	def void addInOutVar(EObject varDecl) {
		inOutVarList.add(varDecl)
	}
	
	def void addInOutVar(EObject varDecl, String pref) {
		inOutVarList.add(varDecl, pref)
	}
	
	def String generateProcessEnum(String processName) {
		return processList.findFirst[name == processName].generateEnumName
	}
	
	def String generateProcessStart(String processName) {
		return processList.findFirst[name == processName].generateStart
	}
	
}
