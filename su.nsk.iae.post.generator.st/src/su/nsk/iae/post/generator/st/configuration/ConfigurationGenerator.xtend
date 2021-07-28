package su.nsk.iae.post.generator.st.configuration

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import su.nsk.iae.post.generator.st.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.poST.Configuration
import su.nsk.iae.post.poST.Resource

import static extension su.nsk.iae.post.generator.st.common.util.GeneratorUtil.*

class ConfigurationGenerator {
	
	Configuration configuration
	
	VarHelper confVarList = new GlobalVarHelper
	List<ResourceGenerator> resources = new LinkedList
	
	
	new (Configuration configuration) {
		this.configuration = configuration
		configuration.confGlobVars.stream.forEach([v | confVarList.add(v)])
		configuration.resources.stream.forEach([r | resources.add(new ResourceGenerator(r))])
	}
	
	def String generateConfiguration() '''
		CONFIGURATION «configuration.name»
			
			«confVarList.generateVars»
			«FOR r : resources»
				«r.generateResource»
				
			«ENDFOR»
		END_CONFIGURATION
		
	'''
	
	def EList<Resource> getResources() {
		return configuration.resources
	}
	
}