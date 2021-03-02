package su.nsk.iae.post.generator.st

import org.eclipse.xtext.generator.IFileSystemAccess2
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.common.vars.data.VarData

class CodeGenerator extends ICodeGenerator {
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«codeName.toLowerCase».st''', generateCode)
	}
	
	protected override String generateCode() '''
		«type» «codeName»

		«inVarList.generateVar»
		«outVarList.generateVar»
		«inOutVarList.generateVar»
		«externalVarList.generateVar»
		«varList.generateVar»
		«tempVarList.generateVar»
		
		«generateGlobalTime» := TIME();
		
		«FOR p : processList»
			«p.generateBody»
			
		«ENDFOR»
		END_«type»
	'''
	
	private def String generateVar(VarHelper helper) '''
		«IF !helper.list.empty»
			«IF helper.hasConstant»
				«helper.type» CONSTANT
					«FOR v : helper.list»
						«IF v.isConstant»
							«v.generateSingleDeclaration»«v.generateValue»;
						«ENDIF»
					«ENDFOR»
				END_VAR
				
			«ENDIF»
			«IF helper.hasNonConstant»
				«helper.type»
					«FOR v : helper.list»
						«IF !v.isConstant»
							«v.generateSingleDeclaration»«v.generateValue»;
						«ENDIF»
					«ENDFOR»
				END_VAR
				
			«ENDIF»
		«ENDIF»
	'''
	private def String generateSingleDeclaration(VarData data) {
		return '''«data.name» : «data.type»'''
	}
	
	private def String generateValue(VarData v) {
		if ((v.value === null) && (v.arraValues === null)) {
			return ''''''
		}
		if (v.array) {
			return ''' := [«v.arraValues.map[it].join(', ')»]'''	
		}
		return ''' := «v.value»'''
	}
	
}