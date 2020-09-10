import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICenterHeadMySuffix } from 'app/shared/model/center-head-my-suffix.model';
import { getEntities as getCenterHeads } from 'app/entities/center-head-my-suffix/center-head-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './centers-my-suffix.reducer';
import { ICentersMySuffix } from 'app/shared/model/centers-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICentersMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICentersMySuffixUpdateState {
  isNew: boolean;
  centerheadId: string;
}

export class CentersMySuffixUpdate extends React.Component<ICentersMySuffixUpdateProps, ICentersMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      centerheadId: '0',
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

    this.props.getCenterHeads();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { centersEntity } = this.props;
      const entity = {
        ...centersEntity,
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
    this.props.history.push('/entity/centers-my-suffix');
  };

  render() {
    const { centersEntity, centerHeads, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.centers.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.centers.home.createOrEditLabel">Create or edit a Centers</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : centersEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="centers-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="centers-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="centerCodeLabel" for="centers-my-suffix-centerCode">
                    <Translate contentKey="risingArjunApp.centers.centerCode">Center Code</Translate>
                  </Label>
                  <AvField
                    id="centers-my-suffix-centerCode"
                    type="text"
                    name="centerCode"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="centerTitleLabel" for="centers-my-suffix-centerTitle">
                    <Translate contentKey="risingArjunApp.centers.centerTitle">Center Title</Translate>
                  </Label>
                  <AvField
                    id="centers-my-suffix-centerTitle"
                    type="text"
                    name="centerTitle"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="streetNoLabel" for="centers-my-suffix-streetNo">
                    <Translate contentKey="risingArjunApp.centers.streetNo">Street No</Translate>
                  </Label>
                  <AvField id="centers-my-suffix-streetNo" type="string" className="form-control" name="streetNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="centers-my-suffix-city">
                    <Translate contentKey="risingArjunApp.centers.city">City</Translate>
                  </Label>
                  <AvInput
                    id="centers-my-suffix-city"
                    type="select"
                    className="form-control"
                    name="city"
                    value={(!isNew && centersEntity.city) || 'GURGAON'}
                  >
                    <option value="GURGAON">{translate('risingArjunApp.City.GURGAON')}</option>
                    <option value="DELHI">{translate('risingArjunApp.City.DELHI')}</option>
                    <option value="DEHRADUN">{translate('risingArjunApp.City.DEHRADUN')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="stateLabel" for="centers-my-suffix-state">
                    <Translate contentKey="risingArjunApp.centers.state">State</Translate>
                  </Label>
                  <AvInput
                    id="centers-my-suffix-state"
                    type="select"
                    className="form-control"
                    name="state"
                    value={(!isNew && centersEntity.state) || 'DELHI'}
                  >
                    <option value="DELHI">{translate('risingArjunApp.State.DELHI')}</option>
                    <option value="HARYANA">{translate('risingArjunApp.State.HARYANA')}</option>
                    <option value="UTTRAKHAND">{translate('risingArjunApp.State.UTTRAKHAND')}</option>
                    <option value="MADHYAPRADESH">{translate('risingArjunApp.State.MADHYAPRADESH')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="pincodeLabel" for="centers-my-suffix-pincode">
                    <Translate contentKey="risingArjunApp.centers.pincode">Pincode</Translate>
                  </Label>
                  <AvField id="centers-my-suffix-pincode" type="string" className="form-control" name="pincode" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/centers-my-suffix" replace color="info">
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
  centerHeads: storeState.centerHead.entities,
  centersEntity: storeState.centers.entity,
  loading: storeState.centers.loading,
  updating: storeState.centers.updating,
  updateSuccess: storeState.centers.updateSuccess
});

const mapDispatchToProps = {
  getCenterHeads,
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
)(CentersMySuffixUpdate);
