package su.nsk.iae.post.generator.plcopen.xml

import java.io.File
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.eclipse.xtext.generator.JavaIoFileSystemAccess
import su.nsk.iae.post.IDsmExecutor
import su.nsk.iae.post.PoSTStandaloneSetup
import su.nsk.iae.post.generator.IPoSTGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.generator.plcopen.xml.configuration.ConfigurationGenerator
import su.nsk.iae.post.poST.ArrayVariable
import su.nsk.iae.post.poST.AssignmentStatement
import su.nsk.iae.post.poST.AttachVariableConfElement
import su.nsk.iae.post.poST.Constant
import su.nsk.iae.post.poST.ForStatement
import su.nsk.iae.post.poST.Model
import su.nsk.iae.post.poST.PrimaryExpression
import su.nsk.iae.post.poST.ProcessStatements
import su.nsk.iae.post.poST.ProcessStatusExpression
import su.nsk.iae.post.poST.SymbolicVariable
import su.nsk.iae.post.poST.TemplateProcessAttachVariableConfElement
import su.nsk.iae.post.poST.TemplateProcessConfElement
import su.nsk.iae.post.poST.TimeoutStatement
import su.nsk.iae.post.poST.Variable

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*
import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

class XMLGenerator implements IDsmExecutor, IPoSTGenerator {

	ConfigurationGenerator configuration = null
	VarHelper globVarList = new GlobalVarHelper
	List<ProgramGenerator> programs = new LinkedList

	override execute(String root, String fileName, Resource resource) {
		try {
			val fsa = PoSTStandaloneSetup.getInjector().getInstance(JavaIoFileSystemAccess);
			val generatePath = root + File.separator + "xml" + File.separator + fileName;
			fsa.setOutputPath(generatePath);

			model = resource.allContents.toIterable.filter(Model).get(0)
			beforeGenerate(resource, fsa, null);
			doGenerate(resource, fsa, null);
			afterGenerate(resource, fsa, null);
			return "Files generated in " + generatePath;
		} catch (Throwable th) {
			//Do nothing
		}
		return "Failure";
	}

	override setModel(Model model) {
		globVarList.clear()
		programs.clear()
		model.globVars.stream.forEach([v|globVarList.add(v)])
		if (model.conf !== null) {
			configuration = new ConfigurationGenerator(model.conf, this)
			configuration.resources.stream.map([res|res.resStatement.programConfs]).flatMap([res|res.stream]).
				forEach([ programConf |
					val program = programConf.program.copy()
					program.name = programConf.name.capitalizeFirst
					programs.add(new ProgramPOUGenerator(program, true))
				])
		} else {
			model.programs.stream.forEach([p|programs.add(new ProgramPOUGenerator(p, false))])
			model.fbs.stream.forEach([fb|programs.add(new FunctionBlockPOUGenerator(fb, false))])
		}
	}

	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		preparePrograms()
	}

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		generateSingleFile(fsa, "")
	}

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {}

	def void addGlobalVar(EObject varDecl) {
		globVarList.add(varDecl)
	}

	private def void generateSingleFile(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»poST_code.xml''', generateSingleXMLFile)
	}

//	private def void generateMultipleFiles(IFileSystemAccess2 fsa, String path) {
//		for (p : programs) {
//			p.generate(fsa, path)
//		}
//		if (!globVarList.list.empty) {
//			fsa.generateFile('''«path»GVL.xml''', generateGlobalVars, configuration === null)
//		}
//	}
//	private def String generateGlobalVars() '''
//		«generateXMLStart»
//		«generateXMLEnd(globVarList)»
//	'''
	private def String generateSingleXMLFile() '''
		«generateXMLStart»
		«FOR c : programs»
			«c.generateProgram»
		«ENDFOR»
		«IF !globVarList.list.empty»
			«globVarList.generateXMLEndWithGlobalVars»
		«ELSE»
			«generateXMLEnd»
		«ENDIF»
	'''

	private def void preparePrograms() {
		if (configuration === null) {
			return
		}
		configuration.resources.stream.map([res|res.resStatement.programConfs]).flatMap([res|res.stream]).
			forEach([ programConf |
				if (programConf.args !== null) {
					val programConfName = programConf.name.capitalizeFirst
					val programGen = programs.stream.filter([x|x.name == programConfName]).findFirst.get
					programConf.args.elements.stream.forEach([ confElement |
						if (confElement instanceof TemplateProcessConfElement) {
							val process = confElement.process.copy
							process.name = confElement.name.capitalizeFirst
							confElement.args.elements.stream.forEach([e|e.changeAllVars(process)])
							programGen.addProcess(process, confElement.active)
						} else if (confElement instanceof AttachVariableConfElement) {
							confElement.changeAllVars(programGen.EObject)
						}
					])
				}
			])
	}

	def void changeAllVars(AttachVariableConfElement element, EObject root) {
		changeAllVars(element.programVar, element.attVar, element.const, root)
	}

	def void changeAllVars(TemplateProcessAttachVariableConfElement element, EObject root) {
		changeAllVars(element.programVar, element.attVar, element.const, root)
	}

	def void changeAllVars(Variable programVar, Variable attVar, Constant const, EObject root) {
		root.getAllContentsOfType(PrimaryExpression).stream.filter([ v |
			(v.variable !== null) && (v.variable.name == programVar.name)
		]).forEach([ v |
			if (attVar !== null) {
				v.variable = attVar as SymbolicVariable
			} else {
				v.variable = null
				v.const = const.copy
			}
		])
		root.getAllContentsOfType(AssignmentStatement).stream.filter([ v |
			(v.variable !== null) && (v.variable.name == programVar.name)
		]).forEach([ v |
			v.variable = attVar as SymbolicVariable
		])
		root.getAllContentsOfType(ForStatement).stream.filter([v|v.variable.name == programVar.name]).forEach([ v |
			v.variable = attVar as SymbolicVariable
		])
		root.getAllContentsOfType(ArrayVariable).stream.filter([v|v.variable.name == programVar.name]).forEach([ v |
			v.variable = attVar as SymbolicVariable
		])
		root.getAllContentsOfType(TimeoutStatement).stream.filter([ v |
			(v.variable !== null) && (v.variable.name == programVar.name)
		]).forEach([ v |
			v.variable = attVar as SymbolicVariable
		])
		root.getAllContentsOfType(ProcessStatements).stream.filter([ v |
			(v.process !== null) && (v.process.name == programVar.name)
		]).forEach([ v |
			v.process.name = attVar.name.capitalizeFirst
		])
		root.getAllContentsOfType(ProcessStatusExpression).stream.filter([ v |
			(v.process !== null) && (v.process.name == programVar.name)
		]).forEach([ v |
			v.process.name = attVar.name.capitalizeFirst
		])
	}

	private def String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1)
	}

}
