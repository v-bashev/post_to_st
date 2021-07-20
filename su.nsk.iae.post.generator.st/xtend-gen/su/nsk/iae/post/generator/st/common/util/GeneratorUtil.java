package su.nsk.iae.post.generator.st.common.util;

import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class GeneratorUtil {
  public static String generateStopConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_STOP");
    return _builder.toString();
  }
  
  public static String generateErrorConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_ERROR");
    return _builder.toString();
  }
  
  public static String generateGlobalTime() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_global_time");
    return _builder.toString();
  }
}
