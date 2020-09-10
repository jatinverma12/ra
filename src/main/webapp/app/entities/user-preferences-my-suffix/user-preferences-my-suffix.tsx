import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-preferences-my-suffix.reducer';
import { IUserPreferencesMySuffix } from 'app/shared/model/user-preferences-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserPreferencesMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class UserPreferencesMySuffix extends React.Component<IUserPreferencesMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { userPreferencesList, match } = this.props;
    return (
      <div>
        <h2 id="user-preferences-my-suffix-heading">
          <Translate contentKey="risingArjunApp.userPreferences.home.title">User Preferences</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.userPreferences.home.createLabel">Create new User Preferences</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {userPreferencesList && userPreferencesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.userPreferences.theme">Theme</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.userPreferences.user">User</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {userPreferencesList.map((userPreferences, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${userPreferences.id}`} color="link" size="sm">
                        {userPreferences.id}
                      </Button>
                    </td>
                    <td>{userPreferences.theme}</td>
                    <td>{userPreferences.userLogin ? userPreferences.userLogin : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${userPreferences.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${userPreferences.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${userPreferences.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.userPreferences.home.notFound">No User Preferences found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ userPreferences }: IRootState) => ({
  userPreferencesList: userPreferences.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserPreferencesMySuffix);
