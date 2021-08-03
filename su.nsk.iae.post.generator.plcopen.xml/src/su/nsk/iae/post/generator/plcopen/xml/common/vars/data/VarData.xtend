package su.nsk.iae.post.generator.plcopen.xml.common.vars.data

import java.util.List
import org.eclipse.xtend.lib.annotations.Data

@Data
class VarData {
	
	new(String name, String type, String value, boolean isConstant) {
		this.name = name
		this.type = type
		this.value = value
		this.isConstant = isConstant
		this.isArray = false
		this.arraValues = null
		this.arrayStart = null
		this.arrayEnd = null
	}
	
	new(String name, String type, String arrayStart, String arrayEnd, boolean isConstant, List<String> arraValues) {
		this.name = name
		this.type = type
		this.value = null
		this.isConstant = isConstant
		this.isArray = true
		this.arraValues = arraValues
		this.arrayStart = arrayStart
		this.arrayEnd = arrayEnd
	}
	
	String name
	String type
	String value
	
	boolean isConstant
	
	boolean isArray
	String arrayStart
	String arrayEnd
	List<String> arraValues
}
