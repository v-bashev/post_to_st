package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.IpoSTGenerator
import su.nsk.iae.post.poST.Model
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.util.List
import java.util.LinkedList
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper

class STGenerator implements IpoSTGenerator {
	
	VarHelper globVarList = new GlobalVarHelper
	List<CodeGenerator> codes = new LinkedList
	
	override setModel(Model model) {
		globVarList.clear()
		codes.clear()
		for (v : model.globVars) {
			globVarList.add(v)
		}
		for (p : model.programs) {
			codes.add(new ProgramGenerator(p))
		}
		for (fb : model.fbs) {
			codes.add(new FBGenerator(fb))
		}
	}
	
	override generateSingleFile(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»poST_code.st''', generateSingleFileBody)
	}
	
	override generateMultipleFiles(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»GVL.st''', CodeGenerator.generateVar(globVarList))
		for (c : codes) {
			c.generate(fsa, path)
		}
	}
	
	private def String generateSingleFileBody() '''
		«CodeGenerator.generateVar(globVarList)»
		«FOR c : codes»
			«c.generateCode»
			
		«ENDFOR»
	'''
	
}