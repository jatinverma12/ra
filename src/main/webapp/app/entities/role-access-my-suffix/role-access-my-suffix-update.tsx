import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IJhiauthorityMySuffix } from 'app/shared/model/jhiauthority-my-suffix.model';
import { getEntities as getJhiauthorities } from 'app/entities/jhiauthority-my-suffix/jhiauthority-my-suffix.reducer';
import { IFeaturesMySuffix } from 'app/shared/model/features-my-suffix.model';
import { getEntities as getFeatures } from 'app/entities/features-my-suffix/features-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './role-access-my-suffix.reducer';
import { IRoleAccessMySuffix } from 'app/shared/model/role-access-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRoleAccessMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRoleAccessMySuffixUpdateState {
  isNew: boolean;
  roleId: string;
  featureId: string;
}

export class RoleAccessMySuffixUpdate extends React.Component<IRoleAccessMySuffixUpdateProps, IRoleAccessMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      roleId: '0',
      featureId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getJhiauthorities();
    this.props.getFeatures();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { roleAccessEntity } = this.props;
      const entity = {
        ...roleAccessEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/role-access-my-suffix');
  };

  render() {
    const { roleAccessEntity, jhiauthorities, features, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.roleAccess.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.roleAccess.home.createOrEditLabel">Create or edit a RoleAccess</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : roleAccessEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="role-access-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="role-access-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="createLabel" check>
                    <AvInput id="role-access-my-suffix-create" type="checkbox" className="form-control" name="create" />
                    <Translate contentKey="risingArjunApp.roleAccess.create">Create</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="readLabel" check>
                    <AvInput id="role-access-my-suffix-read" type="checkbox" className="form-control" name="read" />
                    <Translate contentKey="risingArjunApp.roleAccess.read">Read</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="updateLabel" check>
                    <AvInput id="role-access-my-suffix-update" type="checkbox" className="form-control" name="update" />
                    <Translate contentKey="risingArjunApp.roleAccess.update">Update</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="delLabel" check>
                    <AvInput id="role-access-my-suffix-del" type="checkbox" className="form-control" name="del" />
                    <Translate contentKey="risingArjunApp.roleAccess.del">Del</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="role-access-my-suffix-role">
                    <Translate contentKey="risingArjunApp.roleAccess.role">Role</Translate>
                  </Label>
                  <AvInput id="role-access-my-suffix-role" type="select" className="form-control" name="roleId">
                    <option value="" key="0" />
                    {jhiauthorities
                      ? jhiauthorities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="role-access-my-suffix-feature">
                    <Translate contentKey="risingArjunApp.roleAccess.feature">Feature</Translate>
                  </Label>
                  <AvInput id="role-access-my-suffix-feature" type="select" className="form-control" name="featureId">
                    <option value="" key="0" />
                    {features
                      ? features.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.featureDetail}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/role-access-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  jhiauthorities: storeState.jhiauthority.entities,
  features: storeState.features.entities,
  roleAccessEntity: storeState.roleAccess.entity,
  loading: storeState.roleAccess.loading,
  updating: storeState.roleAccess.updating,
  updateSuccess: storeState.roleAccess.updateSuccess
});

const mapDispatchToProps = {
  getJhiauthorities,
  getFeatures,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RoleAccessMySuffixUpdate);
