package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.VarDeclaration

class SimpleVarHelper extends VarHelper {
	
	new() {
		varType = "VAR"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof VarDeclaration) {
			parseSimpleVar(varDecl.vars, pref, varDecl.const)
		}
	}
	
}