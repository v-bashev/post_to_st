package su.nsk.iae.post.generator.plcopen.xml.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.InputOutputVarDeclaration

class InputOutputVarHelper extends VarHelper {
	
	new() {
		varType = "inputOutputVars"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof InputOutputVarDeclaration) {
			parseSimpleVar(varDecl.vars, pref)
		}
	}
	
}