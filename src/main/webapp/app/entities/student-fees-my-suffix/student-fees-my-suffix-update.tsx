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
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { getEntities as getSubjects } from 'app/entities/subjects-my-suffix/subjects-my-suffix.reducer';
import { IAcademicSessionsMySuffix } from 'app/shared/model/academic-sessions-my-suffix.model';
import { getEntities as getAcademicSessions } from 'app/entities/academic-sessions-my-suffix/academic-sessions-my-suffix.reducer';
import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
import { getEntities as getEmployees } from 'app/entities/employees-my-suffix/employees-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './student-fees-my-suffix.reducer';
import { IStudentFeesMySuffix } from 'app/shared/model/student-fees-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudentFeesMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudentFeesMySuffixUpdateState {
  isNew: boolean;
  registrationnoId: string;
  subjectId: string;
  sessionId: string;
  teacherId: string;
}

export class StudentFeesMySuffixUpdate extends React.Component<IStudentFeesMySuffixUpdateProps, IStudentFeesMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      registrationnoId: '0',
      subjectId: '0',
      sessionId: '0',
      teacherId: '0',
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
    this.props.getSubjects();
    this.props.getAcademicSessions();
    this.props.getEmployees();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { studentFeesEntity } = this.props;
      const entity = {
        ...studentFeesEntity,
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
    this.props.history.push('/entity/student-fees-my-suffix');
  };

  render() {
    const { studentFeesEntity, students, subjects, academicSessions, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.studentFees.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.studentFees.home.createOrEditLabel">Create or edit a StudentFees</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studentFeesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="student-fees-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="student-fees-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="feeLabel" for="student-fees-my-suffix-fee">
                    <Translate contentKey="risingArjunApp.studentFees.fee">Fee</Translate>
                  </Label>
                  <AvField id="student-fees-my-suffix-fee" type="string" className="form-control" name="fee" />
                </AvGroup>
                <AvGroup>
                  <Label id="feeCorrectionLabel" for="student-fees-my-suffix-feeCorrection">
                    <Translate contentKey="risingArjunApp.studentFees.feeCorrection">Fee Correction</Translate>
                  </Label>
                  <AvField id="student-fees-my-suffix-feeCorrection" type="string" className="form-control" name="feeCorrection" />
                </AvGroup>
                <AvGroup>
                  <Label id="monthLabel" for="student-fees-my-suffix-month">
                    <Translate contentKey="risingArjunApp.studentFees.month">Month</Translate>
                  </Label>
                  <AvInput
                    id="student-fees-my-suffix-month"
                    type="select"
                    className="form-control"
                    name="month"
                    value={(!isNew && studentFeesEntity.month) || 'JAN'}
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
                  <Label id="feeStatusLabel" check>
                    <AvInput id="student-fees-my-suffix-feeStatus" type="checkbox" className="form-control" name="feeStatus" />
                    <Translate contentKey="risingArjunApp.studentFees.feeStatus">Fee Status</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="student-fees-my-suffix-remarks">
                    <Translate contentKey="risingArjunApp.studentFees.remarks">Remarks</Translate>
                  </Label>
                  <AvField id="student-fees-my-suffix-remarks" type="text" name="remarks" />
                </AvGroup>
                <AvGroup>
                  <Label for="student-fees-my-suffix-registrationno">
                    <Translate contentKey="risingArjunApp.studentFees.registrationno">Registrationno</Translate>
                  </Label>
                  <AvInput id="student-fees-my-suffix-registrationno" type="select" className="form-control" name="registrationnoId">
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
                  <Label for="student-fees-my-suffix-subject">
                    <Translate contentKey="risingArjunApp.studentFees.subject">Subject</Translate>
                  </Label>
                  <AvInput id="student-fees-my-suffix-subject" type="select" className="form-control" name="subjectId">
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
                  <Label for="student-fees-my-suffix-session">
                    <Translate contentKey="risingArjunApp.studentFees.session">Session</Translate>
                  </Label>
                  <AvInput id="student-fees-my-suffix-session" type="select" className="form-control" name="sessionId">
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
                  <Label for="student-fees-my-suffix-teacher">
                    <Translate contentKey="risingArjunApp.studentFees.teacher">Teacher</Translate>
                  </Label>
                  <AvInput id="student-fees-my-suffix-teacher" type="select" className="form-control" name="teacherId">
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
                <Button tag={Link} id="cancel-save" to="/entity/student-fees-my-suffix" replace color="info">
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
  subjects: storeState.subjects.entities,
  academicSessions: storeState.academicSessions.entities,
  employees: storeState.employees.entities,
  studentFeesEntity: storeState.studentFees.entity,
  loading: storeState.studentFees.loading,
  updating: storeState.studentFees.updating,
  updateSuccess: storeState.studentFees.updateSuccess
});

const mapDispatchToProps = {
  getStudents,
  getSubjects,
  getAcademicSessions,
  getEmployees,
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
)(StudentFeesMySuffixUpdate);
