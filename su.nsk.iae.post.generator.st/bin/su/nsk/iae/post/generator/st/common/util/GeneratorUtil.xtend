package su.nsk.iae.post.generator.st.common.util

import java.util.function.Function
import su.nsk.iae.post.generator.st.common.ProcessGenerator
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.common.vars.data.VarData
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
import su.nsk.iae.post.poST.Process
import su.nsk.iae.post.poST.ProcessStatusExpression
import su.nsk.iae.post.poST.RealLiteral
import su.nsk.iae.post.poST.SignedInteger
import su.nsk.iae.post.poST.SymbolicVariable
import su.nsk.iae.post.poST.UnaryExpression
import su.nsk.iae.post.poST.UnaryOperator
import su.nsk.iae.post.poST.XorExpression
import su.nsk.iae.post.poST.ParamAssignmentElements
import su.nsk.iae.post.poST.ParamAssignment
import su.nsk.iae.post.poST.AssignmentType
import java.util.stream.Collectors

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
	
	static def String generateEnumName(Process process) {
		return '''_g_p_«process.name»_state'''
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
			return ''' := [«v.arraValues.join(', ')»]'''	
		}
		return ''' := «v.value»'''
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
				} else if (exp.funCall !== null) {
					return '''«exp.funCall.function.name»(«exp.funCall.args.generateParamAssignmentElements([x | x.generateExpression(gVar, gArray, gPStatus)])»)'''
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
	
	static def String generateParamAssignmentElements(ParamAssignmentElements elements) {
		return generateParamAssignmentElements(elements, [x | x.generateExpression])
	}
	
	static def String generateParamAssignmentElements(ParamAssignmentElements elements, Function<Expression, String> gExp) {
		return elements.elements.stream.map([x | x.generateParamAssignment(gExp)]).collect(Collectors.toList).join(", ")
	}
	
	private static def String generateParamAssignment(ParamAssignment ele, Function<Expression, String> gExp) {
		return '''«ele.variable.name» «IF ele.assig == AssignmentType.IN»:=«ELSE»=>«ENDIF» «gExp.apply(ele.value)»'''
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
