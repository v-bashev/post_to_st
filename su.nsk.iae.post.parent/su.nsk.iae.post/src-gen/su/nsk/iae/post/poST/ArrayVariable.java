/**
 * generated by Xtext 2.24.0
 */
package su.nsk.iae.post.poST;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link su.nsk.iae.post.poST.ArrayVariable#getVarName <em>Var Name</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.ArrayVariable#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @see su.nsk.iae.post.poST.PoSTPackage#getArrayVariable()
 * @model
 * @generated
 */
public interface ArrayVariable extends EObject
{
  /**
   * Returns the value of the '<em><b>Var Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Var Name</em>' reference.
   * @see #setVarName(SymbolicVariable)
   * @see su.nsk.iae.post.poST.PoSTPackage#getArrayVariable_VarName()
   * @model
   * @generated
   */
  SymbolicVariable getVarName();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.ArrayVariable#getVarName <em>Var Name</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Var Name</em>' reference.
   * @see #getVarName()
   * @generated
   */
  void setVarName(SymbolicVariable value);

  /**
   * Returns the value of the '<em><b>Index</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Index</em>' containment reference.
   * @see #setIndex(Expression)
   * @see su.nsk.iae.post.poST.PoSTPackage#getArrayVariable_Index()
   * @model containment="true"
   * @generated
   */
  Expression getIndex();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.ArrayVariable#getIndex <em>Index</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Index</em>' containment reference.
   * @see #getIndex()
   * @generated
   */
  void setIndex(Expression value);

} // ArrayVariable
