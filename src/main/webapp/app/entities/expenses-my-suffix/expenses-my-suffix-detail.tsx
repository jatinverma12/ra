import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './expenses-my-suffix.reducer';
import { IExpensesMySuffix } from 'app/shared/model/expenses-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExpensesMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ExpensesMySuffixDetail extends React.Component<IExpensesMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { expensesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.expenses.detail.title">Expenses</Translate> [<b>{expensesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="item">
                <Translate contentKey="risingArjunApp.expenses.item">Item</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.item}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="risingArjunApp.expenses.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.quantity}</dd>
            <dt>
              <span id="rate">
                <Translate contentKey="risingArjunApp.expenses.rate">Rate</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.rate}</dd>
            <dt>
              <span id="laborCost">
                <Translate contentKey="risingArjunApp.expenses.laborCost">Labor Cost</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.laborCost}</dd>
            <dt>
              <span id="otherExpense">
                <Translate contentKey="risingArjunApp.expenses.otherExpense">Other Expense</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.otherExpense}</dd>
            <dt>
              <span id="total">
                <Translate contentKey="risingArjunApp.expenses.total">Total</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.total}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="risingArjunApp.expenses.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={expensesEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="transactionId">
                <Translate contentKey="risingArjunApp.expenses.transactionId">Transaction Id</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.transactionId}</dd>
            <dt>
              <span id="expenseMode">
                <Translate contentKey="risingArjunApp.expenses.expenseMode">Expense Mode</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.expenseMode}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="risingArjunApp.expenses.type">Type</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.type}</dd>
            <dt>
              <span id="bill">
                <Translate contentKey="risingArjunApp.expenses.bill">Bill</Translate>
              </span>
            </dt>
            <dd>
              {expensesEntity.bill ? (
                <div>
                  <a onClick={openFile(expensesEntity.billContentType, expensesEntity.bill)}>
                    <img src={`data:${expensesEntity.billContentType};base64,${expensesEntity.bill}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {expensesEntity.billContentType}, {byteSize(expensesEntity.bill)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="risingArjunApp.expenses.remarks">Remarks</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.remarks}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.expenses.incurredBy">Incurred By</Translate>
            </dt>
            <dd>{expensesEntity.incurredByEmployeeId ? expensesEntity.incurredByEmployeeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/expenses-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/expenses-my-suffix/${expensesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ expenses }: IRootState) => ({
  expensesEntity: expenses.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExpensesMySuffixDetail);
