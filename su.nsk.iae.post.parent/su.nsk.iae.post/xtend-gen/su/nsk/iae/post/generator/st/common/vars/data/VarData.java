package su.nsk.iae.post.generator.st.common.vars.data;

import java.util.List;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@Data
@SuppressWarnings("all")
public class VarData {
  public VarData(final String name, final String type, final String value, final boolean isConstant) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.isConstant = isConstant;
    this.isArray = false;
    this.arraValues = null;
  }
  
  public VarData(final String name, final String type, final boolean isConstant, final List<String> arraValues) {
    this.name = name;
    this.type = type;
    this.value = null;
    this.isConstant = isConstant;
    this.isArray = true;
    this.arraValues = arraValues;
  }
  
  private final String name;
  
  private final String type;
  
  private final String value;
  
  private final boolean isConstant;
  
  private final boolean isArray;
  
  private final List<String> arraValues;
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    result = prime * result + (this.isConstant ? 1231 : 1237);
    result = prime * result + (this.isArray ? 1231 : 1237);
    return prime * result + ((this.arraValues== null) ? 0 : this.arraValues.hashCode());
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VarData other = (VarData) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    if (other.isConstant != this.isConstant)
      return false;
    if (other.isArray != this.isArray)
      return false;
    if (this.arraValues == null) {
      if (other.arraValues != null)
        return false;
    } else if (!this.arraValues.equals(other.arraValues))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("type", this.type);
    b.add("value", this.value);
    b.add("isConstant", this.isConstant);
    b.add("isArray", this.isArray);
    b.add("arraValues", this.arraValues);
    return b.toString();
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  @Pure
  public String getType() {
    return this.type;
  }
  
  @Pure
  public String getValue() {
    return this.value;
  }
  
  @Pure
  public boolean isConstant() {
    return this.isConstant;
  }
  
  @Pure
  public boolean isArray() {
    return this.isArray;
  }
  
  @Pure
  public List<String> getArraValues() {
    return this.arraValues;
  }
}
