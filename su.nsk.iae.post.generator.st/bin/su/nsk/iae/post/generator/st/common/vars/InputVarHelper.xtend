package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.InputVarDeclaration

class InputVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_INPUT"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof InputVarDeclaration) {
			parseSimpleVar(varDecl.vars, pref)
		}
	}
	
}