import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './salaries-payment-my-suffix.reducer';
import { ISalariesPaymentMySuffix } from 'app/shared/model/salaries-payment-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISalariesPaymentMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SalariesPaymentMySuffixDetail extends React.Component<ISalariesPaymentMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { salariesPaymentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.salariesPayment.detail.title">SalariesPayment</Translate> [
            <b>{salariesPaymentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="salary">
                <Translate contentKey="risingArjunApp.salariesPayment.salary">Salary</Translate>
              </span>
            </dt>
            <dd>{salariesPaymentEntity.salary}</dd>
            <dt>
              <span id="paid">
                <Translate contentKey="risingArjunApp.salariesPayment.paid">Paid</Translate>
              </span>
            </dt>
            <dd>{salariesPaymentEntity.paid}</dd>
            <dt>
              <span id="unpaid">
                <Translate contentKey="risingArjunApp.salariesPayment.unpaid">Unpaid</Translate>
              </span>
            </dt>
            <dd>{salariesPaymentEntity.unpaid}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="risingArjunApp.salariesPayment.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={salariesPaymentEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="transactionId">
                <Translate contentKey="risingArjunApp.salariesPayment.transactionId">Transaction Id</Translate>
              </span>
            </dt>
            <dd>{salariesPaymentEntity.transactionId}</dd>
            <dt>
              <span id="paymentMode">
                <Translate contentKey="risingArjunApp.salariesPayment.paymentMode">Payment Mode</Translate>
              </span>
            </dt>
            <dd>{salariesPaymentEntity.paymentMode}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="risingArjunApp.salariesPayment.remarks">Remarks</Translate>
              </span>
            </dt>
            <dd>{salariesPaymentEntity.remarks}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.salariesPayment.employee">Employee</Translate>
            </dt>
            <dd>{salariesPaymentEntity.employeeEmployeeId ? salariesPaymentEntity.employeeEmployeeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/salaries-payment-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/salaries-payment-my-suffix/${salariesPaymentEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ salariesPayment }: IRootState) => ({
  salariesPaymentEntity: salariesPayment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SalariesPaymentMySuffixDetail);
