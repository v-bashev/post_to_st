package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.st.vars.VarHelper
import su.nsk.iae.post.generator.st.vars.InputVarHelper
import su.nsk.iae.post.generator.st.vars.OutputVarHelper
import su.nsk.iae.post.generator.st.vars.InputOutputVarHelper
import su.nsk.iae.post.generator.st.vars.ExternalVarHelper
import su.nsk.iae.post.generator.st.vars.SimpleVarHelper
import su.nsk.iae.post.generator.st.vars.TempVarHelper
import su.nsk.iae.post.poST.Program
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject

class ProgramGenerator {
	
	Program program
	
	VarHelper inVarList = new InputVarHelper
	VarHelper outVarList = new OutputVarHelper
	VarHelper inOutVarList = new InputOutputVarHelper
	VarHelper externalVarList = new ExternalVarHelper
	VarHelper varList = new SimpleVarHelper
	VarHelper tempVarList = new TempVarHelper
	
	List<ProcessGenerator> processList = new LinkedList
	
	new(Program program) {
		this.program = program

		for (v : program.progInVars) {
			inVarList.add(v)
		}
		for (v : program.progOutVars) {
			outVarList.add(v)
		}
		for (v : program.progInOutVars) {
			inOutVarList.add(v)
		}
		for (v : program.progExternVars) {
			externalVarList.add(v)
		}
		for (v : program.progVars) {
			varList.add(v)
		}
		for (v : program.progTempVars) {
			tempVarList.add(v)
		}
		
		for (p: program.processes) {
			processList.add(new ProcessGenerator(this, p))
		}
		addVar(generateStopConstant, "INT", "254", true)
		addVar(generateErrorConstant, "INT", "255", true)
	}
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«program.name.toLowerCase».st''', generateCode)
	}
	
	private def String generateCode() '''
		PROGRAM «program.name»

			«inVarList.generate»
			«outVarList.generate»
			«inOutVarList.generate»
			«externalVarList.generate»
			«varList.generate»
			«tempVarList.generate»
		
			«FOR p : processList»
				«p.generateBody»
				
			«ENDFOR»
		END_PROGRAM
		
	'''
	
	def String generateStopConstant() {
		return '''ALL_PROCESSES_STOP_CONSTANT'''
	}
	
	def String generateErrorConstant() {
		return '''ALL_PROCESSES_ERROR_CONSTANT'''
	}
	
	def String generateProcessEnum(String processName) {
		return processList.findFirst[name == processName].generateEnumName
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