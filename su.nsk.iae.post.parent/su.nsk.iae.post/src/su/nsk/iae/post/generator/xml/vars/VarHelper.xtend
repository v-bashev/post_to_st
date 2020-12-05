package su.nsk.iae.post.generator.xml.vars

import java.util.List
import su.nsk.iae.post.generator.xml.vars.data.VarData
import java.util.LinkedList
import org.eclipse.emf.common.util.EList
import su.nsk.iae.post.poST.VarInitDeclaration
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.nodemodel.util.NodeModelUtils

abstract class VarHelper {

	protected String varType
	protected List<VarData> listDecl = new LinkedList
	
	def void add(EObject varDecl)
	
	def void add(String name, String type) {
		add(name, type, null)
	}
	
	def void add(String name, String type, String value) {
		add(name, type, value, false)
	}
	
	def void add(String name, String type, String value, boolean isConstant) {
		listDecl.add(new VarData(name, type, value, isConstant))
	}
	
	def String generate() '''
		«IF !listDecl.empty»
			«IF hasConstant»
				<localVars constant="true">
					«FOR v : listDecl»
						«IF v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</localVars>
			«ENDIF»
			«IF hasNonConstant»
				<localVars>
					«FOR v : listDecl»
						«IF !v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</localVars>
			«ENDIF»
		«ENDIF»
	'''
	
	def getList() {
		return listDecl
	}
	
	def boolean contains(String name) {
		for (v : listDecl) {
			if (v.name == name) {
				return true
			}
		}
		return false
	}
	
	protected def void parseSimpleVar(EList<VarInitDeclaration> varList) {
		parseSimpleVar(varList, false)
	}
	
	protected def void parseSimpleVar(EList<VarInitDeclaration> varList, boolean isConst) {
		for (v : varList) {
			if (v.spec !== null) {
				val type = v.spec.type
				var String value = null
				if (v.spec.value !== null) {
					value = NodeModelUtils.getNode(v.spec.value).text.trim
				}
				for (e : v.varList.vars) {
					listDecl.add(new VarData(e.name, type, value, isConst))
				}
			} else {
				val type = '''ARRAY [«NodeModelUtils.getNode(v.arrSpec.init.start).text.trim»..«NodeModelUtils.getNode(v.arrSpec.init.end).text.trim»] OF «v.arrSpec.init.type»'''
				var List<String> values = null
				if (v.arrSpec.values !== null) {
					values = new LinkedList
					for (e : v.arrSpec.values.elements) {
						values.add(NodeModelUtils.getNode(e).text.trim)
					}
				}
				for (e : v.varList.vars) {
					listDecl.add(new VarData(e.name, type, isConst, values))
				}
			}
		}
	}
	
	protected def String generateSingleDeclaration(VarData data) '''
		<variable name="«data.name»">
			<type>
				<«data.type» />
			</type>
			«IF data.value !== null»
				<initialValue>
					<simpleValue value="«data.value»" />
				</initialValue>
			«ENDIF»
		</variable>
	'''
	
	private def boolean hasConstant() {
		for (v : listDecl) {
			if (v.isConstant) {
				return true
			}
		}
		return false
	}
	
	private def boolean hasNonConstant() {
		for (v : listDecl) {
			if (!v.isConstant) {
				return true
			}
		}
		return false
	}
}