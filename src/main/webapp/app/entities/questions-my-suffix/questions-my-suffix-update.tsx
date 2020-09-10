import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
import { getEntities as getCourses } from 'app/entities/courses-my-suffix/courses-my-suffix.reducer';
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { getEntities as getSubjects } from 'app/entities/subjects-my-suffix/subjects-my-suffix.reducer';
import { IChaptersMySuffix } from 'app/shared/model/chapters-my-suffix.model';
import { getEntities as getChapters } from 'app/entities/chapters-my-suffix/chapters-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './questions-my-suffix.reducer';
import { IQuestionsMySuffix } from 'app/shared/model/questions-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuestionsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IQuestionsMySuffixUpdateState {
  isNew: boolean;
  courseId: string;
  subjectId: string;
  chapterId: string;
}

export class QuestionsMySuffixUpdate extends React.Component<IQuestionsMySuffixUpdateProps, IQuestionsMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      courseId: '0',
      subjectId: '0',
      chapterId: '0',
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
    this.props.getChapters();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { questionsEntity } = this.props;
      const entity = {
        ...questionsEntity,
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
    this.props.history.push('/entity/questions-my-suffix');
  };

  render() {
    const { questionsEntity, courses, subjects, chapters, loading, updating } = this.props;
    const { isNew } = this.state;

    const { question, diagram, diagramContentType } = questionsEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.questions.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.questions.home.createOrEditLabel">Create or edit a Questions</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : questionsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="questions-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="questions-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="questionLabel" for="questions-my-suffix-question">
                    <Translate contentKey="risingArjunApp.questions.question">Question</Translate>
                  </Label>
                  <AvInput
                    id="questions-my-suffix-question"
                    type="textarea"
                    name="question"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="diagramLabel" for="diagram">
                      <Translate contentKey="risingArjunApp.questions.diagram">Diagram</Translate>
                    </Label>
                    <br />
                    {diagram ? (
                      <div>
                        <a onClick={openFile(diagramContentType, diagram)}>
                          <img src={`data:${diagramContentType};base64,${diagram}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {diagramContentType}, {byteSize(diagram)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('diagram')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_diagram" type="file" onChange={this.onBlobChange(true, 'diagram')} accept="image/*" />
                    <AvInput type="hidden" name="diagram" value={diagram} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="option1Label" for="questions-my-suffix-option1">
                    <Translate contentKey="risingArjunApp.questions.option1">Option 1</Translate>
                  </Label>
                  <AvField id="questions-my-suffix-option1" type="text" name="option1" />
                </AvGroup>
                <AvGroup>
                  <Label id="option2Label" for="questions-my-suffix-option2">
                    <Translate contentKey="risingArjunApp.questions.option2">Option 2</Translate>
                  </Label>
                  <AvField id="questions-my-suffix-option2" type="text" name="option2" />
                </AvGroup>
                <AvGroup>
                  <Label id="option3Label" for="questions-my-suffix-option3">
                    <Translate contentKey="risingArjunApp.questions.option3">Option 3</Translate>
                  </Label>
                  <AvField id="questions-my-suffix-option3" type="text" name="option3" />
                </AvGroup>
                <AvGroup>
                  <Label id="option4Label" for="questions-my-suffix-option4">
                    <Translate contentKey="risingArjunApp.questions.option4">Option 4</Translate>
                  </Label>
                  <AvField id="questions-my-suffix-option4" type="text" name="option4" />
                </AvGroup>
                <AvGroup>
                  <Label id="answerLabel" for="questions-my-suffix-answer">
                    <Translate contentKey="risingArjunApp.questions.answer">Answer</Translate>
                  </Label>
                  <AvField
                    id="questions-my-suffix-answer"
                    type="text"
                    name="answer"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="maxMarksLabel" for="questions-my-suffix-maxMarks">
                    <Translate contentKey="risingArjunApp.questions.maxMarks">Max Marks</Translate>
                  </Label>
                  <AvField
                    id="questions-my-suffix-maxMarks"
                    type="string"
                    className="form-control"
                    name="maxMarks"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="negativeMarksLabel" for="questions-my-suffix-negativeMarks">
                    <Translate contentKey="risingArjunApp.questions.negativeMarks">Negative Marks</Translate>
                  </Label>
                  <AvField id="questions-my-suffix-negativeMarks" type="string" className="form-control" name="negativeMarks" />
                </AvGroup>
                <AvGroup>
                  <Label id="levelLabel" for="questions-my-suffix-level">
                    <Translate contentKey="risingArjunApp.questions.level">Level</Translate>
                  </Label>
                  <AvInput
                    id="questions-my-suffix-level"
                    type="select"
                    className="form-control"
                    name="level"
                    value={(!isNew && questionsEntity.level) || 'BEGINNERS'}
                  >
                    <option value="BEGINNERS">{translate('risingArjunApp.QuestionLevel.BEGINNERS')}</option>
                    <option value="MODERATE">{translate('risingArjunApp.QuestionLevel.MODERATE')}</option>
                    <option value="ADVANCE">{translate('risingArjunApp.QuestionLevel.ADVANCE')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="questions-my-suffix-course">
                    <Translate contentKey="risingArjunApp.questions.course">Course</Translate>
                  </Label>
                  <AvInput id="questions-my-suffix-course" type="select" className="form-control" name="courseId">
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
                  <Label for="questions-my-suffix-subject">
                    <Translate contentKey="risingArjunApp.questions.subject">Subject</Translate>
                  </Label>
                  <AvInput id="questions-my-suffix-subject" type="select" className="form-control" name="subjectId">
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
                  <Label for="questions-my-suffix-chapter">
                    <Translate contentKey="risingArjunApp.questions.chapter">Chapter</Translate>
                  </Label>
                  <AvInput id="questions-my-suffix-chapter" type="select" className="form-control" name="chapterId">
                    <option value="" key="0" />
                    {chapters
                      ? chapters.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.chapterTitle}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/questions-my-suffix" replace color="info">
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
  chapters: storeState.chapters.entities,
  questionsEntity: storeState.questions.entity,
  loading: storeState.questions.loading,
  updating: storeState.questions.updating,
  updateSuccess: storeState.questions.updateSuccess
});

const mapDispatchToProps = {
  getCourses,
  getSubjects,
  getChapters,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QuestionsMySuffixUpdate);
