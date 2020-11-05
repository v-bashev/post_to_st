package su.nsk.iae.post.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;

import su.nsk.iae.post.poST.AssignmentStatement;
import su.nsk.iae.post.poST.CaseElement;
import su.nsk.iae.post.poST.CaseStatement;
import su.nsk.iae.post.poST.ErrorProcessStatement;
import su.nsk.iae.post.poST.ExternalVarDeclaration;
import su.nsk.iae.post.poST.ExternalVarInitDeclaration;
import su.nsk.iae.post.poST.IfStatement;
import su.nsk.iae.post.poST.InputOutputVarDeclaration;
import su.nsk.iae.post.poST.InputVarDeclaration;
import su.nsk.iae.post.poST.IterationStatement;
import su.nsk.iae.post.poST.OutputVarDeclaration;
import su.nsk.iae.post.poST.PoSTPackage;
import su.nsk.iae.post.poST.PrimaryExpression;
import su.nsk.iae.post.poST.Process;
import su.nsk.iae.post.poST.ProcessStatements;
import su.nsk.iae.post.poST.ProcessStatusExpression;
import su.nsk.iae.post.poST.Program;
import su.nsk.iae.post.poST.SelectionStatement;
import su.nsk.iae.post.poST.SetStateStatement;
import su.nsk.iae.post.poST.StartProcessStatement;
import su.nsk.iae.post.poST.Statement;
import su.nsk.iae.post.poST.StatementList;
import su.nsk.iae.post.poST.StopProcessStatement;
import su.nsk.iae.post.poST.SymbolicVariable;
import su.nsk.iae.post.poST.TempVarDeclaration;
import su.nsk.iae.post.poST.TimeLiteral;
import su.nsk.iae.post.poST.TimeoutStatement;
import su.nsk.iae.post.poST.VarDeclaration;
import su.nsk.iae.post.poST.VarInitDeclaration;

public class PoSTValidator extends AbstractPoSTValidator {

	/* ======================= START Variables Validator ======================= */

	@Check
	public void checkVariableNameConflicts(SymbolicVariable varName) {
		Process process = EcoreUtil2.getContainerOfType(varName, Process.class);
		if ((process != null) && checkVariableNameConflictsInProcess(process, varName)) {
			if (checkVariableNameConflictsInProcess(process, varName)) {
				error("Var conflict: Process already has a variable with this name",
						PoSTPackage.eINSTANCE.getSymbolicVariable_Name());
				return;
			}
		}
		Program program = EcoreUtil2.getContainerOfType(varName, Program.class);
		if (program != null) {
			if (checkVariableNameConflictsInProgram(program, varName)) {
				error("Var conflict: Program already has a variable with this name",
						PoSTPackage.eINSTANCE.getSymbolicVariable_Name());
				return;
			}
			return;
		}
	}

	@Check
	public void checkNeverUseVariable(SymbolicVariable varName) {
		Program program = EcoreUtil2.getContainerOfType(varName, Program.class);
		if ((program != null) && !hasCrossReferences(program, varName)) {
			warning("Variable is never use", PoSTPackage.eINSTANCE.getSymbolicVariable_Name());
		}

	}

	@Check
	public void checkAssignmentStatement(AssignmentStatement statement) {
		SymbolicVariable varName = statement.getVariable();
		if (checkVariableNameConflictsInProcess(EcoreUtil2.getContainerOfType(statement, Process.class), varName)
				|| checkVariableNameConflictsInProgram(EcoreUtil2.getContainerOfType(statement, Program.class),
						varName)) {
			error("Scope error: Variable is not visible in this process",
					PoSTPackage.eINSTANCE.getAssignmentStatement_Variable());
			return;
		}
		if ((EcoreUtil2.getContainerOfType(varName, InputVarDeclaration.class) != null)) {
			warning("Modification of input varsiable", PoSTPackage.eINSTANCE.getAssignmentStatement_Variable());
			return;
		}
		VarDeclaration inputDecl = EcoreUtil2.getContainerOfType(varName, VarDeclaration.class);
		ExternalVarDeclaration externalDecl = EcoreUtil2.getContainerOfType(varName, ExternalVarDeclaration.class);
		if (((inputDecl != null) && inputDecl.isConst()) || ((externalDecl != null) && externalDecl.isConst())) {
			error("Assignment error: Couldn't modidy constant varsiable",
					PoSTPackage.eINSTANCE.getAssignmentStatement_Variable());
		}
	}

	@Check
	public void chekPrimaryExpression(PrimaryExpression expr) {
		if (expr.getVariable() == null) {
			return;
		}
		SymbolicVariable varName = expr.getVariable();
		if (checkVariableNameConflictsInProcess(EcoreUtil2.getContainerOfType(expr, Process.class), varName)
				|| checkVariableNameConflictsInProgram(EcoreUtil2.getContainerOfType(expr, Program.class), varName)) {
			error("Scope error: Variable is not visible in this process",
					PoSTPackage.eINSTANCE.getPrimaryExpression_Variable());
			return;
		}
	}

	/* ======================= END Variables Validator ======================= */

	/* ======================= START poST Validator ======================= */

	@Check
	public void checkEmptyProgram(Program program) {
		if (program.getProcesses().isEmpty()) {
			error("Statement error: Program can't be empty", PoSTPackage.eINSTANCE.getProcess_Name());
		}
	}

	@Check
	public void checkProcessNameConflicts(Process process) {
		Program program = EcoreUtil2.getContainerOfType(process, Program.class);
		for (Process p : program.getProcesses()) {
			if ((p != process) && p.getName().equals(process.getName())) {
				error("Name error: Process with this name already exists", PoSTPackage.eINSTANCE.getProcess_Name());
			}
		}
	}

	@Check
	public void checkEmptyProcess(Process process) {
		if (process.getStates().isEmpty()) {
			error("Statement error: Process can't be empty", PoSTPackage.eINSTANCE.getProcess_Name());
		}
	}

	@Check
	public void checkProcessUnreachable(Process process) {
		Program program = EcoreUtil2.getContainerOfType(process, Program.class);
		if (program.getProcesses().indexOf(process) == 0) {
			return;
		}
		for (Process p : program.getProcesses()) {
			if (p == process) {
				continue;
			}
			for (StartProcessStatement s : EcoreUtil2.getAllContentsOfType(p, StartProcessStatement.class)) {
				if (s.getProcess() == process) {
					return;
				}
			}
		}
		warning("Process is unreachable", PoSTPackage.eINSTANCE.getProcess_Name());
	}

	@Check
	public void checkStateNameConflicts(su.nsk.iae.post.poST.State state) {
		Process process = EcoreUtil2.getContainerOfType(state, Process.class);
		for (su.nsk.iae.post.poST.State s : process.getStates()) {
			if ((s != state) && s.getName().equals(state.getName())) {
				error("Name error: State with this name already exists", PoSTPackage.eINSTANCE.getState_Name());
			}
		}
	}

	@Check
	public void checkEmptyState(su.nsk.iae.post.poST.State state) {
		if (state.getStatement().getStatements().isEmpty() && state.getTimeout() == null) {
			error("Statement error: State can't be empty", PoSTPackage.eINSTANCE.getState_Name());
		}
	}

	@Check
	public void checkUnreachableState(su.nsk.iae.post.poST.State state) {
		Process process = EcoreUtil2.getContainerOfType(state, Process.class);
		if (process.getStates().indexOf(state) == 0) {
			return;
		}
		for (su.nsk.iae.post.poST.State st : process.getStates()) {
			if (st == state) {
				continue;
			}
			for (SetStateStatement s : EcoreUtil2.getAllContentsOfType(st, SetStateStatement.class)) {
				if (s.getState() == state) {
					return;
				}
			}
		}
		for (SetStateStatement s : EcoreUtil2.getAllContentsOfType(
				process.getStates().get(process.getStates().indexOf(state) - 1), SetStateStatement.class)) {
			if (s.isNext()) {
				return;
			}
		}
		warning("State is unreachable", PoSTPackage.eINSTANCE.getState_Name());
	}

	@Check
	public void checkLoopedState(su.nsk.iae.post.poST.State state) {
		boolean check = containsStatement(state.getStatement(), SetStateStatement.class);
		if (state.getTimeout() != null)
			check |= containsStatement(state.getTimeout().getStatement(), SetStateStatement.class);
		if (!check) {
			check |= containsStatement(state.getTimeout().getStatement(), StopProcessStatement.class);
			if (state.getTimeout() != null)
				check |= containsStatement(state.getTimeout().getStatement(), StopProcessStatement.class);
		}
		if (!check) {
			check |= containsStatement(state.getTimeout().getStatement(), ErrorProcessStatement.class);
			if (state.getTimeout() != null)
				check |= containsStatement(state.getTimeout().getStatement(), ErrorProcessStatement.class);
		}
		if (state.isLooped()) {
			if (check) {
				warning("State mustn't be LOOPED", PoSTPackage.eINSTANCE.getState_Looped());
			}
		} else {
			if (!check) {
				warning("State must be LOOPED", PoSTPackage.eINSTANCE.getState_Name());
			}
		}
	}

	@Check
	public void checkProcessStatusExpression(ProcessStatusExpression expr) {
		Program program = EcoreUtil2.getContainerOfType(expr, Program.class);
		if (!program.getProcesses().contains(expr.getProcess())) {
			error("Name error: No process with this name", PoSTPackage.eINSTANCE.getProcessStatements_Process());
		}
	}

	@Check
	public void checkNextState(SetStateStatement statement) {
		Process process = EcoreUtil2.getContainerOfType(statement, Process.class);
		su.nsk.iae.post.poST.State state = EcoreUtil2.getContainerOfType(statement, su.nsk.iae.post.poST.State.class);
		if (statement.isNext()) {
			if (process.getStates().indexOf(state) + 1 >= process.getStates().size()) {
				error("Invalide statement: No next state in the process",
						PoSTPackage.eINSTANCE.getSetStateStatement_Next());
			}
		} else {
			if (!process.getStates().contains(statement.getState())) {
				error("Invalide statement: No state with this name",
						PoSTPackage.eINSTANCE.getSetStateStatement_State());
			} else if (state.getName().equals(statement.getState().getName())) {
				warning("Useless statement, use RESET TIMER", PoSTPackage.eINSTANCE.getSetStateStatement_State());
			}
		}
	}

	@Check
	public void checkStartProcessStatement(StartProcessStatement statement) {
		if (statement.getProcess() == null) {
			return;
		}
		Program program = EcoreUtil2.getContainerOfType(statement, Program.class);
		if (!program.getProcesses().contains(statement.getProcess())) {
			error("Invalide statement: No process with this name", null);
		}
	}

	@Check
	public void checkStopProcessStatement(StopProcessStatement statement) {
		if (statement.getProcess() == null) {
			return;
		}
		Program program = EcoreUtil2.getContainerOfType(statement, Program.class);
		if (!program.getProcesses().contains(statement.getProcess())) {
			error("Invalide statement: No process with this name", null);
		}
	}

	@Check
	public void checkErrorProcessStatement(ErrorProcessStatement statement) {
		if (statement.getProcess() == null) {
			return;
		}
		Program program = EcoreUtil2.getContainerOfType(statement, Program.class);
		if (!program.getProcesses().contains(statement.getProcess())) {
			error("Invalide statement: No process with this name", null);
		}
	}

	@Check
	public void checkTimeoutStatement(TimeoutStatement statement) {
		if (statement.getStatement().getStatements().isEmpty()) {
			error("Statement error: No reaction on timeout", PoSTPackage.eINSTANCE.getTimeoutStatement_Statement());
		}
	}

	@Check
	public void checkTimeLiteral(TimeLiteral time) {
		String str = time.getInterval().replaceAll("ms", "q");
		long res = 0;
		if (str.contains("d")) {
			res += Integer.valueOf(str.substring(0, str.indexOf("d"))) * 86400000;
			str = str.substring(str.indexOf("d") + 1);
		}
		if (str.contains("h")) {
			res += Integer.valueOf(str.substring(0, str.indexOf("h"))) * 3600000;
			str = str.substring(str.indexOf("h") + 1);
		}
		if (str.contains("m")) {
			res += Integer.valueOf(str.substring(0, str.indexOf("m"))) * 60000;
			str = str.substring(str.indexOf("m") + 1);
		}
		if (str.contains("s")) {
			res += Integer.valueOf(str.substring(0, str.indexOf("s"))) * 1000;
			str = str.substring(str.indexOf("s") + 1);
		}
		if (str.contains("q")) {
			res += Integer.valueOf(str.substring(0, str.indexOf("q")));
		}
		if (res >= 0xFFFFFFFFL) {
			error("Time error: Max value of time is 4294967295 milliseconds or near 50 days",
					PoSTPackage.eINSTANCE.getTimeLiteral_Interval());
		}
	}

	/* ======================= END poST Validator ======================= */

	@Check
	public void checkIfStatement(IfStatement statement) {
		if (statement.getMainStatement().getStatements().isEmpty()) {
			warning("Statement boby is empty", PoSTPackage.eINSTANCE.getIfStatement_MainStatement());
		}
	}

	private boolean hasCrossReferences(EObject context, EObject target) {
		Set<EObject> targetSet = new HashSet<EObject>();
		targetSet.add(target);
		List<EReference> res = new ArrayList<EReference>();
		EcoreUtil2.ElementReferenceAcceptor acceptor = (EObject referrer, EObject referenced, EReference reference,
				int index) -> {
			res.add(reference);
		};
		EcoreUtil2.findCrossReferences(context, targetSet, acceptor);
		return !res.isEmpty();
	}

	private <T extends Statement> boolean containsStatement(StatementList list, Class<T> type) {
		for (Statement s : list.getStatements()) {
			if (type.isInstance(s)) {
				return true;
			}
			boolean tmp = true;
			if (s instanceof IfStatement) {
				tmp &= containsStatement(((IfStatement) s).getMainStatement(), type);
				for (StatementList l : ((IfStatement) s).getElseIfStatements()) {
					tmp &= containsStatement(l, type);
				}
				if (((IfStatement) s).getElseStatement() != null) {
					tmp &= containsStatement(((IfStatement) s).getElseStatement(), type);
				}
				if (tmp) {
					return true;
				}
			}
			if (s instanceof CaseStatement) {
				for (CaseElement e : ((CaseStatement) s).getCaseElements()) {
					tmp &= containsStatement(e.getStatement(), type);
				}
				if (((CaseStatement) s).getElseStatement() != null) {
					tmp &= containsStatement(((CaseStatement) s).getElseStatement(), type);
				}
				if (tmp) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkVariableNameConflictsInProcess(Process process, SymbolicVariable varName) {
		for (VarDeclaration varDecl : process.getProcVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		for (TempVarDeclaration varDecl : process.getProcTempVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkVariableNameConflictsInProgram(Program program, SymbolicVariable varName) {
		for (InputVarDeclaration varDecl : program.getProgInVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		for (OutputVarDeclaration varDecl : program.getProgOutVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		for (InputOutputVarDeclaration varDecl : program.getProgInOutVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		for (ExternalVarDeclaration varDecl : program.getProgExternVars()) {
			if (checkExternalVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		for (VarDeclaration varDecl : program.getProgVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		for (TempVarDeclaration varDecl : program.getProgTempVars()) {
			if (checkVarInitDeclaration(varDecl.getVars(), varName)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkVarInitDeclaration(EList<VarInitDeclaration> decls, SymbolicVariable varName) {
		for (VarInitDeclaration varList : decls) {
			for (SymbolicVariable v : varList.getVarList().getVars()) {
				if ((v != varName) && v.getName().equals(varName.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkExternalVarInitDeclaration(EList<ExternalVarInitDeclaration> decls, SymbolicVariable varName) {
		for (ExternalVarInitDeclaration varList : decls) {
			for (SymbolicVariable v : varList.getVarList().getVars()) {
				if ((v != varName) && v.getName().equals(varName.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}