package su.nsk.iae.post.generator.plcopen.xml.configuration

import su.nsk.iae.post.poST.Task

class TaskGenerator {
	
	Task task
	
	new (Task task) {
		this.task = task
	}
	
	def String generateTask() '''
		<task name="«task.name»" interval="PT0.01S" priority="«task.init.priority»">
	'''
	
}