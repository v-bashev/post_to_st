package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.poST.FunctionBlock

class FunctionBlockPOUGenerator extends ProgramGenerator {
	
	new(FunctionBlock fb) {
		programName = fb.name
		type = "FUNCTION_BLOCK"
		
		fb.fbInVars.stream.forEach([v | inVarList.add(v)])
		fb.fbOutVars.stream.forEach([v | outVarList.add(v)])
		fb.fbInOutVars.stream.forEach([v | inOutVarList.add(v)])
		fb.fbExternVars.stream.forEach([v | externalVarList.add(v)])
		fb.fbVars.stream.forEach([v | varList.add(v)])
		fb.fbTempVars.stream.forEach([v | tempVarList.add(v)])

		parseProcesses(fb.processes)
	}
	
}