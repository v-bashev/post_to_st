package su.nsk.iae.post.generator.xml

import java.text.SimpleDateFormat
import java.util.Calendar
import org.eclipse.xtext.generator.IFileSystemAccess2
import su.nsk.iae.post.generator.xml.ICodeGenerator
import su.nsk.iae.post.generator.xml.common.vars.VarHelper
import su.nsk.iae.post.generator.xml.common.vars.data.VarData

class CodeGenerator extends ICodeGenerator {
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«codeName.toLowerCase».xml''', generateCode)
	}
	
	static def String generateXMLStart() '''
		<?xml version="1.0" encoding="utf-8"?>
		<project xmlns="http://www.plcopen.org/xml/tc6_0200">
			<fileHeader companyName="" productName="CODESYS" productVersion="CODESYS V3.5 SP11" creationDateTime="«generateCurrentTime»" />
			<contentHeader name="poST.project">
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
	'''
	
	static def String generateXMLEnd() {
		return generateXMLEnd(null)
	}
	
	static def String generateXMLEnd(VarHelper globalVars) '''
				</pous>
			</types>
			<instances>
				<configurations />
			</instances>
			«IF globalVars !== null»
				<addData>
					<data name="http://www.3s-software.com/plcopenxml/globalvars" handleUnknown="implementation">
						«generateVar(globalVars, "GVL")»
					</data>
				</addData>
			«ENDIF»
		</project>
	'''
	
	def String generateXMLBody() '''
					<pou name="«codeName»" pouType="«type.toLowerCase»">
						<interface>
							«varList.generateVar»
						</interface>
						<body>
							<ST>
								<xhtml xmlns="http://www.w3.org/1999/xhtml">«generateGlobalTime» := TIME();
			
		«FOR p : processList»
			«p.generateBody»
			
		«ENDFOR»
		</xhtml>
							</ST>
						</body>
					</pou>
	'''
	
	override String generateCode() '''
		«generateXMLStart»
		«generateXMLBody»
		«generateXMLEnd»
	'''
	
	private static def String generateCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(Calendar.instance.time)
	}
	
	static def String generateVar(VarHelper varHelper) {
		return generateVar(varHelper, null)
	}
	
	static def String generateVar(VarHelper varHelper, String name) '''
		«IF !varHelper.list.empty»
			«IF varHelper.hasConstant»
				<«varHelper.type»«IF name !== null» name="«name»"«ENDIF» constant="true">
					«FOR v : varHelper.list»
						«IF v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</«varHelper.type»>
			«ENDIF»
			«IF varHelper.hasNonConstant»
				<«varHelper.type»«IF name !== null» name="«name»"«ENDIF»>
					«FOR v : varHelper.list»
						«IF !v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</«varHelper.type»>
			«ENDIF»
		«ENDIF»
	'''
	
	private static def String generateSingleDeclaration(VarData data) '''
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
	
}