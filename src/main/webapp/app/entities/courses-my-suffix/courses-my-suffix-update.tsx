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
import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
import { getEntities as getStudentsSubjects } from 'app/entities/students-subjects-my-suffix/students-subjects-my-suffix.reducer';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';
import { getEntities as getTeachers } from 'app/entities/teachers-my-suffix/teachers-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './courses-my-suffix.reducer';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICoursesMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICoursesMySuffixUpdateState {
  isNew: boolean;
  studentsId: string;
  studentsubjectsId: string;
  teachersId: string;
}

export class CoursesMySuffixUpdate extends React.Component<ICoursesMySuffixUpdateProps, ICoursesMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      studentsId: '0',
      studentsubjectsId: '0',
      teachersId: '0',
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
    this.props.getStudentsSubjects();
    this.props.getTeachers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { coursesEntity } = this.props;
      const entity = {
        ...coursesEntity,
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
    this.props.history.push('/entity/courses-my-suffix');
  };

  render() {
    const { coursesEntity, students, studentsSubjects, teachers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.courses.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.courses.home.createOrEditLabel">Create or edit a Courses</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : coursesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="courses-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="courses-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="courseIdLabel" for="courses-my-suffix-courseId">
                    <Translate contentKey="risingArjunApp.courses.courseId">Course Id</Translate>
                  </Label>
                  <AvField
                    id="courses-my-suffix-courseId"
                    type="text"
                    name="courseId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="courseLabel" for="courses-my-suffix-course">
                    <Translate contentKey="risingArjunApp.courses.course">Course</Translate>
                  </Label>
                  <AvField
                    id="courses-my-suffix-course"
                    type="text"
                    name="course"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/courses-my-suffix" replace color="info">
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
  studentsSubjects: storeState.studentsSubjects.entities,
  teachers: storeState.teachers.entities,
  coursesEntity: storeState.courses.entity,
  loading: storeState.courses.loading,
  updating: storeState.courses.updating,
  updateSuccess: storeState.courses.updateSuccess
});

const mapDispatchToProps = {
  getStudents,
  getStudentsSubjects,
  getTeachers,
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
)(CoursesMySuffixUpdate);
