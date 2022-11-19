package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.generator.st.common.vars.data.VarData
import su.nsk.iae.post.poST.GlobalVarDeclaration
import su.nsk.iae.post.poST.GlobalVarInitDeclaration

class GlobalVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_GLOBAL"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof GlobalVarDeclaration) {
			parseDirectVars(varDecl.varsAs, pref)
			parseSimpleVar(varDecl.varsSimple, pref, varDecl.const)
		}
	}
	
	private def void parseDirectVars(EList<GlobalVarInitDeclaration> varList, String pref) {
		for (v : varList) {
			val type = v.type
			for (e : v.varList.vars) {
				listDecl.add(new VarData(e.name, type, null, false))
			}
		}
	}
	
}