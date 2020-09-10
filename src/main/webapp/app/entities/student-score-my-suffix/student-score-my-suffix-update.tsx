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
import { IQuestionsMySuffix } from 'app/shared/model/questions-my-suffix.model';
import { getEntities as getQuestions } from 'app/entities/questions-my-suffix/questions-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './student-score-my-suffix.reducer';
import { IStudentScoreMySuffix } from 'app/shared/model/student-score-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudentScoreMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudentScoreMySuffixUpdateState {
  isNew: boolean;
  studentId: string;
  questionIdId: string;
}

export class StudentScoreMySuffixUpdate extends React.Component<IStudentScoreMySuffixUpdateProps, IStudentScoreMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      studentId: '0',
      questionIdId: '0',
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
    this.props.getQuestions();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { studentScoreEntity } = this.props;
      const entity = {
        ...studentScoreEntity,
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
    this.props.history.push('/entity/student-score-my-suffix');
  };

  render() {
    const { studentScoreEntity, students, questions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.studentScore.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.studentScore.home.createOrEditLabel">Create or edit a StudentScore</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studentScoreEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="student-score-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="student-score-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="answerLabel" for="student-score-my-suffix-answer">
                    <Translate contentKey="risingArjunApp.studentScore.answer">Answer</Translate>
                  </Label>
                  <AvField
                    id="student-score-my-suffix-answer"
                    type="text"
                    name="answer"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="scoreLabel" for="student-score-my-suffix-score">
                    <Translate contentKey="risingArjunApp.studentScore.score">Score</Translate>
                  </Label>
                  <AvField
                    id="student-score-my-suffix-score"
                    type="string"
                    className="form-control"
                    name="score"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="student-score-my-suffix-date">
                    <Translate contentKey="risingArjunApp.studentScore.date">Date</Translate>
                  </Label>
                  <AvField
                    id="student-score-my-suffix-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="student-score-my-suffix-student">
                    <Translate contentKey="risingArjunApp.studentScore.student">Student</Translate>
                  </Label>
                  <AvInput id="student-score-my-suffix-student" type="select" className="form-control" name="studentId">
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
                  <Label for="student-score-my-suffix-questionId">
                    <Translate contentKey="risingArjunApp.studentScore.questionId">Question Id</Translate>
                  </Label>
                  <AvInput id="student-score-my-suffix-questionId" type="select" className="form-control" name="questionIdId">
                    <option value="" key="0" />
                    {questions
                      ? questions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/student-score-my-suffix" replace color="info">
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
  questions: storeState.questions.entities,
  studentScoreEntity: storeState.studentScore.entity,
  loading: storeState.studentScore.loading,
  updating: storeState.studentScore.updating,
  updateSuccess: storeState.studentScore.updateSuccess
});

const mapDispatchToProps = {
  getStudents,
  getQuestions,
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
)(StudentScoreMySuffixUpdate);
