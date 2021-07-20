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
import su.nsk.iae.post.generator.st.common.vars.data.VarData
import su.nsk.iae.post.poST.Process

import static su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class ProgramGenerator {
	
	protected String programName
	protected String type
	
	protected VarHelper inVarList = new InputVarHelper
	protected VarHelper outVarList = new OutputVarHelper
	protected VarHelper inOutVarList = new InputOutputVarHelper
	protected VarHelper externalVarList = new ExternalVarHelper
	protected VarHelper varList = new SimpleVarHelper
	protected VarHelper tempVarList = new TempVarHelper
	
	protected List<ProcessGenerator> processList = new LinkedList
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«programName.toLowerCase».st''', generateProgram)
	}
	
	def String generateProgram() '''
		«type» «programName»
		
		«inVarList.generateVars»
		«outVarList.generateVars»
		«inOutVarList.generateVars»
		«externalVarList.generateVars»
		«varList.generateVars»
		«tempVarList.generateVars»
		
		«generateGlobalTime» := TIME();
		
		«FOR p : processList»
			«p.generateBody»
			
		«ENDFOR»
		END_«type»
	'''
	
	static def String generateVars(VarHelper helper) '''
		«IF !helper.list.empty»
			«IF helper.hasConstant»
				«helper.type» CONSTANT
					«FOR v : helper.list»
						«IF v.isConstant»
							«v.generateSingleDeclaration»«v.generateValue»;
						«ENDIF»
					«ENDFOR»
				END_VAR
				
			«ENDIF»
			«IF helper.hasNonConstant»
				«helper.type»
					«FOR v : helper.list»
						«IF !v.isConstant»
							«v.generateSingleDeclaration»«v.generateValue»;
						«ENDIF»
					«ENDFOR»
				END_VAR
				
			«ENDIF»
		«ENDIF»
	'''
	
	protected def parseProcesses(EList<Process> processes) {
		for (p: processes) {
			processList.add(new ProcessGenerator(this, p))
		}
		addVar(generateGlobalTime, "TIME")
		for (p : processList) {
			p.addTimeVars()
		}
		for (p : processList) {
			p.addStateVars()
		}
		addVar(generateStopConstant, "INT", "254", true)
		addVar(generateErrorConstant, "INT", "255", true)
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
		return processList.get(0) == process
	}
	
	def String generateProcessEnum(String processName) {
		return processList.findFirst[name == processName].generateEnumName
	}
	
	def String generateProcessStart(String processName) {
		return processList.findFirst[name == processName].generateStart
	}
	
	private static def String generateSingleDeclaration(VarData data) {
		return '''«data.name» : «data.type»'''
	}
	
	private static def String generateValue(VarData v) {
		if ((v.value === null) && (v.arraValues === null)) {
			return ''''''
		}
		if (v.array) {
			return ''' := [«v.arraValues.map[it].join(', ')»]'''	
		}
		return ''' := «v.value»'''
	}
	
}
