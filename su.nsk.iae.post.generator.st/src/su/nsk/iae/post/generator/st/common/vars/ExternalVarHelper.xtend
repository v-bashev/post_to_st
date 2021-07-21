package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.generator.st.common.vars.data.VarData
import su.nsk.iae.post.poST.ExternalVarDeclaration
import su.nsk.iae.post.poST.ExternalVarInitDeclaration

class ExternalVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_EXTERNAL"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof ExternalVarDeclaration) {
			parseExternVar(varDecl.vars, pref, varDecl.const)
		}
	}
	
	private def void parseExternVar(EList<ExternalVarInitDeclaration> varList, String pref, boolean isConst) {
		for (v : varList) {
			val type = v.type
			for (e : v.varList.vars) {
				listDecl.add(new VarData(pref + e.name, type, null, isConst))
			}
		}
	}
	
}