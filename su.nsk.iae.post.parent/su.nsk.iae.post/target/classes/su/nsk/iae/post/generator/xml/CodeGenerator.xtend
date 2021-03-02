package su.nsk.iae.post.generator.xml

import java.text.SimpleDateFormat
import java.util.Calendar
import org.eclipse.xtext.generator.IFileSystemAccess2
import su.nsk.iae.post.generator.st.ICodeGenerator
import su.nsk.iae.post.generator.st.common.vars.VarHelper
import su.nsk.iae.post.generator.st.common.vars.data.VarData

class CodeGenerator extends ICodeGenerator {
	
	def void generate(IFileSystemAccess2 fsa, String path) {
		fsa.generateFile('''«path»«codeName.toLowerCase».xml''', generateCode)
	}
	
	protected override String generateCode() '''
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
							«varList.generateVar»
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
	
	private def String generateCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(Calendar.instance.time)
	}
	
	def String generateVar(VarHelper varHelper) '''
		«IF !varHelper.list.empty»
			«IF varHelper.hasConstant»
				<localVars constant="true">
					«FOR v : varHelper.list»
						«IF v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</localVars>
			«ENDIF»
			«IF varHelper.hasNonConstant»
				<localVars>
					«FOR v : varHelper.list»
						«IF !v.isConstant»
							«v.generateSingleDeclaration»
						«ENDIF»
					«ENDFOR»
				</localVars>
			«ENDIF»
		«ENDIF»
	'''
	
	private def String generateSingleDeclaration(VarData data) '''
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