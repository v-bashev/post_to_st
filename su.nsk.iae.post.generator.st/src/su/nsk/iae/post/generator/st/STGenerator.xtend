package su.nsk.iae.post.generator.st

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.generator.IPoSTGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.configuration.ConfigurationGenerator
import su.nsk.iae.post.poST.ArrayVariable
import su.nsk.iae.post.poST.AssignmentStatement
import su.nsk.iae.post.poST.AttachVariableConfElement
import su.nsk.iae.post.poST.ForStatement
import su.nsk.iae.post.poST.Model
import su.nsk.iae.post.poST.PrimaryExpression
import su.nsk.iae.post.poST.ProcessStatements
import su.nsk.iae.post.poST.SymbolicVariable
import su.nsk.iae.post.poST.TemplateProcessAttachVariableConfElement
import su.nsk.iae.post.poST.TemplateProcessConfElement
import su.nsk.iae.post.poST.TimeoutStatement
import su.nsk.iae.post.poST.Variable

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*
import static extension su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class STGenerator implements IPoSTGenerator {
	
	ConfigurationGenerator configuration = null
	VarHelper globVarList = new GlobalVarHelper
	List<ProgramGenerator> programs = new LinkedList
	
	override setModel(Model model) {
		globVarList.clear()
		programs.clear()
		model.globVars.stream.forEach([v | globVarList.add(v)])
		if (model.conf !== null) {
			configuration = new ConfigurationGenerator(model.conf)
			configuration.resources.stream.map([res | res.resStatement.programConfs]).flatMap([res | res.stream]).forEach([programConf | 
				val program = programConf.program.copy()
				program.name = programConf.name.capitalizeFirst
				programs.add(new ProgramPOUGenerator(program))
			])
		} else {
			model.programs.stream.forEach([p | programs.add(new ProgramPOUGenerator(p))])
			model.fbs.stream.forEach([fb | programs.add(new FunctionBlockPOUGenerator(fb))])
		}
	}
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		preparePrograms()
	}
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		generateSingleFile(fsa, "")
	}
	
	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {}
	
	private def void generateSingleFile(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»poST_code.st''', generateSingleFileBody)
	}
	
//	private def void generateMultipleFiles(IFileSystemAccess2 fsa, String path) {
//		fsa.generateFile('''«path»GVL.st''', ProgramGenerator.generateVar(globVarList))
//		for (c : programs) {
//			c.generate(fsa, path)
//		}
//	}

	private def String generateSingleFileBody() '''
		«globVarList.generateVars»
		«configuration.generateConfiguration»
		«FOR c : programs»
			«c.generateProgram»
			
		«ENDFOR»
	'''
	
	private def void preparePrograms() {
		if (configuration === null) {
			return
		}
		configuration.resources.stream.map([res | res.resStatement.programConfs]).flatMap([res | res.stream]).forEach([programConf |
			val programConfName = programConf.name.capitalizeFirst
			val programGen = programs.stream.filter([x | x.name == programConfName]).findFirst.get
			programConf.args.elements.stream.forEach([confElement |
				if (confElement instanceof TemplateProcessConfElement) {
					val process = confElement.process.copy
					process.name = confElement.name.capitalizeFirst
					confElement.args.elements.stream.forEach([e | e.changeAllVars(process)])
					programGen.addProcess(process)
				} else if (confElement instanceof AttachVariableConfElement) {
					confElement.changeAllVars(programGen.EObject)
				}
			])
		])
	}
	
	private def void changeAllVars(AttachVariableConfElement element, EObject root) {
		changeAllVars(element.programVar, element.attVar, root)
	}
	
	private def void changeAllVars(TemplateProcessAttachVariableConfElement element, EObject root) {
		changeAllVars(element.programVar, element.attVar, root)
	}
	
	private def void changeAllVars(Variable programVar, Variable attVar, EObject root) {
		root.getAllContentsOfType(PrimaryExpression).stream.filter([v | (v.variable !== null) && (v.variable.name == programVar.name)]).forEach([v |
			v.variable = (attVar as SymbolicVariable).copy
		])
		root.getAllContentsOfType(AssignmentStatement).stream.filter([v | (v.variable !== null) && (v.variable.name == programVar.name)]).forEach([v |
			v.variable = (attVar as SymbolicVariable).copy
		])
		root.getAllContentsOfType(ForStatement).stream.filter([v | v.variable.name == programVar.name]).forEach([v |
			v.variable = (attVar as SymbolicVariable).copy
		])
		root.getAllContentsOfType(ArrayVariable).stream.filter([v | v.variable.name == programVar.name]).forEach([v |
			v.variable = (attVar as SymbolicVariable).copy
		])
		root.getAllContentsOfType(TimeoutStatement).stream.filter([v | (v.variable !== null) && (v.variable.name == programVar.name)]).forEach([v |
			v.variable = (attVar as SymbolicVariable).copy
		])
		root.getAllContentsOfType(ProcessStatements).stream.filter([v | (v.process !== null) && (v.process.name == programVar.name)]).forEach([v |
			v.process.name = v.process.name.capitalizeFirst
		])
	}
	
	private def String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1)
	}
	
}
