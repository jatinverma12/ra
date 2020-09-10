import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
import { getEntities as getEmployees } from 'app/entities/employees-my-suffix/employees-my-suffix.reducer';
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { getEntities as getSubjects } from 'app/entities/subjects-my-suffix/subjects-my-suffix.reducer';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
import { getEntities as getCourses } from 'app/entities/courses-my-suffix/courses-my-suffix.reducer';
import { IAcademicSessionsMySuffix } from 'app/shared/model/academic-sessions-my-suffix.model';
import { getEntities as getAcademicSessions } from 'app/entities/academic-sessions-my-suffix/academic-sessions-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './teachers-share-my-suffix.reducer';
import { ITeachersShareMySuffix } from 'app/shared/model/teachers-share-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITeachersShareMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITeachersShareMySuffixUpdateState {
  isNew: boolean;
  teacherId: string;
  subjectId: string;
  courseId: string;
  sessionId: string;
}

export class TeachersShareMySuffixUpdate extends React.Component<ITeachersShareMySuffixUpdateProps, ITeachersShareMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      teacherId: '0',
      subjectId: '0',
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

    this.props.getEmployees();
    this.props.getSubjects();
    this.props.getCourses();
    this.props.getAcademicSessions();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { teachersShareEntity } = this.props;
      const entity = {
        ...teachersShareEntity,
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
    this.props.history.push('/entity/teachers-share-my-suffix');
  };

  render() {
    const { teachersShareEntity, employees, subjects, courses, academicSessions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.teachersShare.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.teachersShare.home.createOrEditLabel">Create or edit a TeachersShare</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : teachersShareEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="teachers-share-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="teachers-share-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="shareLabel" for="teachers-share-my-suffix-share">
                    <Translate contentKey="risingArjunApp.teachersShare.share">Share</Translate>
                  </Label>
                  <AvField
                    id="teachers-share-my-suffix-share"
                    type="string"
                    className="form-control"
                    name="share"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      max: { value: 100, errorMessage: translate('entity.validation.max', { max: 100 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="plannedClassesLabel" for="teachers-share-my-suffix-plannedClasses">
                    <Translate contentKey="risingArjunApp.teachersShare.plannedClasses">Planned Classes</Translate>
                  </Label>
                  <AvField
                    id="teachers-share-my-suffix-plannedClasses"
                    type="string"
                    className="form-control"
                    name="plannedClasses"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="actualClassesLabel" for="teachers-share-my-suffix-actualClasses">
                    <Translate contentKey="risingArjunApp.teachersShare.actualClasses">Actual Classes</Translate>
                  </Label>
                  <AvField
                    id="teachers-share-my-suffix-actualClasses"
                    type="string"
                    className="form-control"
                    name="actualClasses"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="shareCorrectionLabel" for="teachers-share-my-suffix-shareCorrection">
                    <Translate contentKey="risingArjunApp.teachersShare.shareCorrection">Share Correction</Translate>
                  </Label>
                  <AvField id="teachers-share-my-suffix-shareCorrection" type="string" className="form-control" name="shareCorrection" />
                </AvGroup>
                <AvGroup>
                  <Label id="monthLabel" for="teachers-share-my-suffix-month">
                    <Translate contentKey="risingArjunApp.teachersShare.month">Month</Translate>
                  </Label>
                  <AvInput
                    id="teachers-share-my-suffix-month"
                    type="select"
                    className="form-control"
                    name="month"
                    value={(!isNew && teachersShareEntity.month) || 'JAN'}
                  >
                    <option value="JAN">{translate('risingArjunApp.Month.JAN')}</option>
                    <option value="FEB">{translate('risingArjunApp.Month.FEB')}</option>
                    <option value="MAR">{translate('risingArjunApp.Month.MAR')}</option>
                    <option value="APR">{translate('risingArjunApp.Month.APR')}</option>
                    <option value="MAY">{translate('risingArjunApp.Month.MAY')}</option>
                    <option value="JUN">{translate('risingArjunApp.Month.JUN')}</option>
                    <option value="JUL">{translate('risingArjunApp.Month.JUL')}</option>
                    <option value="AUG">{translate('risingArjunApp.Month.AUG')}</option>
                    <option value="SEP">{translate('risingArjunApp.Month.SEP')}</option>
                    <option value="OCT">{translate('risingArjunApp.Month.OCT')}</option>
                    <option value="NOV">{translate('risingArjunApp.Month.NOV')}</option>
                    <option value="DEC">{translate('risingArjunApp.Month.DEC')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="teachers-share-my-suffix-remarks">
                    <Translate contentKey="risingArjunApp.teachersShare.remarks">Remarks</Translate>
                  </Label>
                  <AvField id="teachers-share-my-suffix-remarks" type="text" name="remarks" />
                </AvGroup>
                <AvGroup>
                  <Label for="teachers-share-my-suffix-teacher">
                    <Translate contentKey="risingArjunApp.teachersShare.teacher">Teacher</Translate>
                  </Label>
                  <AvInput id="teachers-share-my-suffix-teacher" type="select" className="form-control" name="teacherId">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.employeeId}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="teachers-share-my-suffix-subject">
                    <Translate contentKey="risingArjunApp.teachersShare.subject">Subject</Translate>
                  </Label>
                  <AvInput id="teachers-share-my-suffix-subject" type="select" className="form-control" name="subjectId">
                    <option value="" key="0" />
                    {subjects
                      ? subjects.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.subjectTitle}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="teachers-share-my-suffix-course">
                    <Translate contentKey="risingArjunApp.teachersShare.course">Course</Translate>
                  </Label>
                  <AvInput id="teachers-share-my-suffix-course" type="select" className="form-control" name="courseId">
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
                  <Label for="teachers-share-my-suffix-session">
                    <Translate contentKey="risingArjunApp.teachersShare.session">Session</Translate>
                  </Label>
                  <AvInput id="teachers-share-my-suffix-session" type="select" className="form-control" name="sessionId">
                    <option value="" key="0" />
                    {academicSessions
                      ? academicSessions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.acadSessionId}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/teachers-share-my-suffix" replace color="info">
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
  employees: storeState.employees.entities,
  subjects: storeState.subjects.entities,
  courses: storeState.courses.entities,
  academicSessions: storeState.academicSessions.entities,
  teachersShareEntity: storeState.teachersShare.entity,
  loading: storeState.teachersShare.loading,
  updating: storeState.teachersShare.updating,
  updateSuccess: storeState.teachersShare.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
  getSubjects,
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
)(TeachersShareMySuffixUpdate);
