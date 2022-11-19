package su.nsk.iae.post.generator.plcopen.xml.configuration

import su.nsk.iae.post.poST.ProgramConfiguration

class ProgramConfigurationGenerator {
	
	ProgramConfiguration programConf
	
	new (ProgramConfiguration programConfiguration) {
		this.programConf = programConfiguration
	}
	
	def String generateProgramConfiguration() {
		return '''PROGRAM «programConf.name»«generateTask»;'''
	}
	
	private def String generateTask() {
		if (programConf.task !== null) {
			return ''' WITH «programConf.task.name»'''
		}
		return ''''''
	}
	
}