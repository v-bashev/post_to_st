package su.nsk.iae.post.generator.st.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.OutputVarDeclaration

class OutputVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_OUTPUT"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof OutputVarDeclaration) {
			parseSimpleVar(varDecl.vars, pref)
		}
	}
}