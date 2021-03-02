package su.nsk.iae.post.generator.xml

import su.nsk.iae.post.generator.IpoSTGenerator
import su.nsk.iae.post.poST.Model
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.util.List
import java.util.LinkedList

class XMLGenerator implements IpoSTGenerator {
	
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
	
	override generate(IFileSystemAccess2 fsa, String path) {
		for (c : codes) {
			c.generate(fsa, path)
		}
	}
	
}