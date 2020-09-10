import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
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
import { getEntity, updateEntity, createEntity, reset } from './teachers-my-suffix.reducer';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITeachersMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITeachersMySuffixUpdateState {
  isNew: boolean;
  idssubjects: any[];
  idscourses: any[];
  teacherId: string;
}

export class TeachersMySuffixUpdate extends React.Component<ITeachersMySuffixUpdateProps, ITeachersMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idssubjects: [],
      idscourses: [],
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

    this.props.getEmployees();
    this.props.getSubjects();
    this.props.getCourses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { teachersEntity } = this.props;
      const entity = {
        ...teachersEntity,
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
    this.props.history.push('/entity/teachers-my-suffix');
  };

  render() {
    const { teachersEntity, employees, subjects, courses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.teachers.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.teachers.home.createOrEditLabel">Create or edit a Teachers</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : teachersEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="teachers-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="teachers-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="teachers-my-suffix-teacher">
                    <Translate contentKey="risingArjunApp.teachers.teacher">Teacher</Translate>
                  </Label>
                  <AvInput id="teachers-my-suffix-teacher" type="select" className="form-control" name="teacherId">
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
                  <Label for="teachers-my-suffix-subjects">
                    <Translate contentKey="risingArjunApp.teachers.subjects">Subjects</Translate>
                  </Label>
                  <AvInput
                    id="teachers-my-suffix-subjects"
                    type="select"
                    multiple
                    className="form-control"
                    name="subjects"
                    value={teachersEntity.subjects && teachersEntity.subjects.map(e => e.id)}
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
                  <Label for="teachers-my-suffix-courses">
                    <Translate contentKey="risingArjunApp.teachers.courses">Courses</Translate>
                  </Label>
                  <AvInput
                    id="teachers-my-suffix-courses"
                    type="select"
                    multiple
                    className="form-control"
                    name="courses"
                    value={teachersEntity.courses && teachersEntity.courses.map(e => e.id)}
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
                <Button tag={Link} id="cancel-save" to="/entity/teachers-my-suffix" replace color="info">
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
  teachersEntity: storeState.teachers.entity,
  loading: storeState.teachers.loading,
  updating: storeState.teachers.updating,
  updateSuccess: storeState.teachers.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
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
)(TeachersMySuffixUpdate);
