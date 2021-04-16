package su.nsk.iae.post.generator.xml.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.InputOutputVarDeclaration

class InputOutputVarHelper extends VarHelper {
	
	new() {
		varType = "globalVars"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof InputOutputVarDeclaration) {
			parseSimpleVar(varDecl.vars)
		}
	}
	
}