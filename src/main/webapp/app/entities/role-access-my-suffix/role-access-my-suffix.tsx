import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './role-access-my-suffix.reducer';
import { IRoleAccessMySuffix } from 'app/shared/model/role-access-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRoleAccessMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RoleAccessMySuffix extends React.Component<IRoleAccessMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { roleAccessList, match } = this.props;
    return (
      <div>
        <h2 id="role-access-my-suffix-heading">
          <Translate contentKey="risingArjunApp.roleAccess.home.title">Role Accesses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.roleAccess.home.createLabel">Create new Role Access</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {roleAccessList && roleAccessList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.roleAccess.create">Create</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.roleAccess.read">Read</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.roleAccess.update">Update</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.roleAccess.del">Del</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.roleAccess.role">Role</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.roleAccess.feature">Feature</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {roleAccessList.map((roleAccess, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${roleAccess.id}`} color="link" size="sm">
                        {roleAccess.id}
                      </Button>
                    </td>
                    <td>{roleAccess.create ? 'true' : 'false'}</td>
                    <td>{roleAccess.read ? 'true' : 'false'}</td>
                    <td>{roleAccess.update ? 'true' : 'false'}</td>
                    <td>{roleAccess.del ? 'true' : 'false'}</td>
                    <td>
                      {roleAccess.roleName ? <Link to={`jhiauthority-my-suffix/${roleAccess.roleId}`}>{roleAccess.roleName}</Link> : ''}
                    </td>
                    <td>
                      {roleAccess.featureFeatureDetail ? (
                        <Link to={`features-my-suffix/${roleAccess.featureId}`}>{roleAccess.featureFeatureDetail}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${roleAccess.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${roleAccess.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${roleAccess.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.roleAccess.home.notFound">No Role Accesses found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ roleAccess }: IRootState) => ({
  roleAccessList: roleAccess.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RoleAccessMySuffix);
