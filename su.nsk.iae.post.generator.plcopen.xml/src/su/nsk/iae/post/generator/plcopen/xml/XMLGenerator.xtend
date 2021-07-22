package su.nsk.iae.post.generator.plcopen.xml

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.generator.IPoSTGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.ProgramGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.generator.plcopen.xml.configuration.ConfigurationGenerator
import su.nsk.iae.post.poST.Model
import su.nsk.iae.post.poST.TemplateProcessConfElement

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

class XMLGenerator implements IPoSTGenerator {
	
	ConfigurationGenerator configuration
	VarHelper globVarList = new GlobalVarHelper
	List<ProgramGenerator> programs = new LinkedList
	
	override setModel(Model model) {
		configuration = new ConfigurationGenerator(model.conf)
		
		globVarList.clear()
		programs.clear()
		
		model.globVars.stream.forEach([v | globVarList.add(v)])
		model.programs.stream.forEach([p | programs.add(new ProgramPOUGenerator(p))])
		model.fbs.stream.forEach([fb | programs.add(new FunctionBlockPOUGenerator(fb))])
	}
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		//preparePrograms()
	}
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		generateSingleFile(fsa, "")
	}
	
	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {}
	
	private def void generateSingleFile(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»poST_code.xml''', generateXML)
	}
	
//	private def void generateMultipleFiles(IFileSystemAccess2 fsa, String path) {
//		for (p : programs) {
//			p.generate(fsa, path)
//		}
//		if (!globVarList.list.empty) {
//			fsa.generateFile('''«path»GVL.xml''', generateGlobalVars)
//		}
//	}

//	private def String generateGlobalVars() '''
//		«generateXMLStart»
//		«generateXMLEnd(globVarList)»
//	'''
	
	private def String generateXML() '''
		«generateXMLStart»
		«FOR c : programs»
			«c.generateProgram»
		«ENDFOR»
		«IF !globVarList.list.empty»
			«generateXMLEnd(globVarList)»
		«ELSE»
			«generateXMLEnd»
		«ENDIF»
	'''

	private def void preparePrograms() {
		configuration.resources.stream.map([res | res.resStatement.programConfs]).flatMap([res | res.stream]).forEach([programConf |
			programConf.args.elements.stream.forEach([processConf |
				if (processConf instanceof TemplateProcessConfElement) {
					val programGen = programs.stream.filter([x | x.name == programConf.program.name]).findFirst().get()
					val process = processConf.process.copy()
					process.name = processConf.name
					
					programGen.addProcess(process)
				}
			])
		])
	}
	
}
