package su.nsk.iae.post.generator.plcopen.xml.common.vars

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.generator.plcopen.xml.common.vars.data.VarData
import su.nsk.iae.post.poST.VarInitDeclaration

import static extension su.nsk.iae.post.generator.plcopen.xml.common.util.GeneratorUtil.*

abstract class VarHelper {

	protected String varType
	protected List<VarData> listDecl = new LinkedList
	
	def void add(EObject varDecl) {
		add(varDecl, "")
	}
	
	def void add(EObject varDecl, String pref)
	
	def void add(String name, String type) {
		add(name, type, null)
	}
	
	def void add(String name, String type, String value) {
		add(name, type, value, false)
	}
	
	def void add(String name, String type, String value, boolean isConstant) {
		listDecl.add(new VarData(name, type, value, isConstant))
	}
	
	def String getType() {
		return varType
	}
	
	def List<VarData> getList() {
		return listDecl
	}
	
	def void clear() {
		listDecl.clear()
	}
	
	def boolean contains(String name) {
		return listDecl.stream.anyMatch([v | v.name == name])
	}
	
	def boolean hasConstant() {
		return listDecl.stream.anyMatch([v | v.isConstant])
	}
	
	def boolean hasNonConstant() {
		return listDecl.stream.anyMatch([v | !v.isConstant])
	}
	
	protected def void parseSimpleVar(EList<VarInitDeclaration> varList, String pref) {
		parseSimpleVar(varList, pref, false)
	}
	
	protected def void parseSimpleVar(EList<VarInitDeclaration> varList, String pref, boolean isConst) {
		for (v : varList) {
			if (v.spec !== null) {
				val type = v.spec.type
				var String value = null
				if (v.spec.value !== null) {
					value = v.spec.value.generateExpression
				}
				for (e : v.varList.vars) {
					listDecl.add(new VarData(pref + e.name, type, value, isConst))
				}
			} else if (v.fb !== null) {
				val type = v.fb.name
				for (e : v.varList.vars) {
					listDecl.add(new VarData(pref + e.name, type, null, isConst))
				}
			} else {
				val type = v.arrSpec.init.type
				var String start = null
				var String end = null
				if (v.arrSpec.init.interval !== null) {
					start = v.arrSpec.init.interval.start.generateExpression
					end = v.arrSpec.init.interval.end.generateExpression
				}
				var List<String> values = null
				if (v.arrSpec.values !== null) {
					values = new LinkedList
					for (e : v.arrSpec.values.elements) {
						values.add(e.generateExpression)
					}
				}
				for (e : v.varList.vars) {
					listDecl.add(new VarData(pref + e.name, type, start, end, isConst, values))
				}
			}
		}
	}
}