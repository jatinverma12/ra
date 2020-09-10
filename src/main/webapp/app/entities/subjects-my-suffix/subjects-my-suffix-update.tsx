import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
import { getEntities as getStudentsSubjects } from 'app/entities/students-subjects-my-suffix/students-subjects-my-suffix.reducer';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';
import { getEntities as getTeachers } from 'app/entities/teachers-my-suffix/teachers-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './subjects-my-suffix.reducer';
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubjectsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISubjectsMySuffixUpdateState {
  isNew: boolean;
  studentsubjectId: string;
  teachersId: string;
}

export class SubjectsMySuffixUpdate extends React.Component<ISubjectsMySuffixUpdateProps, ISubjectsMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      studentsubjectId: '0',
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

    this.props.getStudentsSubjects();
    this.props.getTeachers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { subjectsEntity } = this.props;
      const entity = {
        ...subjectsEntity,
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
    this.props.history.push('/entity/subjects-my-suffix');
  };

  render() {
    const { subjectsEntity, studentsSubjects, teachers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.subjects.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.subjects.home.createOrEditLabel">Create or edit a Subjects</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : subjectsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="subjects-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="subjects-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="subjectCodeLabel" for="subjects-my-suffix-subjectCode">
                    <Translate contentKey="risingArjunApp.subjects.subjectCode">Subject Code</Translate>
                  </Label>
                  <AvField
                    id="subjects-my-suffix-subjectCode"
                    type="text"
                    name="subjectCode"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="subjectTitleLabel" for="subjects-my-suffix-subjectTitle">
                    <Translate contentKey="risingArjunApp.subjects.subjectTitle">Subject Title</Translate>
                  </Label>
                  <AvField
                    id="subjects-my-suffix-subjectTitle"
                    type="text"
                    name="subjectTitle"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/subjects-my-suffix" replace color="info">
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
  studentsSubjects: storeState.studentsSubjects.entities,
  teachers: storeState.teachers.entities,
  subjectsEntity: storeState.subjects.entity,
  loading: storeState.subjects.loading,
  updating: storeState.subjects.updating,
  updateSuccess: storeState.subjects.updateSuccess
});

const mapDispatchToProps = {
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
)(SubjectsMySuffixUpdate);
