package su.nsk.iae.post.generator.xml.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.OutputVarDeclaration

class OutputVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_OUTPUT"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof OutputVarDeclaration) {
			parseSimpleVar(varDecl.vars)
		}
	}
}