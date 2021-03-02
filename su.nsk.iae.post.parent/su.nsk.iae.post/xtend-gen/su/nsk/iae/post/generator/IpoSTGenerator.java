package su.nsk.iae.post.generator;

import org.eclipse.xtext.generator.IFileSystemAccess2;
import su.nsk.iae.post.poST.Model;

@SuppressWarnings("all")
public interface IpoSTGenerator {
  void setModel(final Model model);
  
  void generate(final IFileSystemAccess2 fsa, final String path);
}
