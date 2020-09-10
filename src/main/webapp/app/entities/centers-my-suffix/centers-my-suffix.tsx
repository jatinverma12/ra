import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './centers-my-suffix.reducer';
import { ICentersMySuffix } from 'app/shared/model/centers-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICentersMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class CentersMySuffix extends React.Component<ICentersMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { centersList, match } = this.props;
    return (
      <div>
        <h2 id="centers-my-suffix-heading">
          <Translate contentKey="risingArjunApp.centers.home.title">Centers</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.centers.home.createLabel">Create new Centers</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {centersList && centersList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centers.centerCode">Center Code</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centers.centerTitle">Center Title</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centers.streetNo">Street No</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centers.city">City</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centers.state">State</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centers.pincode">Pincode</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {centersList.map((centers, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${centers.id}`} color="link" size="sm">
                        {centers.id}
                      </Button>
                    </td>
                    <td>{centers.centerCode}</td>
                    <td>{centers.centerTitle}</td>
                    <td>{centers.streetNo}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.City.${centers.city}`} />
                    </td>
                    <td>
                      <Translate contentKey={`risingArjunApp.State.${centers.state}`} />
                    </td>
                    <td>{centers.pincode}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${centers.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${centers.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${centers.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.centers.home.notFound">No Centers found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ centers }: IRootState) => ({
  centersList: centers.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CentersMySuffix);
