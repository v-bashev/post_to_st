package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.IpoSTGenerator
import su.nsk.iae.post.poST.Model
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.util.List
import java.util.LinkedList

class STGenerator implements IpoSTGenerator {
	
	List<CodeGenerator> codes = new LinkedList
	
	override setModel(Model model) {
		codes.clear
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
		for (c : codes) {
			c.generate(fsa, path)
		}
	}
	
	private def String generateSingleFileBody() '''
		«FOR c : codes»
			«c.generateCode»
			
		«ENDFOR»
	'''
	
}