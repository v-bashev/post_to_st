package su.nsk.iae.post.generator.st.configuration

import su.nsk.iae.post.poST.ProgramConfiguration

class ProgramConfigurationGenerator {
	
	ProgramConfiguration programConf
	
	new (ProgramConfiguration programConfiguration) {
		this.programConf = programConfiguration
	}
	
	def String generateProgramConfiguration() {
		return '''PROGRAM «programConf.name»«generateTask» : «programConf.name.capitalizeFirst»;'''
	}
	
	private def String generateTask() {
		if (programConf.task !== null) {
			return ''' WITH «programConf.task.name»'''
		}
		return ''''''
	}
	
	private def String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1)
	}
	
/*
	private def String generateArgs() {
		if (programConf.args === null) {
			return ''''''
		}
		val res = new ArrayList<String>
		programConf.args.elements.stream.forEach([e |
			if (e instanceof AttachVariableConfElement) {
				val attach = e.programVar.name + e.assig.generateAssignmentSign + e.attVar.name
				res.add(attach)
			} else {
				val processConf = e as TemplateProcessConfElement
				processConf.args.elements.stream.forEach([p |
					val attach = processConf.name.generateProcessVar(p.programVar.name) + p.assig.generateAssignmentSign + p.attVar.name
					res.add(attach)
				])
			}
		])
		return '''(«res.join(", ")»)'''
	}
	
	private def String generateAssignmentSign(AssignmentType assig) {
		if (assig == AssignmentType.IN) {
			return ''' := '''
		}
		return ''' => '''
	}
	
	private def String generateProcessVar(String process, String varName) {
		return process.generateVarName(varName)
	}
*/
	
}