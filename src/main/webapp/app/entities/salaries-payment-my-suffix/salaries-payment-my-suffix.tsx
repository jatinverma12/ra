import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './salaries-payment-my-suffix.reducer';
import { ISalariesPaymentMySuffix } from 'app/shared/model/salaries-payment-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ISalariesPaymentMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ISalariesPaymentMySuffixState = IPaginationBaseState;

export class SalariesPaymentMySuffix extends React.Component<ISalariesPaymentMySuffixProps, ISalariesPaymentMySuffixState> {
  state: ISalariesPaymentMySuffixState = {
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
    const { salariesPaymentList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="salaries-payment-my-suffix-heading">
          <Translate contentKey="risingArjunApp.salariesPayment.home.title">Salaries Payments</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.salariesPayment.home.createLabel">Create new Salaries Payment</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {salariesPaymentList && salariesPaymentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('salary')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.salary">Salary</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paid')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.paid">Paid</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('unpaid')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.unpaid">Unpaid</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('date')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('transactionId')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.transactionId">Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paymentMode')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.paymentMode">Payment Mode</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="risingArjunApp.salariesPayment.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.salariesPayment.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {salariesPaymentList.map((salariesPayment, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${salariesPayment.id}`} color="link" size="sm">
                        {salariesPayment.id}
                      </Button>
                    </td>
                    <td>{salariesPayment.salary}</td>
                    <td>{salariesPayment.paid}</td>
                    <td>{salariesPayment.unpaid}</td>
                    <td>
                      <TextFormat type="date" value={salariesPayment.date} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{salariesPayment.transactionId}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.Mode.${salariesPayment.paymentMode}`} />
                    </td>
                    <td>{salariesPayment.remarks}</td>
                    <td>
                      {salariesPayment.employeeEmployeeId ? (
                        <Link to={`employees-my-suffix/${salariesPayment.employeeId}`}>{salariesPayment.employeeEmployeeId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${salariesPayment.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${salariesPayment.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${salariesPayment.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.salariesPayment.home.notFound">No Salaries Payments found</Translate>
            </div>
          )}
        </div>
        <div className={salariesPaymentList && salariesPaymentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ salariesPayment }: IRootState) => ({
  salariesPaymentList: salariesPayment.entities,
  totalItems: salariesPayment.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SalariesPaymentMySuffix);
