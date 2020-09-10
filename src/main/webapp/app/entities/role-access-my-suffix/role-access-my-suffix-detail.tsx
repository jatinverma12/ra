import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './role-access-my-suffix.reducer';
import { IRoleAccessMySuffix } from 'app/shared/model/role-access-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRoleAccessMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RoleAccessMySuffixDetail extends React.Component<IRoleAccessMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { roleAccessEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.roleAccess.detail.title">RoleAccess</Translate> [<b>{roleAccessEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="create">
                <Translate contentKey="risingArjunApp.roleAccess.create">Create</Translate>
              </span>
            </dt>
            <dd>{roleAccessEntity.create ? 'true' : 'false'}</dd>
            <dt>
              <span id="read">
                <Translate contentKey="risingArjunApp.roleAccess.read">Read</Translate>
              </span>
            </dt>
            <dd>{roleAccessEntity.read ? 'true' : 'false'}</dd>
            <dt>
              <span id="update">
                <Translate contentKey="risingArjunApp.roleAccess.update">Update</Translate>
              </span>
            </dt>
            <dd>{roleAccessEntity.update ? 'true' : 'false'}</dd>
            <dt>
              <span id="del">
                <Translate contentKey="risingArjunApp.roleAccess.del">Del</Translate>
              </span>
            </dt>
            <dd>{roleAccessEntity.del ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.roleAccess.role">Role</Translate>
            </dt>
            <dd>{roleAccessEntity.roleName ? roleAccessEntity.roleName : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.roleAccess.feature">Feature</Translate>
            </dt>
            <dd>{roleAccessEntity.featureFeatureDetail ? roleAccessEntity.featureFeatureDetail : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/role-access-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/role-access-my-suffix/${roleAccessEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ roleAccess }: IRootState) => ({
  roleAccessEntity: roleAccess.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RoleAccessMySuffixDetail);
