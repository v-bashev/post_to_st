package su.nsk.iae.post.generator.xml.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.OutputVarDeclaration

class OutputVarHelper extends VarHelper {
	
	new() {
		varType = "outputVars"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof OutputVarDeclaration) {
			parseSimpleVar(varDecl.vars)
		}
	}
}