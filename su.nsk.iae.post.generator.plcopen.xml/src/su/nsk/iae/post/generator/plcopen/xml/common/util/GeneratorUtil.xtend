package su.nsk.iae.post.generator.plcopen.xml.common.util

import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData
import su.nsk.iae.post.poST.Constant
import su.nsk.iae.post.poST.IntegerLiteral
import su.nsk.iae.post.poST.Process
import su.nsk.iae.post.poST.RealLiteral
import su.nsk.iae.post.poST.SignedInteger
import java.util.Calendar
import java.text.SimpleDateFormat

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
	
	static def String generateVarName(Process process, String variable) {
		return '''_p_«process.name»_v_«variable»'''
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
	
	static def String generateVars(VarHelper varHelper) {
		return generateVars(varHelper, null)
	}
	
	static def String generateVars(VarHelper varHelper, String name) '''
		«IF !varHelper.list.empty»
			«IF varHelper.hasConstant»
				<«varHelper.type»«IF name !== null» name="«name»"«ENDIF» constant="true">
					«FOR v : varHelper.list»
						«IF v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</«varHelper.type»>
			«ENDIF»
			«IF varHelper.hasNonConstant»
				<«varHelper.type»«IF name !== null» name="«name»"«ENDIF»>
					«FOR v : varHelper.list»
						«IF !v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</«varHelper.type»>
			«ENDIF»
		«ENDIF»
	'''
	
	private static def String generateSingleDeclaration(VarData data) '''
		<variable name="«data.name»">
			<type>
				<«data.type» />
			</type>
			«IF data.value !== null»
				<initialValue>
					<simpleValue value="«data.value»" />
				</initialValue>
			«ENDIF»
		</variable>
	'''
	
	static def String generateXMLStart() '''
		<?xml version="1.0" encoding="utf-8"?>
		<project xmlns="http://www.plcopen.org/xml/tc6_0200">
			<fileHeader companyName="" productName="CODESYS" productVersion="CODESYS V3.5 SP11" creationDateTime="«generateCurrentTime»" />
			<contentHeader name="poST.project">
				 <coordinateInfo>
					<fbd>
						<scaling x="1" y="1" />
					</fbd>
					<ld>
						<scaling x="1" y="1" />
					</ld>
					<sfc>
						<scaling x="1" y="1" />
					</sfc>
				</coordinateInfo>
				<addData>
					<data name="http://www.3s-software.com/plcopenxml/projectinformation" handleUnknown="implementation">
						<ProjectInformation />
					</data>
				</addData>
			</contentHeader>
			<types>
				<dataTypes />
				<pous>
	'''
	
	static def String generateXMLEnd() {
		return generateXMLEnd(null)
	}
	
	static def String generateXMLEnd(VarHelper globalVars) '''
				</pous>
			</types>
			<instances>
				<configurations />
			</instances>
			«IF globalVars !== null»
				<addData>
					<data name="http://www.3s-software.com/plcopenxml/globalvars" handleUnknown="implementation">
						«generateVars(globalVars, "GVL")»
					</data>
				</addData>
			«ENDIF»
		</project>
	'''
	
	private static def String generateCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(Calendar.instance.time)
	}
	
}
