package su.nsk.iae.post.generator.plcopen.xml.configuration

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import su.nsk.iae.post.generator.plcopen.xml.XMLGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.GlobalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.poST.Configuration
import su.nsk.iae.post.poST.Resource

class ConfigurationGenerator {
	
	Configuration configuration
	
	VarHelper confVarList = new GlobalVarHelper
	List<ResourceGenerator> resources = new LinkedList
	
	new (Configuration configuration, XMLGenerator xmlGenerator) {
		this.configuration = configuration
		configuration.confGlobVars.stream.forEach([v | confVarList.add(v) xmlGenerator.addGlobalVar(v)])
		configuration.resources.stream.forEach([r | resources.add(new ResourceGenerator(r, xmlGenerator))])
	}
	
	def EList<Resource> getResources() {
		return configuration.resources
	}
	
}