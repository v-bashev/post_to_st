package su.nsk.iae.post.generator.plcopen.xml

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import su.nsk.iae.post.generator.IPoSTGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.poST.Model

class XMLGenerator implements IPoSTGenerator {
	
	VarHelper globVarList = new GlobalVarHelper
	List<CodeGenerator> codes = new LinkedList
	
	override setModel(Model model) {
		globVarList.clear()
		codes.clear
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
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {}
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		generateSingleFile(fsa, "")
	}
	
	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {}
	
	private def void generateSingleFile(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»poST_code.xml''', generateXML)
	}
	
	private def void generateMultipleFiles(IFileSystemAccess2 fsa, String path) {
		for (c : codes) {
			c.generate(fsa, path)
		}
		if (!globVarList.list.empty) {
			fsa.generateFile('''«path»GVL.xml''', generateGlobalVars)
		}
	}
	
	private def String generateXML() '''
		«CodeGenerator.generateXMLStart»
		«FOR c : codes»
			«c.generateXMLBody»
		«ENDFOR»
		«IF !globVarList.list.empty»
			«CodeGenerator.generateXMLEnd(globVarList)»
		«ELSE»
			«CodeGenerator.generateXMLEnd»
		«ENDIF»
	'''
	
	private def String generateGlobalVars() '''
		«CodeGenerator.generateXMLStart»
		«CodeGenerator.generateXMLEnd(globVarList)»
	'''
	
}