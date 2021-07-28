package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.TempVarDeclaration

class TempVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_TEMP"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof TempVarDeclaration) {
			parseSimpleVar(varDecl.vars, pref)
		}
	}
	
}