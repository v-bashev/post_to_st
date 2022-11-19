package su.nsk.iae.post.generator.st.configuration

import su.nsk.iae.post.poST.Task

import static extension su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class TaskGenerator {
	
	Task task
	
	new (Task task) {
		this.task = task
	}
	
	def String generateTask() {
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