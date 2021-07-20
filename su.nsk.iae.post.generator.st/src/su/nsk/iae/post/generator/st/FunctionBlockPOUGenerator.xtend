package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.poST.FunctionBlock

class FunctionBlockPOUGenerator extends ProgramGenerator {
	
	new(FunctionBlock fb) {
		programName = fb.name
		type = "FUNCTION_BLOCK"
		for (v : fb.fbInVars) {
			inVarList.add(v)
		}
		for (v : fb.fbOutVars) {
			outVarList.add(v)
		}
		for (v : fb.fbInOutVars) {
			inOutVarList.add(v)
		}
		for (v : fb.fbExternVars) {
			externalVarList.add(v)
		}
		for (v : fb.fbVars) {
			varList.add(v)
		}
		for (v : fb.fbTempVars) {
			tempVarList.add(v)
		}
		parseProcesses(fb.processes)
	}
	
}