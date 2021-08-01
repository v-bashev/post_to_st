package su.nsk.iae.post.generator.plcopen.xml.common.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.function.Function
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData
import su.nsk.iae.post.poST.AddExpression
import su.nsk.iae.post.poST.AddOperator
import su.nsk.iae.post.poST.AndExpression
import su.nsk.iae.post.poST.ArrayVariable
import su.nsk.iae.post.poST.CompExpression
import su.nsk.iae.post.poST.CompOperator
import su.nsk.iae.post.poST.Constant
import su.nsk.iae.post.poST.EquExpression
import su.nsk.iae.post.poST.EquOperator
import su.nsk.iae.post.poST.Expression
import su.nsk.iae.post.poST.IntegerLiteral
import su.nsk.iae.post.poST.MulExpression
import su.nsk.iae.post.poST.MulOperator
import su.nsk.iae.post.poST.PowerExpression
import su.nsk.iae.post.poST.PrimaryExpression
import su.nsk.iae.post.poST.ProcessStatusExpression
import su.nsk.iae.post.poST.RealLiteral
import su.nsk.iae.post.poST.SignedInteger
import su.nsk.iae.post.poST.SymbolicVariable
import su.nsk.iae.post.poST.UnaryExpression
import su.nsk.iae.post.poST.UnaryOperator
import su.nsk.iae.post.poST.XorExpression

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
		return generateXMLEndWithGlobalVars(null)
	}
	
	static def String generateXMLEndWithGlobalVars(VarHelper globalVars) '''
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
	
	static def String generateExpression(Expression exp) {
		return generateExpression(exp, null, null, null)
	}
	
	static def String generateExpression(Expression exp, 
		Function<SymbolicVariable, String> gVar,
		Function<ArrayVariable, String> gArray,
		Function<ProcessStatusExpression, String> gPStatus) {
		switch exp {
			PrimaryExpression: {
				if (exp.const !== null) {
					return exp.const.generateConstant
				} else if (exp.variable !== null) {
					if (gVar !== null) {
						return gVar.apply(exp.variable)
					}
					return exp.variable.name
				} else if (exp.array !== null) {
					if (gArray !== null) {
						return gArray.apply(exp.array)
					}
					return '''«exp.array.variable.name»[«exp.array.index.generateExpression(gVar, gArray, gPStatus)»]'''
				} else if (exp.procStatus !== null) {
					if (gPStatus !== null) {
						return gPStatus.apply(exp.procStatus)
					}
					return ''''''
				} else {
					return '''(«exp.nestExpr.generateExpression(gVar, gArray, gPStatus)»)'''
				}
			}
			UnaryExpression:
				return '''«IF exp.unOp == UnaryOperator.NOT»NOT «ELSEIF exp.unOp == UnaryOperator.UNMINUS»-«ENDIF»«exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			PowerExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» ** «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			MulExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» «exp.mulOp.generateMulOperators» «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			AddExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» «IF exp.addOp == AddOperator.PLUS»+«ELSE»-«ENDIF» «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			EquExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» «exp.equOp.generateEquOperators» «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			CompExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» «IF exp.compOp == CompOperator.EQUAL»=«ELSE»<>«ENDIF» «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			AndExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» AND «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			XorExpression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» XOR «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
			Expression:
				return '''«exp.left.generateExpression(gVar, gArray, gPStatus)» OR «exp.right.generateExpression(gVar, gArray, gPStatus)»'''
		}
	}
	
	private static def String generateEquOperators(EquOperator op) {
		switch op {
			case EquOperator.LESS:
				return '''<'''
			case EquOperator.LESS_EQU:
				return '''<='''
			case EquOperator.GREATER:
				return '''>'''
			case EquOperator.GREATER_EQU:
				return '''>='''
		}
	}
	
	private static def String generateMulOperators(MulOperator op) {
		switch op {
			case MulOperator.MUL:
				return '''*'''
			case MulOperator.DIV:
				return '''/'''
			case MulOperator.MOD:
				return '''MOD'''
		}
	}
	
}
