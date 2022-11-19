package su.nsk.iae.post.generator.st

import su.nsk.iae.post.generator.st.common.ProgramGenerator
import su.nsk.iae.post.poST.Program

class ProgramPOUGenerator extends ProgramGenerator {
	
	new(Program program, boolean templateProcess) {
		super(templateProcess)
		object = program
		programName = program.name
		type = "PROGRAM"
		
		program.progInVars.stream.forEach([v | inVarList.add(v)])
		program.progOutVars.stream.forEach([v | outVarList.add(v)])
		program.progInOutVars.stream.forEach([v | inOutVarList.add(v)])
		program.progExternVars.stream.forEach([v | externalVarList.add(v)])
		program.progVars.stream.forEach([v | varList.add(v)])
		program.progTempVars.stream.forEach([v | tempVarList.add(v)])
		
		parseProcesses(program.processes)
	}
	
}