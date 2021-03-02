/**
 * generated by Xtext 2.24.0
 */
package su.nsk.iae.post.poST;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getName <em>Name</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProgInVars <em>Prog In Vars</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProgOutVars <em>Prog Out Vars</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProgInOutVars <em>Prog In Out Vars</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProgVars <em>Prog Vars</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProgTempVars <em>Prog Temp Vars</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProgExternVars <em>Prog Extern Vars</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.FunctionBlock#getProcesses <em>Processes</em>}</li>
 * </ul>
 *
 * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock()
 * @model
 * @generated
 */
public interface FunctionBlock extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link su.nsk.iae.post.poST.FunctionBlock#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Prog In Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.InputVarDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog In Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_ProgInVars()
   * @model containment="true"
   * @generated
   */
  EList<InputVarDeclaration> getProgInVars();

  /**
   * Returns the value of the '<em><b>Prog Out Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.OutputVarDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog Out Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_ProgOutVars()
   * @model containment="true"
   * @generated
   */
  EList<OutputVarDeclaration> getProgOutVars();

  /**
   * Returns the value of the '<em><b>Prog In Out Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.InputOutputVarDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog In Out Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_ProgInOutVars()
   * @model containment="true"
   * @generated
   */
  EList<InputOutputVarDeclaration> getProgInOutVars();

  /**
   * Returns the value of the '<em><b>Prog Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.VarDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_ProgVars()
   * @model containment="true"
   * @generated
   */
  EList<VarDeclaration> getProgVars();

  /**
   * Returns the value of the '<em><b>Prog Temp Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.TempVarDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog Temp Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_ProgTempVars()
   * @model containment="true"
   * @generated
   */
  EList<TempVarDeclaration> getProgTempVars();

  /**
   * Returns the value of the '<em><b>Prog Extern Vars</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.ExternalVarDeclaration}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog Extern Vars</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_ProgExternVars()
   * @model containment="true"
   * @generated
   */
  EList<ExternalVarDeclaration> getProgExternVars();

  /**
   * Returns the value of the '<em><b>Processes</b></em>' containment reference list.
   * The list contents are of type {@link su.nsk.iae.post.poST.Process}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Processes</em>' containment reference list.
   * @see su.nsk.iae.post.poST.PoSTPackage#getFunctionBlock_Processes()
   * @model containment="true"
   * @generated
   */
  EList<su.nsk.iae.post.poST.Process> getProcesses();

} // FunctionBlock
