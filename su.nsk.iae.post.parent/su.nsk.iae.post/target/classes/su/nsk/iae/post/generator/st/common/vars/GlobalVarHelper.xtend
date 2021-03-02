package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.GlobalVarDeclaration
import org.eclipse.emf.common.util.EList
import su.nsk.iae.post.poST.GlobalVarInitDeclaration
import su.nsk.iae.post.generator.st.common.vars.data.VarData

class GlobalVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_GLOBAL"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof GlobalVarDeclaration) {
			parseDirectVars(varDecl.varsAs)
			parseSimpleVar(varDecl.varsSimple, varDecl.const)
		}
	}
	
	private def void parseDirectVars(EList<GlobalVarInitDeclaration> varList) {
		for (v : varList) {
			val type = v.type
			for (e : v.varList.vars) {
				listDecl.add(new VarData(e.name, type, null, false))
			}
		}
	}
	
}