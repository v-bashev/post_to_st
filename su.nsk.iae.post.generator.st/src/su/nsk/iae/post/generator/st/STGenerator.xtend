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
import su.nsk.iae.post.poST.Model

class STGenerator implements IPoSTGenerator {
	
	VarHelper globVarList = new GlobalVarHelper
	List<ProgramGenerator> programs = new LinkedList
	
	override setModel(Model model) {
		globVarList.clear()
		programs.clear()
		for (v : model.globVars) {
			globVarList.add(v)
		}
		for (p : model.programs) {
			programs.add(new ProgramPOUGenerator(p))
		}
		for (fb : model.fbs) {
			programs.add(new FunctionBlockPOUGenerator(fb))
		}
	}
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {}
	
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
		«ProgramGenerator.generateVars(globVarList)»
		«FOR c : programs»
			«c.generateProgram»
			
		«ENDFOR»
	'''
	
}