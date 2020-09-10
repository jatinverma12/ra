import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
import { getEntities as getEmployees } from 'app/entities/employees-my-suffix/employees-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './expenses-my-suffix.reducer';
import { IExpensesMySuffix } from 'app/shared/model/expenses-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExpensesMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IExpensesMySuffixUpdateState {
  isNew: boolean;
  incurredById: string;
}

export class ExpensesMySuffixUpdate extends React.Component<IExpensesMySuffixUpdateProps, IExpensesMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      incurredById: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEmployees();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { expensesEntity } = this.props;
      const entity = {
        ...expensesEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/expenses-my-suffix');
  };

  render() {
    const { expensesEntity, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    const { bill, billContentType } = expensesEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.expenses.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.expenses.home.createOrEditLabel">Create or edit a Expenses</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : expensesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="expenses-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="expenses-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="itemLabel" for="expenses-my-suffix-item">
                    <Translate contentKey="risingArjunApp.expenses.item">Item</Translate>
                  </Label>
                  <AvField
                    id="expenses-my-suffix-item"
                    type="text"
                    name="item"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="quantityLabel" for="expenses-my-suffix-quantity">
                    <Translate contentKey="risingArjunApp.expenses.quantity">Quantity</Translate>
                  </Label>
                  <AvField
                    id="expenses-my-suffix-quantity"
                    type="string"
                    className="form-control"
                    name="quantity"
                    validate={{
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="rateLabel" for="expenses-my-suffix-rate">
                    <Translate contentKey="risingArjunApp.expenses.rate">Rate</Translate>
                  </Label>
                  <AvField
                    id="expenses-my-suffix-rate"
                    type="string"
                    className="form-control"
                    name="rate"
                    validate={{
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="laborCostLabel" for="expenses-my-suffix-laborCost">
                    <Translate contentKey="risingArjunApp.expenses.laborCost">Labor Cost</Translate>
                  </Label>
                  <AvField
                    id="expenses-my-suffix-laborCost"
                    type="string"
                    className="form-control"
                    name="laborCost"
                    validate={{
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="otherExpenseLabel" for="expenses-my-suffix-otherExpense">
                    <Translate contentKey="risingArjunApp.expenses.otherExpense">Other Expense</Translate>
                  </Label>
                  <AvField id="expenses-my-suffix-otherExpense" type="string" className="form-control" name="otherExpense" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalLabel" for="expenses-my-suffix-total">
                    <Translate contentKey="risingArjunApp.expenses.total">Total</Translate>
                  </Label>
                  <AvField
                    id="expenses-my-suffix-total"
                    type="string"
                    className="form-control"
                    name="total"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="expenses-my-suffix-date">
                    <Translate contentKey="risingArjunApp.expenses.date">Date</Translate>
                  </Label>
                  <AvField id="expenses-my-suffix-date" type="date" className="form-control" name="date" />
                </AvGroup>
                <AvGroup>
                  <Label id="transactionIdLabel" for="expenses-my-suffix-transactionId">
                    <Translate contentKey="risingArjunApp.expenses.transactionId">Transaction Id</Translate>
                  </Label>
                  <AvField
                    id="expenses-my-suffix-transactionId"
                    type="text"
                    name="transactionId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="expenseModeLabel" for="expenses-my-suffix-expenseMode">
                    <Translate contentKey="risingArjunApp.expenses.expenseMode">Expense Mode</Translate>
                  </Label>
                  <AvInput
                    id="expenses-my-suffix-expenseMode"
                    type="select"
                    className="form-control"
                    name="expenseMode"
                    value={(!isNew && expensesEntity.expenseMode) || 'NEFT'}
                  >
                    <option value="NEFT">{translate('risingArjunApp.Mode.NEFT')}</option>
                    <option value="UPI">{translate('risingArjunApp.Mode.UPI')}</option>
                    <option value="CASH">{translate('risingArjunApp.Mode.CASH')}</option>
                    <option value="DEBITCARD">{translate('risingArjunApp.Mode.DEBITCARD')}</option>
                    <option value="NETBANKING">{translate('risingArjunApp.Mode.NETBANKING')}</option>
                    <option value="OTHERS">{translate('risingArjunApp.Mode.OTHERS')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="expenses-my-suffix-type">
                    <Translate contentKey="risingArjunApp.expenses.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="expenses-my-suffix-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && expensesEntity.type) || 'OPERATING'}
                  >
                    <option value="OPERATING">{translate('risingArjunApp.ExpensesType.OPERATING')}</option>
                    <option value="MARKETING">{translate('risingArjunApp.ExpensesType.MARKETING')}</option>
                    <option value="FIXASSET">{translate('risingArjunApp.ExpensesType.FIXASSET')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="billLabel" for="bill">
                      <Translate contentKey="risingArjunApp.expenses.bill">Bill</Translate>
                    </Label>
                    <br />
                    {bill ? (
                      <div>
                        <a onClick={openFile(billContentType, bill)}>
                          <img src={`data:${billContentType};base64,${bill}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {billContentType}, {byteSize(bill)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('bill')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_bill" type="file" onChange={this.onBlobChange(true, 'bill')} accept="image/*" />
                    <AvInput type="hidden" name="bill" value={bill} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="expenses-my-suffix-remarks">
                    <Translate contentKey="risingArjunApp.expenses.remarks">Remarks</Translate>
                  </Label>
                  <AvField id="expenses-my-suffix-remarks" type="text" name="remarks" />
                </AvGroup>
                <AvGroup>
                  <Label for="expenses-my-suffix-incurredBy">
                    <Translate contentKey="risingArjunApp.expenses.incurredBy">Incurred By</Translate>
                  </Label>
                  <AvInput id="expenses-my-suffix-incurredBy" type="select" className="form-control" name="incurredById">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.employeeId}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/expenses-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  employees: storeState.employees.entities,
  expensesEntity: storeState.expenses.entity,
  loading: storeState.expenses.loading,
  updating: storeState.expenses.updating,
  updateSuccess: storeState.expenses.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExpensesMySuffixUpdate);
