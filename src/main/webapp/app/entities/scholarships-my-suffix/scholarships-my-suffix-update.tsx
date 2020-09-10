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
import { getEntity, updateEntity, createEntity, reset } from './scholarships-my-suffix.reducer';
import { IScholarshipsMySuffix } from 'app/shared/model/scholarships-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IScholarshipsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IScholarshipsMySuffixUpdateState {
  isNew: boolean;
  sessionId: string;
}

export class ScholarshipsMySuffixUpdate extends React.Component<IScholarshipsMySuffixUpdateProps, IScholarshipsMySuffixUpdateState> {
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
      const { scholarshipsEntity } = this.props;
      const entity = {
        ...scholarshipsEntity,
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
    this.props.history.push('/entity/scholarships-my-suffix');
  };

  render() {
    const { scholarshipsEntity, academicSessions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.scholarships.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.scholarships.home.createOrEditLabel">Create or edit a Scholarships</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : scholarshipsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="scholarships-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="scholarships-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="minMarksLabel" for="scholarships-my-suffix-minMarks">
                    <Translate contentKey="risingArjunApp.scholarships.minMarks">Min Marks</Translate>
                  </Label>
                  <AvField id="scholarships-my-suffix-minMarks" type="string" className="form-control" name="minMarks" />
                </AvGroup>
                <AvGroup>
                  <Label id="percentLabel" for="scholarships-my-suffix-percent">
                    <Translate contentKey="risingArjunApp.scholarships.percent">Percent</Translate>
                  </Label>
                  <AvField id="scholarships-my-suffix-percent" type="string" className="form-control" name="percent" />
                </AvGroup>
                <AvGroup>
                  <Label for="scholarships-my-suffix-session">
                    <Translate contentKey="risingArjunApp.scholarships.session">Session</Translate>
                  </Label>
                  <AvInput id="scholarships-my-suffix-session" type="select" className="form-control" name="sessionId">
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
                <Button tag={Link} id="cancel-save" to="/entity/scholarships-my-suffix" replace color="info">
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
  scholarshipsEntity: storeState.scholarships.entity,
  loading: storeState.scholarships.loading,
  updating: storeState.scholarships.updating,
  updateSuccess: storeState.scholarships.updateSuccess
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
)(ScholarshipsMySuffixUpdate);
