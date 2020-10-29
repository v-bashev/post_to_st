package su.nsk.iae.post.generator.st

import su.nsk.iae.post.poST.FunctionBlock

class FBGenerator extends CodeGenerator {
	
	new(FunctionBlock fb) {
		codeName = fb.name
		type = "FUNCTION_BLOCK"
		for (v : fb.progInVars) {
			inVarList.add(v)
		}
		for (v : fb.progOutVars) {
			outVarList.add(v)
		}
		for (v : fb.progInOutVars) {
			inOutVarList.add(v)
		}
		for (v : fb.progExternVars) {
			externalVarList.add(v)
		}
		for (v : fb.progVars) {
			varList.add(v)
		}
		for (v : fb.progTempVars) {
			tempVarList.add(v)
		}
		parseProcesses(fb.processes)
	}
	
}