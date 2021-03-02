package su.nsk.iae.post.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.generator.st.STGenerator
import su.nsk.iae.post.generator.xml.XMLGenerator
import su.nsk.iae.post.poST.Model

class PoSTGenerator extends AbstractGenerator {
	
	IpoSTGenerator stGenerator = new STGenerator
	IpoSTGenerator xmlGenerator = new XMLGenerator
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val model = input.allContents.toIterable.filter(Model).get(0)
		stGenerator.model = model
		xmlGenerator.model = model
	}

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		stGenerator.generate(fsa, "st/")
		xmlGenerator.generate(fsa, "xml/")
	}
}
