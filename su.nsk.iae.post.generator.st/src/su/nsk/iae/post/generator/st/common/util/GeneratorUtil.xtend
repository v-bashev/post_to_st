package su.nsk.iae.post.generator.st.common.util

import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.common.vars.data.VarData
import su.nsk.iae.post.poST.Constant
import su.nsk.iae.post.poST.IntegerLiteral
import su.nsk.iae.post.poST.Process
import su.nsk.iae.post.poST.RealLiteral
import su.nsk.iae.post.poST.SignedInteger

class GeneratorUtil {
	
	static def String generateStopConstant() {
		return '''_STOP'''
	}
	
	static def String generateErrorConstant() {
		return '''_ERROR'''
	}
	
	static def String generateGlobalTime() {
		return '''_global_time'''
	}
	
	static def String generateVarName(String process, String variable) {
		return '''_p_«process»_v_«variable»'''
	}
	
	static def String generateVarName(ProcessGenerator process, String variable) {
		return '''_p_«process.name»_v_«variable»'''
	}
	
	static def String generateTimeoutName(ProcessGenerator process) {
		return '''_g_p_«process.name»_time'''
	}
	
	static def String generateEnumName(ProcessGenerator process) {
		return '''_g_p_«process.name»_state'''
	}
	
	static def String generateEnumStateConstant(ProcessGenerator process, String name) {
		return '''_P_«process.name.toUpperCase»_S_«name.toUpperCase»'''
	}
	
	static def String generateConstant(Constant constant) {
		if (constant.num !== null) {
			val num = constant.num
			if (num instanceof IntegerLiteral) {
				return '''«IF num.type !== null»«num.type»#«ENDIF»«num.value.generateSignedInteger»'''
			}
			val dNum = num as RealLiteral
			return '''«IF dNum.type !== null»«dNum.type»#«ENDIF»«IF dNum.RSig»-«ENDIF»«dNum.value»'''
		}
		if (constant.time !== null) {
			return '''T#«constant.time.interval»'''
		}
		return constant.oth
	}
	
	static def String generateSignedInteger(SignedInteger sint) {
		return '''«IF sint.ISig»-«ENDIF»«sint.value»''' 
	}
	
	static def String generateVars(VarHelper helper) '''
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
	
	private static def String generateSingleDeclaration(VarData data) {
		return '''«data.name» : «data.type»'''
	}
	
	private static def String generateValue(VarData v) {
		if ((v.value === null) && (v.arraValues === null)) {
			return ''''''
		}
		if (v.array) {
			return ''' := [«v.arraValues.map[it].join(', ')»]'''	
		}
		return ''' := «v.value»'''
	}
	
}
