package su.nsk.iae.post.generator.plcopen.xml.configuration

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.poST.Configuration
import su.nsk.iae.post.poST.Resource

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

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
		<data name="http://www.3s-software.com/plcopenxml/application" handleUnknown="implementation">
			«FOR r : resources»
				«r.generateResource»
			«ENDFOR»
		</data>
		
	'''
	
	def EList<Resource> getResources() {
		return configuration.resources
	}
	
}