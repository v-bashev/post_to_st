package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.poST.Program

class ProgramPOUGenerator extends ProgramGenerator {
	
	new(Program program) {
		programName = program.name
		type = "PROGRAM"
		for (v : program.progInVars) {
			inVarList.add(v)
		}
		for (v : program.progOutVars) {
			outVarList.add(v)
		}
		for (v : program.progInOutVars) {
			inOutVarList.add(v)
		}
		for (v : program.progExternVars) {
			externalVarList.add(v)
		}
		for (v : program.progVars) {
			varList.add(v)
		}
		for (v : program.progTempVars) {
			tempVarList.add(v)
		}
		parseProcesses(program.processes)
	}
	
}