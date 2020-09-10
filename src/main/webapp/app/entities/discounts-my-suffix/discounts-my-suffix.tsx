import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './discounts-my-suffix.reducer';
import { IDiscountsMySuffix } from 'app/shared/model/discounts-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDiscountsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class DiscountsMySuffix extends React.Component<IDiscountsMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { discountsList, match } = this.props;
    return (
      <div>
        <h2 id="discounts-my-suffix-heading">
          <Translate contentKey="risingArjunApp.discounts.home.title">Discounts</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.discounts.home.createLabel">Create new Discounts</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {discountsList && discountsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject2">Subject 2</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject3">Subject 3</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject4">Subject 4</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject5">Subject 5</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject6">Subject 6</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject7">Subject 7</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.subject8">Subject 8</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.quarterly">Quarterly</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.halfYearly">Half Yearly</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.annually">Annually</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.sibling">Sibling</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.referral">Referral</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.discounts.session">Session</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {discountsList.map((discounts, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${discounts.id}`} color="link" size="sm">
                        {discounts.id}
                      </Button>
                    </td>
                    <td>{discounts.subject2}</td>
                    <td>{discounts.subject3}</td>
                    <td>{discounts.subject4}</td>
                    <td>{discounts.subject5}</td>
                    <td>{discounts.subject6}</td>
                    <td>{discounts.subject7}</td>
                    <td>{discounts.subject8}</td>
                    <td>{discounts.quarterly}</td>
                    <td>{discounts.halfYearly}</td>
                    <td>{discounts.annually}</td>
                    <td>{discounts.sibling}</td>
                    <td>{discounts.referral}</td>
                    <td>
                      {discounts.sessionAcadSession ? (
                        <Link to={`academic-sessions-my-suffix/${discounts.sessionId}`}>{discounts.sessionAcadSession}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${discounts.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${discounts.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${discounts.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.discounts.home.notFound">No Discounts found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ discounts }: IRootState) => ({
  discountsList: discounts.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DiscountsMySuffix);
