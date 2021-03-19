package su.nsk.iae.post.generator.st.common

import su.nsk.iae.post.poST.AddExpression
import su.nsk.iae.post.poST.AddOperator
import su.nsk.iae.post.poST.AndExpression
import su.nsk.iae.post.poST.AssignmentStatement
import su.nsk.iae.post.poST.CaseStatement
import su.nsk.iae.post.poST.CompExpression
import su.nsk.iae.post.poST.CompOperator
import su.nsk.iae.post.poST.EquExpression
import su.nsk.iae.post.poST.EquOperator
import su.nsk.iae.post.poST.ErrorProcessStatement
import su.nsk.iae.post.poST.Expression
import su.nsk.iae.post.poST.ForStatement
import su.nsk.iae.post.poST.IfStatement
import su.nsk.iae.post.poST.MulExpression
import su.nsk.iae.post.poST.MulOperator
import su.nsk.iae.post.poST.PowerExpression
import su.nsk.iae.post.poST.PrimaryExpression
import su.nsk.iae.post.poST.RepeatStatement
import su.nsk.iae.post.poST.ResetTimerStatement
import su.nsk.iae.post.poST.SetStateStatement
import su.nsk.iae.post.poST.StartProcessStatement
import su.nsk.iae.post.poST.State
import su.nsk.iae.post.poST.Statement
import su.nsk.iae.post.poST.StatementList
import su.nsk.iae.post.poST.StopProcessStatement
import su.nsk.iae.post.poST.SymbolicVariable
import su.nsk.iae.post.poST.UnaryExpression
import su.nsk.iae.post.poST.WhileStatement
import su.nsk.iae.post.poST.XorExpression
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import su.nsk.iae.post.poST.ProcessStatusExpression
import su.nsk.iae.post.poST.SignedInteger
import su.nsk.iae.post.poST.UnaryOperator
import su.nsk.iae.post.poST.ArrayVariable
import su.nsk.iae.post.generator.st.ICodeGenerator

class StateGenerator {
	
	ICodeGenerator program
	ProcessGenerator process
	State state
	
	new(ICodeGenerator program, ProcessGenerator process, State state) {
		this.program = program
		this.process = process
		this.state = state
	}
	
	def String generateBody() '''
		«state.statement.generateStatementList»
		«IF state.timeout !== null»
			IF («program.generateGlobalTime» - «process.generateTimeoutName») >= «IF state.timeout.variable !== null»«state.timeout.variable.generateVar»«ELSE»«NodeModelUtils.getNode(state.timeout.const).text.trim»«ENDIF» THEN
				«state.timeout.statement.generateStatementList»
			END_IF
		«ENDIF»
	'''
	
	def String getName() {
		return state.name
	}
	
	def boolean hasTimeout() {
		return state.timeout !== null
	}
	
	private def String generateStatementList(StatementList statementList) '''
		«FOR s : statementList.statements»
			«s.generateStatement»
		«ENDFOR»
	'''
	
	private def String generateStatement(Statement s) {
		switch s {
			AssignmentStatement:
				if (s.variable !== null){
					return '''«s.variable.generateVar» := «s.value.generateExpression»;'''
				} else {
					return '''«s.array.generateArray» := «s.value.generateExpression»;'''
				}
			IfStatement:
				return '''
					IF «s.mainCond.generateExpression» THEN
						«s.mainStatement.generateStatementList»
					«IF !s.elseIfCond.empty»
						«FOR i : 0..(s.elseIfCond.size - 1)»
							ELSIF «s.elseIfCond.get(i).generateExpression» THEN
								«s.elseIfStatements.get(i).generateStatementList»
						«ENDFOR»
					«ENDIF»
					«IF s.elseStatement !== null»
						ELSE
							«s.elseStatement.generateStatementList»
					«ENDIF»
					END_IF
				'''
			CaseStatement:
				return '''
					CASE «s.cond.generateExpression» OF
						«FOR e : s.caseElements»
							«FOR c : e.caseList.caseListElement»
								«c.generateSignedInteger»«IF e.caseList.caseListElement.indexOf(c) < e.caseList.caseListElement.size - 1», «ELSE»:«ENDIF»
							«ENDFOR»
								«e.statement.generateStatementList»
						«ENDFOR»
						«IF s.elseStatement !== null»
							ELSE
								«s.elseStatement.generateStatementList»
						«ENDIF»
					END_CASE
				'''
			ForStatement:
				return '''
					FOR «s.variable.generateVar» := «s.forList.start.generateExpression» TO «s.forList.end.generateExpression»«IF s.forList.step !== null» BY «s.forList.step.generateExpression»«ENDIF» DO
						«s.statement.generateStatementList»
					END_FOR
				'''
			WhileStatement:
				return '''
					WHILE «s.cond.generateExpression» DO
						«s.statement.generateStatementList»
					END_WHILE
				'''
			RepeatStatement:
				return '''
					REPEAT
						«s.statement.generateStatementList»
					UNTIL «s.cond.generateExpression» END_REPEAT
				'''
			StartProcessStatement:
				return '''«IF s.process !== null»«program.generateProcessStart(s.process.name)»«ELSE»«process.generateStart»«ENDIF»'''
			StopProcessStatement:
				return '''«IF s.process !== null»«program.generateProcessEnum(s.process.name)»«ELSE»«process.generateEnumName»«ENDIF» := «program.generateStopConstant»;'''
			ErrorProcessStatement:
				return '''«IF s.process !== null»«program.generateProcessEnum(s.process.name)»«ELSE»«process.generateEnumName»«ENDIF» := «program.generateErrorConstant»;'''
			SetStateStatement:
				return '''«IF s.next»«process.generateNextState(this)»«ELSE»«process.generateSetState(s.state.name)»«ENDIF»'''
			ResetTimerStatement:
				return '''
					«process.generateTimeoutName» := «program.generateGlobalTime»;
				'''
		}
		return '''RETURN'''
	}
	
	private def String generateExpression(Expression exp) {
		switch exp {
			PrimaryExpression: {
				if (exp.const !== null) {
					return NodeModelUtils.getNode(exp.const).text.trim
				} else if (exp.variable !== null) {
					return exp.variable.generateVar
				} else if (exp.array !== null) {
					return exp.array.generateArray
				} else if (exp.procStatus !== null) {
					return '''«exp.procStatus.generateProcessStatus»'''
				} else {
					return '''(«exp.nestExpr.generateExpression»)'''
				}
			}
			UnaryExpression:
				return '''«IF exp.unOp == UnaryOperator.NOT»NOT «ELSEIF exp.unOp == UnaryOperator.UNMINUS»-«ENDIF»«exp.right.generateExpression»'''
			PowerExpression:
				return '''«exp.left.generateExpression» ** «exp.right.generateExpression»'''
			MulExpression:
				return '''«exp.left.generateExpression» «exp.mulOp.generateMulOperators» «exp.right.generateExpression»'''
			AddExpression:
				return '''«exp.left.generateExpression» «IF exp.addOp == AddOperator.PLUS»+«ELSE»-«ENDIF» «exp.right.generateExpression»'''
			EquExpression:
				return '''«exp.left.generateExpression» «exp.equOp.generateEquOperators» «exp.right.generateExpression»'''
			CompExpression:
				return '''«exp.left.generateExpression» «IF exp.compOp == CompOperator.EQUAL»=«ELSE»<>«ENDIF» «exp.right.generateExpression»'''
			AndExpression:
				return '''«exp.left.generateExpression» AND «exp.right.generateExpression»'''
			XorExpression:
				return '''«exp.left.generateExpression» XOR «exp.right.generateExpression»'''
			Expression:
				return '''«exp.left.generateExpression» OR «exp.right.generateExpression»'''
				
		}
	}
	
	private def String generateVar(SymbolicVariable varName) {
		if (process.containsVar(varName.name)) {
			return process.getVarName(varName.name)
		}
		return varName.name
	}
	
	private def String generateArray(ArrayVariable varDecl) {
		return '''«varDecl.varName.generateVar»[«varDecl.index.generateExpression»]'''
	}
	
	private def String generateEquOperators(EquOperator op) {
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
	
	private def String generateMulOperators(MulOperator op) {
		switch op {
			case MulOperator.MUL:
				return '''*'''
			case MulOperator.DIV:
				return '''/'''
			case MulOperator.MOD:
				return '''MOD'''
		}
	}
	
	private def String generateProcessStatus(ProcessStatusExpression exp) {
		if (exp.active) {
			return '''((«program.generateProcessEnum(exp.process.name)» <> «program.generateStopConstant») AND («program.generateProcessEnum(exp.process.name)» <> «program.generateErrorConstant»))'''
		} else if (exp.inactive) {
			return '''((«program.generateProcessEnum(exp.process.name)» = «program.generateStopConstant») OR («program.generateProcessEnum(exp.process.name)» = «program.generateErrorConstant»))'''
		} else if (exp.stop) {
			return '''(«program.generateProcessEnum(exp.process.name)» = «program.generateStopConstant»)'''
		}
		return '''(«program.generateProcessEnum(exp.process.name)» = «program.generateErrorConstant»)'''
	}
	
	private def String generateSignedInteger(SignedInteger sint) {
		return '''«IF sint.ISig»-«ENDIF»«sint.value»''' 
	}
	
}