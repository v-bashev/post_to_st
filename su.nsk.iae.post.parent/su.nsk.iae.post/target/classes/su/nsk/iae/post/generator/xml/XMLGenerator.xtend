package su.nsk.iae.post.generator.xml

import su.nsk.iae.post.generator.IpoSTGenerator
import su.nsk.iae.post.poST.Model
import org.eclipse.xtext.generator.IFileSystemAccess2
import java.util.List
import java.util.LinkedList
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper

class XMLGenerator implements IpoSTGenerator {
	
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
	
	override generateSingleFile(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»poST_code.xml''', generateXML)
	}
	
	override generateMultipleFiles(IFileSystemAccess2 fsa, String path) {
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