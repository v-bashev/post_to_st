package su.nsk.iae.post.generator.plcopen.xml

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import su.nsk.iae.post.generator.plcopen.xml.common.ProcessGenerator
import su.nsk.iae.post.generator.plcopen.xml.common.vars.ExternalVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.InputOutputVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.InputVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.OutputVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.SimpleVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.TempVarHelper
import su.nsk.iae.post.generator.plcopen.xml.common.vars.VarHelper
import su.nsk.iae.post.poST.Process

abstract class ICodeGenerator {
	
	protected String codeName
	protected String type
	
	protected VarHelper inVarList = new InputVarHelper
	protected VarHelper outVarList = new OutputVarHelper
	protected VarHelper inOutVarList = new InputOutputVarHelper
	protected VarHelper externalVarList = new ExternalVarHelper
	protected VarHelper varList = new SimpleVarHelper
	protected VarHelper tempVarList = new TempVarHelper
	
	protected List<ProcessGenerator> processList = new LinkedList
	
	def String generateCode()
	
	protected def parseProcesses(EList<Process> processes) {
		for (p: processes) {
			processList.add(new ProcessGenerator(this, p))
		}
		addVar(generateGlobalTime, "TIME")
		for (p : processList) {
			p.addTimeVars()
		}
		for (p : processList) {
			p.addStateVars()
		}
		addVar(generateStopConstant, "INT", "254", true)
		addVar(generateErrorConstant, "INT", "255", true)
	}
	
	def String generateStopConstant() {
		return '''_STOP'''
	}
	
	def String generateErrorConstant() {
		return '''_ERROR'''
	}
	
	def String generateGlobalTime() {
		return '''_global_time'''
	}
	
	def String generateProcessEnum(String processName) {
		return processList.findFirst[name == processName].generateEnumName
	}
	
	def String generateProcessStart(String processName) {
		return processList.findFirst[name == processName].generateStart
	}
	
	def void addVar(EObject varDecl) {
		varList.add(varDecl)
	}
	
	def void addVar(String name, String type) {
		varList.add(name, type)
	}
	
	def void addVar(String name, String type, String value) {
		varList.add(name, type, value)
	}
	
	def void addVar(String name, String type, String value, boolean isConstant) {
		varList.add(name, type, value, isConstant)
	}
	
	def void addTempVar(EObject varDecl) {
		tempVarList.add(varDecl)
	}
	
	def void addTempVar(String name, String type, String value) {
		tempVarList.add(name, type, value)
	}
	
	def boolean isFirstProcess(ProcessGenerator process) {
		return processList.get(0) == process
	}
}