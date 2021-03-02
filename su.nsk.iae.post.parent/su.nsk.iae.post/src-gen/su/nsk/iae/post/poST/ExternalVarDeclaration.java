/**
 * generated by Xtext 2.24.0
 */
package su.nsk.iae.post.poST;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Var Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link su.nsk.iae.post.poST.ExternalVarDeclaration#isConst <em>Const</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.ExternalVarDeclaration#getVars <em>Vars</em>}</li>
 * </ul>
 *
 * @see su.nsk.iae.post.poST.PoSTPackage#getExternalVarDeclaration()
 * @model
 * @generated
 */
public interface ExternalVarDeclaration extends EObject
{
  /**
   * Returns the value of the '<em><b>Const</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Const</em>' attribute.
   * @see #setConst(boolean)
   * @see su.nsk.iae.post.poST.PoSTPackage#getExternalVarDeclaration_Const()
   * @model
   * @generated
   */
  boolean isConst();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.ExternalVarDeclaration#isConst <em>Const</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Const</em>' attribute.
   * @see #isConst()
   * @generated
   */
  void setConst(boolean value);

  /**
   * Returns the value of the '<em><b>Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.ExternalVarInitDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getExternalVarDeclaration_Vars()
   * @model containment="true"
   * @generated
   */
  EList<ExternalVarInitDeclaration> getVars();

} // ExternalVarDeclaration
