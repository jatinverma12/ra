import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
import { getEntities as getEmployees } from 'app/entities/employees-my-suffix/employees-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './salaries-payment-my-suffix.reducer';
import { ISalariesPaymentMySuffix } from 'app/shared/model/salaries-payment-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISalariesPaymentMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISalariesPaymentMySuffixUpdateState {
  isNew: boolean;
  employeeId: string;
}

export class SalariesPaymentMySuffixUpdate extends React.Component<
  ISalariesPaymentMySuffixUpdateProps,
  ISalariesPaymentMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      employeeId: '0',
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

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { salariesPaymentEntity } = this.props;
      const entity = {
        ...salariesPaymentEntity,
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
    this.props.history.push('/entity/salaries-payment-my-suffix');
  };

  render() {
    const { salariesPaymentEntity, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.salariesPayment.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.salariesPayment.home.createOrEditLabel">Create or edit a SalariesPayment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : salariesPaymentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="salaries-payment-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="salaries-payment-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="salaryLabel" for="salaries-payment-my-suffix-salary">
                    <Translate contentKey="risingArjunApp.salariesPayment.salary">Salary</Translate>
                  </Label>
                  <AvField
                    id="salaries-payment-my-suffix-salary"
                    type="string"
                    className="form-control"
                    name="salary"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="paidLabel" for="salaries-payment-my-suffix-paid">
                    <Translate contentKey="risingArjunApp.salariesPayment.paid">Paid</Translate>
                  </Label>
                  <AvField
                    id="salaries-payment-my-suffix-paid"
                    type="string"
                    className="form-control"
                    name="paid"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="unpaidLabel" for="salaries-payment-my-suffix-unpaid">
                    <Translate contentKey="risingArjunApp.salariesPayment.unpaid">Unpaid</Translate>
                  </Label>
                  <AvField
                    id="salaries-payment-my-suffix-unpaid"
                    type="string"
                    className="form-control"
                    name="unpaid"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="salaries-payment-my-suffix-date">
                    <Translate contentKey="risingArjunApp.salariesPayment.date">Date</Translate>
                  </Label>
                  <AvField
                    id="salaries-payment-my-suffix-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="transactionIdLabel" for="salaries-payment-my-suffix-transactionId">
                    <Translate contentKey="risingArjunApp.salariesPayment.transactionId">Transaction Id</Translate>
                  </Label>
                  <AvField
                    id="salaries-payment-my-suffix-transactionId"
                    type="text"
                    name="transactionId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentModeLabel" for="salaries-payment-my-suffix-paymentMode">
                    <Translate contentKey="risingArjunApp.salariesPayment.paymentMode">Payment Mode</Translate>
                  </Label>
                  <AvInput
                    id="salaries-payment-my-suffix-paymentMode"
                    type="select"
                    className="form-control"
                    name="paymentMode"
                    value={(!isNew && salariesPaymentEntity.paymentMode) || 'NEFT'}
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
                  <Label id="remarksLabel" for="salaries-payment-my-suffix-remarks">
                    <Translate contentKey="risingArjunApp.salariesPayment.remarks">Remarks</Translate>
                  </Label>
                  <AvField id="salaries-payment-my-suffix-remarks" type="text" name="remarks" />
                </AvGroup>
                <AvGroup>
                  <Label for="salaries-payment-my-suffix-employee">
                    <Translate contentKey="risingArjunApp.salariesPayment.employee">Employee</Translate>
                  </Label>
                  <AvInput id="salaries-payment-my-suffix-employee" type="select" className="form-control" name="employeeId">
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
                <Button tag={Link} id="cancel-save" to="/entity/salaries-payment-my-suffix" replace color="info">
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
  salariesPaymentEntity: storeState.salariesPayment.entity,
  loading: storeState.salariesPayment.loading,
  updating: storeState.salariesPayment.updating,
  updateSuccess: storeState.salariesPayment.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SalariesPaymentMySuffixUpdate);
