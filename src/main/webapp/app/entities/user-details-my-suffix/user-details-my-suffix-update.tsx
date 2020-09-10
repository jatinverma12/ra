import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-details-my-suffix.reducer';
import { IUserDetailsMySuffix } from 'app/shared/model/user-details-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserDetailsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserDetailsMySuffixUpdateState {
  isNew: boolean;
  userId: string;
}

export class UserDetailsMySuffixUpdate extends React.Component<IUserDetailsMySuffixUpdateProps, IUserDetailsMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
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

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { userDetailsEntity } = this.props;
      const entity = {
        ...userDetailsEntity,
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
    this.props.history.push('/entity/user-details-my-suffix');
  };

  render() {
    const { userDetailsEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.userDetails.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.userDetails.home.createOrEditLabel">Create or edit a UserDetails</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="user-details-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="user-details-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="mobileNoLabel" for="user-details-my-suffix-mobileNo">
                    <Translate contentKey="risingArjunApp.userDetails.mobileNo">Mobile No</Translate>
                  </Label>
                  <AvField
                    id="user-details-my-suffix-mobileNo"
                    type="text"
                    name="mobileNo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dobLabel" for="user-details-my-suffix-dob">
                    <Translate contentKey="risingArjunApp.userDetails.dob">Dob</Translate>
                  </Label>
                  <AvField
                    id="user-details-my-suffix-dob"
                    type="date"
                    className="form-control"
                    name="dob"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="houseNoLabel" for="user-details-my-suffix-houseNo">
                    <Translate contentKey="risingArjunApp.userDetails.houseNo">House No</Translate>
                  </Label>
                  <AvField
                    id="user-details-my-suffix-houseNo"
                    type="text"
                    name="houseNo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="streetNoLabel" for="user-details-my-suffix-streetNo">
                    <Translate contentKey="risingArjunApp.userDetails.streetNo">Street No</Translate>
                  </Label>
                  <AvField id="user-details-my-suffix-streetNo" type="string" className="form-control" name="streetNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="user-details-my-suffix-city">
                    <Translate contentKey="risingArjunApp.userDetails.city">City</Translate>
                  </Label>
                  <AvInput
                    id="user-details-my-suffix-city"
                    type="select"
                    className="form-control"
                    name="city"
                    value={(!isNew && userDetailsEntity.city) || 'GURGAON'}
                  >
                    <option value="GURGAON">{translate('risingArjunApp.City.GURGAON')}</option>
                    <option value="DELHI">{translate('risingArjunApp.City.DELHI')}</option>
                    <option value="DEHRADUN">{translate('risingArjunApp.City.DEHRADUN')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="stateLabel" for="user-details-my-suffix-state">
                    <Translate contentKey="risingArjunApp.userDetails.state">State</Translate>
                  </Label>
                  <AvInput
                    id="user-details-my-suffix-state"
                    type="select"
                    className="form-control"
                    name="state"
                    value={(!isNew && userDetailsEntity.state) || 'DELHI'}
                  >
                    <option value="DELHI">{translate('risingArjunApp.State.DELHI')}</option>
                    <option value="HARYANA">{translate('risingArjunApp.State.HARYANA')}</option>
                    <option value="UTTRAKHAND">{translate('risingArjunApp.State.UTTRAKHAND')}</option>
                    <option value="MADHYAPRADESH">{translate('risingArjunApp.State.MADHYAPRADESH')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="pincodeLabel" for="user-details-my-suffix-pincode">
                    <Translate contentKey="risingArjunApp.userDetails.pincode">Pincode</Translate>
                  </Label>
                  <AvField id="user-details-my-suffix-pincode" type="string" className="form-control" name="pincode" />
                </AvGroup>
                <AvGroup>
                  <Label for="user-details-my-suffix-user">
                    <Translate contentKey="risingArjunApp.userDetails.user">User</Translate>
                  </Label>
                  <AvInput id="user-details-my-suffix-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-details-my-suffix" replace color="info">
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
  users: storeState.userManagement.users,
  userDetailsEntity: storeState.userDetails.entity,
  loading: storeState.userDetails.loading,
  updating: storeState.userDetails.updating,
  updateSuccess: storeState.userDetails.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
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
)(UserDetailsMySuffixUpdate);
