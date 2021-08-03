package su.nsk.iae.post.generator.plcopen.xml.common.vars.data;

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
    this.arrayStart = null;
    this.arrayEnd = null;
  }
  
  public VarData(final String name, final String type, final String arrayStart, final String arrayEnd, final boolean isConstant, final List<String> arraValues) {
    this.name = name;
    this.type = type;
    this.value = null;
    this.isConstant = isConstant;
    this.isArray = true;
    this.arraValues = arraValues;
    this.arrayStart = arrayStart;
    this.arrayEnd = arrayEnd;
  }
  
  private final String name;
  
  private final String type;
  
  private final String value;
  
  private final boolean isConstant;
  
  private final boolean isArray;
  
  private final String arrayStart;
  
  private final String arrayEnd;
  
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
    result = prime * result + ((this.arrayStart== null) ? 0 : this.arrayStart.hashCode());
    result = prime * result + ((this.arrayEnd== null) ? 0 : this.arrayEnd.hashCode());
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
    if (this.arrayStart == null) {
      if (other.arrayStart != null)
        return false;
    } else if (!this.arrayStart.equals(other.arrayStart))
      return false;
    if (this.arrayEnd == null) {
      if (other.arrayEnd != null)
        return false;
    } else if (!this.arrayEnd.equals(other.arrayEnd))
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
    b.add("arrayStart", this.arrayStart);
    b.add("arrayEnd", this.arrayEnd);
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
  public String getArrayStart() {
    return this.arrayStart;
  }
  
  @Pure
  public String getArrayEnd() {
    return this.arrayEnd;
  }
  
  @Pure
  public List<String> getArraValues() {
    return this.arraValues;
  }
}
