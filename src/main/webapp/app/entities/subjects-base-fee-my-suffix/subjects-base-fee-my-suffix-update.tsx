import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
import { getEntities as getCourses } from 'app/entities/courses-my-suffix/courses-my-suffix.reducer';
import { IAcademicSessionsMySuffix } from 'app/shared/model/academic-sessions-my-suffix.model';
import { getEntities as getAcademicSessions } from 'app/entities/academic-sessions-my-suffix/academic-sessions-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './subjects-base-fee-my-suffix.reducer';
import { ISubjectsBaseFeeMySuffix } from 'app/shared/model/subjects-base-fee-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubjectsBaseFeeMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISubjectsBaseFeeMySuffixUpdateState {
  isNew: boolean;
  courseId: string;
  sessionId: string;
}

export class SubjectsBaseFeeMySuffixUpdate extends React.Component<
  ISubjectsBaseFeeMySuffixUpdateProps,
  ISubjectsBaseFeeMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      courseId: '0',
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

    this.props.getCourses();
    this.props.getAcademicSessions();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { subjectsBaseFeeEntity } = this.props;
      const entity = {
        ...subjectsBaseFeeEntity,
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
    this.props.history.push('/entity/subjects-base-fee-my-suffix');
  };

  render() {
    const { subjectsBaseFeeEntity, courses, academicSessions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.subjectsBaseFee.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.subjectsBaseFee.home.createOrEditLabel">Create or edit a SubjectsBaseFee</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : subjectsBaseFeeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="subjects-base-fee-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="subjects-base-fee-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="baseFeeLabel" for="subjects-base-fee-my-suffix-baseFee">
                    <Translate contentKey="risingArjunApp.subjectsBaseFee.baseFee">Base Fee</Translate>
                  </Label>
                  <AvField id="subjects-base-fee-my-suffix-baseFee" type="string" className="form-control" name="baseFee" />
                </AvGroup>
                <AvGroup>
                  <Label for="subjects-base-fee-my-suffix-course">
                    <Translate contentKey="risingArjunApp.subjectsBaseFee.course">Course</Translate>
                  </Label>
                  <AvInput id="subjects-base-fee-my-suffix-course" type="select" className="form-control" name="courseId">
                    <option value="" key="0" />
                    {courses
                      ? courses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.course}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="subjects-base-fee-my-suffix-session">
                    <Translate contentKey="risingArjunApp.subjectsBaseFee.session">Session</Translate>
                  </Label>
                  <AvInput id="subjects-base-fee-my-suffix-session" type="select" className="form-control" name="sessionId">
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
                <Button tag={Link} id="cancel-save" to="/entity/subjects-base-fee-my-suffix" replace color="info">
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
  courses: storeState.courses.entities,
  academicSessions: storeState.academicSessions.entities,
  subjectsBaseFeeEntity: storeState.subjectsBaseFee.entity,
  loading: storeState.subjectsBaseFee.loading,
  updating: storeState.subjectsBaseFee.updating,
  updateSuccess: storeState.subjectsBaseFee.updateSuccess
});

const mapDispatchToProps = {
  getCourses,
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
)(SubjectsBaseFeeMySuffixUpdate);
