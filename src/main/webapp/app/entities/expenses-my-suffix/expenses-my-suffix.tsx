import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './expenses-my-suffix.reducer';
import { IExpensesMySuffix } from 'app/shared/model/expenses-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IExpensesMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IExpensesMySuffixState = IPaginationBaseState;

export class ExpensesMySuffix extends React.Component<IExpensesMySuffixProps, IExpensesMySuffixState> {
  state: IExpensesMySuffixState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { expensesList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="expenses-my-suffix-heading">
          <Translate contentKey="risingArjunApp.expenses.home.title">Expenses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.expenses.home.createLabel">Create new Expenses</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {expensesList && expensesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('item')}>
                    <Translate contentKey="risingArjunApp.expenses.item">Item</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('quantity')}>
                    <Translate contentKey="risingArjunApp.expenses.quantity">Quantity</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('rate')}>
                    <Translate contentKey="risingArjunApp.expenses.rate">Rate</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('laborCost')}>
                    <Translate contentKey="risingArjunApp.expenses.laborCost">Labor Cost</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('otherExpense')}>
                    <Translate contentKey="risingArjunApp.expenses.otherExpense">Other Expense</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('total')}>
                    <Translate contentKey="risingArjunApp.expenses.total">Total</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('date')}>
                    <Translate contentKey="risingArjunApp.expenses.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('transactionId')}>
                    <Translate contentKey="risingArjunApp.expenses.transactionId">Transaction Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('expenseMode')}>
                    <Translate contentKey="risingArjunApp.expenses.expenseMode">Expense Mode</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('type')}>
                    <Translate contentKey="risingArjunApp.expenses.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('bill')}>
                    <Translate contentKey="risingArjunApp.expenses.bill">Bill</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="risingArjunApp.expenses.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.expenses.incurredBy">Incurred By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {expensesList.map((expenses, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${expenses.id}`} color="link" size="sm">
                        {expenses.id}
                      </Button>
                    </td>
                    <td>{expenses.item}</td>
                    <td>{expenses.quantity}</td>
                    <td>{expenses.rate}</td>
                    <td>{expenses.laborCost}</td>
                    <td>{expenses.otherExpense}</td>
                    <td>{expenses.total}</td>
                    <td>
                      <TextFormat type="date" value={expenses.date} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{expenses.transactionId}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.Mode.${expenses.expenseMode}`} />
                    </td>
                    <td>
                      <Translate contentKey={`risingArjunApp.ExpensesType.${expenses.type}`} />
                    </td>
                    <td>
                      {expenses.bill ? (
                        <div>
                          <a onClick={openFile(expenses.billContentType, expenses.bill)}>
                            <img src={`data:${expenses.billContentType};base64,${expenses.bill}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {expenses.billContentType}, {byteSize(expenses.bill)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{expenses.remarks}</td>
                    <td>
                      {expenses.incurredByEmployeeId ? (
                        <Link to={`employees-my-suffix/${expenses.incurredById}`}>{expenses.incurredByEmployeeId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${expenses.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${expenses.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${expenses.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="risingArjunApp.expenses.home.notFound">No Expenses found</Translate>
            </div>
          )}
        </div>
        <div className={expensesList && expensesList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ expenses }: IRootState) => ({
  expensesList: expenses.entities,
  totalItems: expenses.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExpensesMySuffix);
