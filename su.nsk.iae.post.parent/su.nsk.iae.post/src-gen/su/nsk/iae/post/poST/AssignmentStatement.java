/**
 * generated by Xtext 2.24.0
 */
package su.nsk.iae.post.poST;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link su.nsk.iae.post.poST.AssignmentStatement#getVariable <em>Variable</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.AssignmentStatement#getArray <em>Array</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.AssignmentStatement#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see su.nsk.iae.post.poST.PoSTPackage#getAssignmentStatement()
 * @model
 * @generated
 */
public interface AssignmentStatement extends Statement
{
  /**
   * Returns the value of the '<em><b>Variable</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' reference.
   * @see #setVariable(SymbolicVariable)
   * @see su.nsk.iae.post.poST.PoSTPackage#getAssignmentStatement_Variable()
   * @model
   * @generated
   */
  SymbolicVariable getVariable();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.AssignmentStatement#getVariable <em>Variable</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Variable</em>' reference.
   * @see #getVariable()
   * @generated
   */
  void setVariable(SymbolicVariable value);

  /**
   * Returns the value of the '<em><b>Array</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Array</em>' containment reference.
   * @see #setArray(ArrayVariable)
   * @see su.nsk.iae.post.poST.PoSTPackage#getAssignmentStatement_Array()
   * @model containment="true"
   * @generated
   */
  ArrayVariable getArray();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.AssignmentStatement#getArray <em>Array</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Array</em>' containment reference.
   * @see #getArray()
   * @generated
   */
  void setArray(ArrayVariable value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Expression)
   * @see su.nsk.iae.post.poST.PoSTPackage#getAssignmentStatement_Value()
   * @model containment="true"
   * @generated
   */
  Expression getValue();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.AssignmentStatement#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Expression value);

} // AssignmentStatement
