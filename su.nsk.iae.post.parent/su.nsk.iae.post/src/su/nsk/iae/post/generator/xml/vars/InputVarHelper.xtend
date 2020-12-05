package su.nsk.iae.post.generator.xml.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.InputVarDeclaration

class InputVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_INPUT"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof InputVarDeclaration) {
			parseSimpleVar(varDecl.vars, false)
		}
	}
	
}