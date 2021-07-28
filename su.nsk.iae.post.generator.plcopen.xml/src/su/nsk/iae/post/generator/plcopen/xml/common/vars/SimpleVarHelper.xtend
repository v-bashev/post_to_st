package su.nsk.iae.post.generator.plcopen.xml.common.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.VarDeclaration

class SimpleVarHelper extends VarHelper {
	
	new() {
		varType = "localVars"
	}
	
	override add(EObject varDecl, String pref) {
		if (varDecl instanceof VarDeclaration) {
			parseSimpleVar(varDecl.vars, pref, varDecl.const)
		}
	}
	
}