package su.nsk.iae.post.generator.st.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.VarDeclaration

class SimpleVarHelper extends VarHelper {
	
	new() {
		varType = "VAR"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof VarDeclaration) {
			parseSimpleVar(varDecl.vars, varDecl.const)
		}
	}
	
}