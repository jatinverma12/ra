import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStudentsMySuffix } from 'app/shared/model/students-my-suffix.model';
import { getEntities as getStudents } from 'app/entities/students-my-suffix/students-my-suffix.reducer';
import { IAcademicSessionsMySuffix } from 'app/shared/model/academic-sessions-my-suffix.model';
import { getEntities as getAcademicSessions } from 'app/entities/academic-sessions-my-suffix/academic-sessions-my-suffix.reducer';
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { getEntities as getSubjects } from 'app/entities/subjects-my-suffix/subjects-my-suffix.reducer';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
import { getEntities as getCourses } from 'app/entities/courses-my-suffix/courses-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './students-subjects-my-suffix.reducer';
import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudentsSubjectsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudentsSubjectsMySuffixUpdateState {
  isNew: boolean;
  idssubjects: any[];
  idscourse: any[];
  registrationnoId: string;
  sessionId: string;
}

export class StudentsSubjectsMySuffixUpdate extends React.Component<
  IStudentsSubjectsMySuffixUpdateProps,
  IStudentsSubjectsMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      idssubjects: [],
      idscourse: [],
      registrationnoId: '0',
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

    this.props.getStudents();
    this.props.getAcademicSessions();
    this.props.getSubjects();
    this.props.getCourses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { studentsSubjectsEntity } = this.props;
      const entity = {
        ...studentsSubjectsEntity,
        ...values,
        subjects: mapIdList(values.subjects),
        courses: mapIdList(values.courses)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/students-subjects-my-suffix');
  };

  render() {
    const { studentsSubjectsEntity, students, academicSessions, subjects, courses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.studentsSubjects.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.studentsSubjects.home.createOrEditLabel">Create or edit a StudentsSubjects</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studentsSubjectsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="students-subjects-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="students-subjects-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="monthLabel" for="students-subjects-my-suffix-month">
                    <Translate contentKey="risingArjunApp.studentsSubjects.month">Month</Translate>
                  </Label>
                  <AvInput
                    id="students-subjects-my-suffix-month"
                    type="select"
                    className="form-control"
                    name="month"
                    value={(!isNew && studentsSubjectsEntity.month) || 'JAN'}
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
                  <Label for="students-subjects-my-suffix-registrationno">
                    <Translate contentKey="risingArjunApp.studentsSubjects.registrationno">Registrationno</Translate>
                  </Label>
                  <AvInput id="students-subjects-my-suffix-registrationno" type="select" className="form-control" name="registrationnoId">
                    <option value="" key="0" />
                    {students
                      ? students.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.studentRegId}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="students-subjects-my-suffix-session">
                    <Translate contentKey="risingArjunApp.studentsSubjects.session">Session</Translate>
                  </Label>
                  <AvInput id="students-subjects-my-suffix-session" type="select" className="form-control" name="sessionId">
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
                <AvGroup>
                  <Label for="students-subjects-my-suffix-subjects">
                    <Translate contentKey="risingArjunApp.studentsSubjects.subjects">Subjects</Translate>
                  </Label>
                  <AvInput
                    id="students-subjects-my-suffix-subjects"
                    type="select"
                    multiple
                    className="form-control"
                    name="subjects"
                    value={studentsSubjectsEntity.subjects && studentsSubjectsEntity.subjects.map(e => e.id)}
                  >
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
                  <Label for="students-subjects-my-suffix-course">
                    <Translate contentKey="risingArjunApp.studentsSubjects.course">Course</Translate>
                  </Label>
                  <AvInput
                    id="students-subjects-my-suffix-course"
                    type="select"
                    multiple
                    className="form-control"
                    name="courses"
                    value={studentsSubjectsEntity.courses && studentsSubjectsEntity.courses.map(e => e.id)}
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/students-subjects-my-suffix" replace color="info">
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
  students: storeState.students.entities,
  academicSessions: storeState.academicSessions.entities,
  subjects: storeState.subjects.entities,
  courses: storeState.courses.entities,
  studentsSubjectsEntity: storeState.studentsSubjects.entity,
  loading: storeState.studentsSubjects.loading,
  updating: storeState.studentsSubjects.updating,
  updateSuccess: storeState.studentsSubjects.updateSuccess
});

const mapDispatchToProps = {
  getStudents,
  getAcademicSessions,
  getSubjects,
  getCourses,
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
)(StudentsSubjectsMySuffixUpdate);
