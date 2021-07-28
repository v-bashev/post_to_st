package su.nsk.iae.post.generator.plcopen.xml.configuration

import java.util.LinkedList
import java.util.List
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.poST.Resource

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

class ResourceGenerator {
	
	Resource resource
	
	VarHelper resVarList = new GlobalVarHelper
	List<TaskGenerator> tasks = new LinkedList
	List<ProgramConfigurationGenerator> programConfigurationGenerators = new LinkedList
	
	new (Resource resource) {
		this.resource = resource
		resource.resGlobVars.stream.forEach([v | resVarList.add(v)])
		resource.resStatement.tasks.stream.forEach([t | tasks.add(new TaskGenerator(t))])
		resource.resStatement.programConfs.stream.forEach([p | programConfigurationGenerators.add(new ProgramConfigurationGenerator(p))])
	}
	
	def generateResource() '''
		<resource name="�resource.name�">
			�FOR t : tasks�
				�t.generateTask�
			�ENDFOR�
			�FOR p : programConfigurationGenerators�
				�p.generateProgramConfiguration�
			�ENDFOR�
		</resource>
	'''
}