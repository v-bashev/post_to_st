package su.nsk.iae.post.generator.xml.vars

import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.poST.ExternalVarDeclaration
import org.eclipse.emf.common.util.EList
import su.nsk.iae.post.poST.ExternalVarInitDeclaration
import su.nsk.iae.post.generator.xml.vars.data.VarData

class ExternalVarHelper extends VarHelper {
	
	new() {
		varType = "VAR_EXTERNAL"
	}
	
	override add(EObject varDecl) {
		if (varDecl instanceof ExternalVarDeclaration) {
			parseExternVar(varDecl.vars, varDecl.const)
		}
	}
	
	private def void parseExternVar(EList<ExternalVarInitDeclaration> varList, boolean isConst) {
		for (v : varList) {
			val type = v.type
			for (e : v.varList.vars) {
				listDecl.add(new VarData(e.name, type, null, isConst))
			}
		}
	}
	
}