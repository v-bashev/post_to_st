package su.nsk.iae.post.generator.plcopen.xml.common

import java.util.Arrays
import java.util.List
import su.nsk.iae.post.generator.plcopen.xml.common.statement.AssignmentStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.CaseStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.ErrorProcessStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.ExitStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.FBInvocationGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.ForStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.IStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.IfStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.RepeatStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.ResetTimerStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.SetStateStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.StartProcessStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.StopProcessStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.SubprogramControlStatementGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.statement.WhileStatementGenerator
import su.nsk.iae.post.poST.ArrayVariable
import su.nsk.iae.post.poST.Expression
import su.nsk.iae.post.poST.ParamAssignmentElements
import su.nsk.iae.post.poST.ProcessStatusExpression
import su.nsk.iae.post.poST.Statement
import su.nsk.iae.post.poST.StatementList
import su.nsk.iae.post.poST.SymbolicVariable

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

class StatementListGenerator {
	
	ProgramGenerator program
	ProcessGenerator process
	StateGenerator state
	List<IStatementGenerator> statementGenerators
	
	new(ProgramGenerator program, ProcessGenerator process, StateGenerator state) {
		this.program = program
		this.process = process
		this.state = state
		statementGenerators = initStatementGenerators
	}
	
	def String generateStatementList(StatementList statementList) '''
		«FOR s : statementList.statements»
			«s.generateStatement»
		«ENDFOR»
	'''
	
	def String generateStatement(Statement statement) {
		for (sg : statementGenerators) {
			if (sg.checkStatement(statement)) {
				return sg.generateStatement(statement)
			}
		}
		return ''''''
	}
	
	def String generateExpression(Expression exp) {
		return generateExpression(exp, [x | x.generateVar], [x | x.generateArray], [x | x.generateProcessStatus])
	}
	
	def String generateParamAssignmentElements(ParamAssignmentElements elements) {
		return generateParamAssignmentElements(elements, [x | x.generateExpression])
	}
	
	def String generateVar(SymbolicVariable varName) {
		if (process.containsVar(varName.name)) {
			return process.generateVarName(varName.name)
		}
		return varName.name
	}
	
	def String generateArray(ArrayVariable varDecl) {
		return '''«varDecl.variable.generateVar»[«varDecl.index.generateExpression»]'''
	}
	
	def String generateProcessStatus(ProcessStatusExpression exp) {
		if (exp.active) {
			return '''((«program.generateProcessEnum(exp.process.name)» &lt;&gt; «generateStopConstant») AND («program.generateProcessEnum(exp.process.name)» &lt;&gt; «generateErrorConstant»))'''
		} else if (exp.inactive) {
			return '''((«program.generateProcessEnum(exp.process.name)» = «generateStopConstant») OR («program.generateProcessEnum(exp.process.name)» = «generateErrorConstant»))'''
		} else if (exp.stop) {
			return '''(«program.generateProcessEnum(exp.process.name)» = «generateStopConstant»)'''
		}
		return '''(«program.generateProcessEnum(exp.process.name)» = «generateErrorConstant»)'''
	}
	
	private def initStatementGenerators() {
		return Arrays.asList(
			new AssignmentStatementGenerator(program, process, state, this),
			new IfStatementGenerator(program, process, state, this),
			new CaseStatementGenerator(program, process, state, this),
			new ForStatementGenerator(program, process, state, this),
			new WhileStatementGenerator(program, process, state, this),
			new RepeatStatementGenerator(program, process, state, this),
			new FBInvocationGenerator(program, process, state, this),
			new StartProcessStatementGenerator(program, process, state, this),
			new StopProcessStatementGenerator(program, process, state, this),
			new ErrorProcessStatementGenerator(program, process, state, this),
			new SetStateStatementGenerator(program, process, state, this),
			new ResetTimerStatementGenerator(program, process, state, this),
			new SubprogramControlStatementGenerator(program, process, state, this),
			new ExitStatementGenerator(program, process, state, this)
		)
	}
	
}