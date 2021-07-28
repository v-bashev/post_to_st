package su.nsk.iae.post.generator.plcopen.xml.configuration

import su.nsk.iae.post.poST.Task

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

class TaskGenerator {
	
	Task task
	
	new (Task task) {
		this.task = task
	}
	
	def String generateTask() '''
		<task name="«task.name»" interval="PT0.01S" priority="«task.init.priority»">
	'''
	
	def String generateTaskA() {
		return '''TASK «task.name» («generateFirstArg», PRIORITY := «task.init.priority»);'''
	}
	
	private def String generateFirstArg() {
		val init = task.init
		if (init.interval !== null) {
			return '''INTERVAL := «init.interval.generateConstant»'''
		}
		return '''SINGLE := «init.single.generateConstant»'''
	}
	
}