import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-preferences-my-suffix.reducer';
import { IUserPreferencesMySuffix } from 'app/shared/model/user-preferences-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserPreferencesMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserPreferencesMySuffixDetail extends React.Component<IUserPreferencesMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userPreferencesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.userPreferences.detail.title">UserPreferences</Translate> [
            <b>{userPreferencesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="theme">
                <Translate contentKey="risingArjunApp.userPreferences.theme">Theme</Translate>
              </span>
            </dt>
            <dd>{userPreferencesEntity.theme}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.userPreferences.user">User</Translate>
            </dt>
            <dd>{userPreferencesEntity.userLogin ? userPreferencesEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-preferences-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-preferences-my-suffix/${userPreferencesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ userPreferences }: IRootState) => ({
  userPreferencesEntity: userPreferences.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserPreferencesMySuffixDetail);
