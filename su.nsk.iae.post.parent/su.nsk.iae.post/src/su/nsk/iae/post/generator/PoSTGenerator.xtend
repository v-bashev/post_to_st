package su.nsk.iae.post.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.poST.Model
import java.util.List
import su.nsk.iae.post.generator.st.ProgramGenerator
import java.util.LinkedList

class PoSTGenerator extends AbstractGenerator {
	
	List<ProgramGenerator> programs = new LinkedList
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val model = input.allContents.toIterable.filter(Model).get(0)
		for (p : model.programs) {
			programs.add(new ProgramGenerator(p))
		}
	}

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		for (p : programs) {
			p.generate(fsa, "")
		}
	}
}
