/*
 * generated by Xtext 2.24.0
 */
package su.nsk.iae.post.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class PoSTAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("su/nsk/iae/post/parser/antlr/internal/InternalPoST.tokens");
	}
}
