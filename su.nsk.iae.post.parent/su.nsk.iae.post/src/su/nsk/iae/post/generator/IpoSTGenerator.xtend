package su.nsk.iae.post.generator

import su.nsk.iae.post.poST.Model
import org.eclipse.xtext.generator.IFileSystemAccess2

interface IpoSTGenerator {
	def void setModel(Model model)
	def void generateSingleFile(IFileSystemAccess2 fsa, String path)
	def void generateMultipleFiles(IFileSystemAccess2 fsa, String path)
}