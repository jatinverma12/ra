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
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { getEntities as getSubjects } from 'app/entities/subjects-my-suffix/subjects-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './chapters-my-suffix.reducer';
import { IChaptersMySuffix } from 'app/shared/model/chapters-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChaptersMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IChaptersMySuffixUpdateState {
  isNew: boolean;
  courseId: string;
  subjectId: string;
}

export class ChaptersMySuffixUpdate extends React.Component<IChaptersMySuffixUpdateProps, IChaptersMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      courseId: '0',
      subjectId: '0',
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
    this.props.getSubjects();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { chaptersEntity } = this.props;
      const entity = {
        ...chaptersEntity,
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
    this.props.history.push('/entity/chapters-my-suffix');
  };

  render() {
    const { chaptersEntity, courses, subjects, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.chapters.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.chapters.home.createOrEditLabel">Create or edit a Chapters</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : chaptersEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="chapters-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="chapters-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="chapterIdLabel" for="chapters-my-suffix-chapterId">
                    <Translate contentKey="risingArjunApp.chapters.chapterId">Chapter Id</Translate>
                  </Label>
                  <AvField
                    id="chapters-my-suffix-chapterId"
                    type="text"
                    name="chapterId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="chapterTitleLabel" for="chapters-my-suffix-chapterTitle">
                    <Translate contentKey="risingArjunApp.chapters.chapterTitle">Chapter Title</Translate>
                  </Label>
                  <AvField id="chapters-my-suffix-chapterTitle" type="text" name="chapterTitle" />
                </AvGroup>
                <AvGroup>
                  <Label for="chapters-my-suffix-course">
                    <Translate contentKey="risingArjunApp.chapters.course">Course</Translate>
                  </Label>
                  <AvInput id="chapters-my-suffix-course" type="select" className="form-control" name="courseId">
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
                  <Label for="chapters-my-suffix-subject">
                    <Translate contentKey="risingArjunApp.chapters.subject">Subject</Translate>
                  </Label>
                  <AvInput id="chapters-my-suffix-subject" type="select" className="form-control" name="subjectId">
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
                <Button tag={Link} id="cancel-save" to="/entity/chapters-my-suffix" replace color="info">
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
  subjects: storeState.subjects.entities,
  chaptersEntity: storeState.chapters.entity,
  loading: storeState.chapters.loading,
  updating: storeState.chapters.updating,
  updateSuccess: storeState.chapters.updateSuccess
});

const mapDispatchToProps = {
  getCourses,
  getSubjects,
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
)(ChaptersMySuffixUpdate);
