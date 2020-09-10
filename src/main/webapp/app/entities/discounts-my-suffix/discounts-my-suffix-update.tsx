import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAcademicSessionsMySuffix } from 'app/shared/model/academic-sessions-my-suffix.model';
import { getEntities as getAcademicSessions } from 'app/entities/academic-sessions-my-suffix/academic-sessions-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './discounts-my-suffix.reducer';
import { IDiscountsMySuffix } from 'app/shared/model/discounts-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDiscountsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDiscountsMySuffixUpdateState {
  isNew: boolean;
  sessionId: string;
}

export class DiscountsMySuffixUpdate extends React.Component<IDiscountsMySuffixUpdateProps, IDiscountsMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      sessionId: '0',
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

    this.props.getAcademicSessions();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { discountsEntity } = this.props;
      const entity = {
        ...discountsEntity,
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
    this.props.history.push('/entity/discounts-my-suffix');
  };

  render() {
    const { discountsEntity, academicSessions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.discounts.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.discounts.home.createOrEditLabel">Create or edit a Discounts</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : discountsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="discounts-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="discounts-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="subject2Label" for="discounts-my-suffix-subject2">
                    <Translate contentKey="risingArjunApp.discounts.subject2">Subject 2</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject2" type="string" className="form-control" name="subject2" />
                </AvGroup>
                <AvGroup>
                  <Label id="subject3Label" for="discounts-my-suffix-subject3">
                    <Translate contentKey="risingArjunApp.discounts.subject3">Subject 3</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject3" type="string" className="form-control" name="subject3" />
                </AvGroup>
                <AvGroup>
                  <Label id="subject4Label" for="discounts-my-suffix-subject4">
                    <Translate contentKey="risingArjunApp.discounts.subject4">Subject 4</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject4" type="string" className="form-control" name="subject4" />
                </AvGroup>
                <AvGroup>
                  <Label id="subject5Label" for="discounts-my-suffix-subject5">
                    <Translate contentKey="risingArjunApp.discounts.subject5">Subject 5</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject5" type="string" className="form-control" name="subject5" />
                </AvGroup>
                <AvGroup>
                  <Label id="subject6Label" for="discounts-my-suffix-subject6">
                    <Translate contentKey="risingArjunApp.discounts.subject6">Subject 6</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject6" type="string" className="form-control" name="subject6" />
                </AvGroup>
                <AvGroup>
                  <Label id="subject7Label" for="discounts-my-suffix-subject7">
                    <Translate contentKey="risingArjunApp.discounts.subject7">Subject 7</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject7" type="string" className="form-control" name="subject7" />
                </AvGroup>
                <AvGroup>
                  <Label id="subject8Label" for="discounts-my-suffix-subject8">
                    <Translate contentKey="risingArjunApp.discounts.subject8">Subject 8</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-subject8" type="string" className="form-control" name="subject8" />
                </AvGroup>
                <AvGroup>
                  <Label id="quarterlyLabel" for="discounts-my-suffix-quarterly">
                    <Translate contentKey="risingArjunApp.discounts.quarterly">Quarterly</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-quarterly" type="string" className="form-control" name="quarterly" />
                </AvGroup>
                <AvGroup>
                  <Label id="halfYearlyLabel" for="discounts-my-suffix-halfYearly">
                    <Translate contentKey="risingArjunApp.discounts.halfYearly">Half Yearly</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-halfYearly" type="string" className="form-control" name="halfYearly" />
                </AvGroup>
                <AvGroup>
                  <Label id="annuallyLabel" for="discounts-my-suffix-annually">
                    <Translate contentKey="risingArjunApp.discounts.annually">Annually</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-annually" type="string" className="form-control" name="annually" />
                </AvGroup>
                <AvGroup>
                  <Label id="siblingLabel" for="discounts-my-suffix-sibling">
                    <Translate contentKey="risingArjunApp.discounts.sibling">Sibling</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-sibling" type="string" className="form-control" name="sibling" />
                </AvGroup>
                <AvGroup>
                  <Label id="referralLabel" for="discounts-my-suffix-referral">
                    <Translate contentKey="risingArjunApp.discounts.referral">Referral</Translate>
                  </Label>
                  <AvField id="discounts-my-suffix-referral" type="string" className="form-control" name="referral" />
                </AvGroup>
                <AvGroup>
                  <Label for="discounts-my-suffix-session">
                    <Translate contentKey="risingArjunApp.discounts.session">Session</Translate>
                  </Label>
                  <AvInput id="discounts-my-suffix-session" type="select" className="form-control" name="sessionId">
                    <option value="" key="0" />
                    {academicSessions
                      ? academicSessions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.acadSession}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/discounts-my-suffix" replace color="info">
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
  academicSessions: storeState.academicSessions.entities,
  discountsEntity: storeState.discounts.entity,
  loading: storeState.discounts.loading,
  updating: storeState.discounts.updating,
  updateSuccess: storeState.discounts.updateSuccess
});

const mapDispatchToProps = {
  getAcademicSessions,
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
)(DiscountsMySuffixUpdate);
