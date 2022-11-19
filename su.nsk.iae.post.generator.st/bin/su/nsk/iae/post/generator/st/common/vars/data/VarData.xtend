package su.nsk.iae.post.generator.st.common.vars.data

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
	}
	
	new(String name, String type, boolean isConstant, List<String> arraValues) {
		this.name = name
		this.type = type
		this.value = null
		this.isConstant = isConstant
		this.isArray = true
		this.arraValues = arraValues
	}
	
	String name
	String type
	String value
	
	boolean isConstant
	
	boolean isArray
	List<String> arraValues
}
