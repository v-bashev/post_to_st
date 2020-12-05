package su.nsk.iae.post.generator.xml

import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.generator.IFileSystemAccess2
import su.nsk.iae.post.generator.xml.vars.ExternalVarHelper
import su.nsk.iae.post.generator.xml.vars.InputOutputVarHelper
import su.nsk.iae.post.generator.xml.vars.InputVarHelper
import su.nsk.iae.post.generator.xml.vars.OutputVarHelper
import su.nsk.iae.post.generator.xml.vars.SimpleVarHelper
import su.nsk.iae.post.generator.xml.vars.TempVarHelper
import su.nsk.iae.post.generator.xml.vars.VarHelper
import su.nsk.iae.post.poST.Process
import java.text.SimpleDateFormat
import java.util.Calendar

class CodeGenerator {
	
	protected String codeName
	protected String type
	
	protected VarHelper inVarList = new InputVarHelper
	protected VarHelper outVarList = new OutputVarHelper
	protected VarHelper inOutVarList = new InputOutputVarHelper
	protected VarHelper externalVarList = new ExternalVarHelper
	protected VarHelper varList = new SimpleVarHelper
	protected VarHelper tempVarList = new TempVarHelper
	
	List<ProcessGenerator> processList = new LinkedList
	
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
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«codeName.toLowerCase».xml''', generateCode)
	}
	
	private def String generateCode() '''
		<?xml version="1.0" encoding="utf-8"?>
		<project xmlns="http://www.plcopen.org/xml/tc6_0200">
			<fileHeader companyName="" productName="CODESYS" productVersion="CODESYS V3.5 SP11" creationDateTime="«generateCurrentTime»" />
			<contentHeader name="«codeName»_poST.project">
		 		<coordinateInfo>
					<fbd>
						<scaling x="1" y="1" />
					</fbd>
					<ld>
						<scaling x="1" y="1" />
					</ld>
					<sfc>
						<scaling x="1" y="1" />
					</sfc>
				</coordinateInfo>
				<addData>
					<data name="http://www.3s-software.com/plcopenxml/projectinformation" handleUnknown="implementation">
						<ProjectInformation />
					</data>
				</addData>
			</contentHeader>
			<types>
				<dataTypes />
				<pous>
					<pou name="«codeName»" pouType="«type.toLowerCase»">
						<interface>
							«varList.generate»
						</interface>
						<body>
							<ST>
								<xhtml xmlns="http://www.w3.org/1999/xhtml">
		«generateGlobalTime» := TIME();
		
		«FOR p : processList»
			«p.generateBody»
			
		«ENDFOR»
		</xhtml>
							</ST>
						</body>
					</pou>
				</pous>
			</types>
			<instances>
				<configurations />
			</instances>
		</project>
	'''
	
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
	
	private def String generateCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(Calendar.instance.time)
	}
}