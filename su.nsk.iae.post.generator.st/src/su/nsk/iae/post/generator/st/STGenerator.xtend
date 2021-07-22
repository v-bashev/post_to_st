package su.nsk.iae.post.generator.st

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.generator.IPoSTGenerator
import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.configuration.ConfigurationGenerator
import su.nsk.iae.post.poST.Model
import su.nsk.iae.post.poST.TemplateProcessConfElement

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
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
				program.name = programConf.name
				programs.add(new ProgramPOUGenerator(program))
			])
			return
		}
		model.programs.stream.forEach([p | programs.add(new ProgramPOUGenerator(p))])
		model.fbs.stream.forEach([fb | programs.add(new FunctionBlockPOUGenerator(fb))])
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
			programConf.args.elements.stream.forEach([processConf |
				if (processConf instanceof TemplateProcessConfElement) {
					val programGen = programs.stream.filter([x | x.name == programConf.name]).findFirst().get()
					val process = processConf.process.copy()
					process.name = processConf.name
					
					programGen.addProcess(process)
				}
			])
		])
	}
	
}
