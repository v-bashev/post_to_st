package su.nsk.iae.post.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.poST.Model
import java.util.List
import java.util.LinkedList
import su.nsk.iae.post.generator.st.CodeGenerator
import su.nsk.iae.post.generator.st.ProgramGenerator
import su.nsk.iae.post.generator.st.FBGenerator

class PoSTGenerator extends AbstractGenerator {
	
	List<CodeGenerator> codes = new LinkedList
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val model = input.allContents.toIterable.filter(Model).get(0)
		for (p : model.programs) {
			codes.add(new ProgramGenerator(p))
		}
		for (fb : model.fbs) {
			codes.add(new FBGenerator(fb))
		}
	}

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		for (c : codes) {
			c.generate(fsa, "")
		}
	}
}
