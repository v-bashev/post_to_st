package su.nsk.iae.post.generator.st.configuration

import java.util.LinkedList
import java.util.List
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.poST.Resource

import static extension su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

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
		RESOURCE «resource.name» ON «resource.type»
			
			«resVarList.generateVars»
			«FOR t : tasks»
				«t.generateTask»
			«ENDFOR»
			
			«FOR p : programConfigurationGenerators»
				«p.generateProgramConfiguration»
			«ENDFOR»
			
		END_RESOURCE
	'''
}