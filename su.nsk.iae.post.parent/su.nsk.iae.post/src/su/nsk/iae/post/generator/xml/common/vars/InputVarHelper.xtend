package su.nsk.iae.post.generator.xml.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.InputVarDeclaration

class InputVarHelper extends VarHelper {
	
	new() {
		varType = "inputVars"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof InputVarDeclaration) {
			parseSimpleVar(varDecl.vars, false)
		}
	}
	
}