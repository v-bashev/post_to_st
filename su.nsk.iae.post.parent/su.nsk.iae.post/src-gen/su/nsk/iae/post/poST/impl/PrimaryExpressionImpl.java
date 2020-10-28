/**
 * generated by Xtext 2.23.0
 */
package su.nsk.iae.post.poST.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import su.nsk.iae.post.poST.Constant;
import su.nsk.iae.post.poST.Expression;
import su.nsk.iae.post.poST.PoSTPackage;
import su.nsk.iae.post.poST.PrimaryExpression;
import su.nsk.iae.post.poST.ProcessStatusExpression;
import su.nsk.iae.post.poST.SymbolicVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link su.nsk.iae.post.poST.impl.PrimaryExpressionImpl#getConst <em>Const</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.impl.PrimaryExpressionImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.impl.PrimaryExpressionImpl#getProcStatus <em>Proc Status</em>}</li>
 *   <li>{@link su.nsk.iae.post.poST.impl.PrimaryExpressionImpl#getNestExpr <em>Nest Expr</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrimaryExpressionImpl extends UnaryExpressionImpl implements PrimaryExpression
{
  /**
   * The cached value of the '{@link #getConst() <em>Const</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConst()
   * @generated
   * @ordered
   */
  protected Constant const_;

  /**
   * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariable()
   * @generated
   * @ordered
   */
  protected SymbolicVariable variable;

  /**
   * The cached value of the '{@link #getProcStatus() <em>Proc Status</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProcStatus()
   * @generated
   * @ordered
   */
  protected ProcessStatusExpression procStatus;

  /**
   * The cached value of the '{@link #getNestExpr() <em>Nest Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNestExpr()
   * @generated
   * @ordered
   */
  protected Expression nestExpr;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PrimaryExpressionImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return PoSTPackage.Literals.PRIMARY_EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Constant getConst()
  {
    return const_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConst(Constant newConst, NotificationChain msgs)
  {
    Constant oldConst = const_;
    const_ = newConst;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__CONST, oldConst, newConst);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setConst(Constant newConst)
  {
    if (newConst != const_)
    {
      NotificationChain msgs = null;
      if (const_ != null)
        msgs = ((InternalEObject)const_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PoSTPackage.PRIMARY_EXPRESSION__CONST, null, msgs);
      if (newConst != null)
        msgs = ((InternalEObject)newConst).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PoSTPackage.PRIMARY_EXPRESSION__CONST, null, msgs);
      msgs = basicSetConst(newConst, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__CONST, newConst, newConst));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SymbolicVariable getVariable()
  {
    if (variable != null && variable.eIsProxy())
    {
      InternalEObject oldVariable = (InternalEObject)variable;
      variable = (SymbolicVariable)eResolveProxy(oldVariable);
      if (variable != oldVariable)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, PoSTPackage.PRIMARY_EXPRESSION__VARIABLE, oldVariable, variable));
      }
    }
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SymbolicVariable basicGetVariable()
  {
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setVariable(SymbolicVariable newVariable)
  {
    SymbolicVariable oldVariable = variable;
    variable = newVariable;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__VARIABLE, oldVariable, variable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ProcessStatusExpression getProcStatus()
  {
    return procStatus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetProcStatus(ProcessStatusExpression newProcStatus, NotificationChain msgs)
  {
    ProcessStatusExpression oldProcStatus = procStatus;
    procStatus = newProcStatus;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS, oldProcStatus, newProcStatus);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setProcStatus(ProcessStatusExpression newProcStatus)
  {
    if (newProcStatus != procStatus)
    {
      NotificationChain msgs = null;
      if (procStatus != null)
        msgs = ((InternalEObject)procStatus).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS, null, msgs);
      if (newProcStatus != null)
        msgs = ((InternalEObject)newProcStatus).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS, null, msgs);
      msgs = basicSetProcStatus(newProcStatus, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS, newProcStatus, newProcStatus));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Expression getNestExpr()
  {
    return nestExpr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetNestExpr(Expression newNestExpr, NotificationChain msgs)
  {
    Expression oldNestExpr = nestExpr;
    nestExpr = newNestExpr;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR, oldNestExpr, newNestExpr);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNestExpr(Expression newNestExpr)
  {
    if (newNestExpr != nestExpr)
    {
      NotificationChain msgs = null;
      if (nestExpr != null)
        msgs = ((InternalEObject)nestExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR, null, msgs);
      if (newNestExpr != null)
        msgs = ((InternalEObject)newNestExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR, null, msgs);
      msgs = basicSetNestExpr(newNestExpr, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR, newNestExpr, newNestExpr));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case PoSTPackage.PRIMARY_EXPRESSION__CONST:
        return basicSetConst(null, msgs);
      case PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS:
        return basicSetProcStatus(null, msgs);
      case PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR:
        return basicSetNestExpr(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case PoSTPackage.PRIMARY_EXPRESSION__CONST:
        return getConst();
      case PoSTPackage.PRIMARY_EXPRESSION__VARIABLE:
        if (resolve) return getVariable();
        return basicGetVariable();
      case PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS:
        return getProcStatus();
      case PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR:
        return getNestExpr();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case PoSTPackage.PRIMARY_EXPRESSION__CONST:
        setConst((Constant)newValue);
        return;
      case PoSTPackage.PRIMARY_EXPRESSION__VARIABLE:
        setVariable((SymbolicVariable)newValue);
        return;
      case PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS:
        setProcStatus((ProcessStatusExpression)newValue);
        return;
      case PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR:
        setNestExpr((Expression)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case PoSTPackage.PRIMARY_EXPRESSION__CONST:
        setConst((Constant)null);
        return;
      case PoSTPackage.PRIMARY_EXPRESSION__VARIABLE:
        setVariable((SymbolicVariable)null);
        return;
      case PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS:
        setProcStatus((ProcessStatusExpression)null);
        return;
      case PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR:
        setNestExpr((Expression)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case PoSTPackage.PRIMARY_EXPRESSION__CONST:
        return const_ != null;
      case PoSTPackage.PRIMARY_EXPRESSION__VARIABLE:
        return variable != null;
      case PoSTPackage.PRIMARY_EXPRESSION__PROC_STATUS:
        return procStatus != null;
      case PoSTPackage.PRIMARY_EXPRESSION__NEST_EXPR:
        return nestExpr != null;
    }
    return super.eIsSet(featureID);
  }

} //PrimaryExpressionImpl