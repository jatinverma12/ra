import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './questions-my-suffix.reducer';
import { IQuestionsMySuffix } from 'app/shared/model/questions-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuestionsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class QuestionsMySuffixDetail extends React.Component<IQuestionsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { questionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.questions.detail.title">Questions</Translate> [<b>{questionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="question">
                <Translate contentKey="risingArjunApp.questions.question">Question</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.question}</dd>
            <dt>
              <span id="diagram">
                <Translate contentKey="risingArjunApp.questions.diagram">Diagram</Translate>
              </span>
            </dt>
            <dd>
              {questionsEntity.diagram ? (
                <div>
                  <a onClick={openFile(questionsEntity.diagramContentType, questionsEntity.diagram)}>
                    <img
                      src={`data:${questionsEntity.diagramContentType};base64,${questionsEntity.diagram}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {questionsEntity.diagramContentType}, {byteSize(questionsEntity.diagram)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="option1">
                <Translate contentKey="risingArjunApp.questions.option1">Option 1</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.option1}</dd>
            <dt>
              <span id="option2">
                <Translate contentKey="risingArjunApp.questions.option2">Option 2</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.option2}</dd>
            <dt>
              <span id="option3">
                <Translate contentKey="risingArjunApp.questions.option3">Option 3</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.option3}</dd>
            <dt>
              <span id="option4">
                <Translate contentKey="risingArjunApp.questions.option4">Option 4</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.option4}</dd>
            <dt>
              <span id="answer">
                <Translate contentKey="risingArjunApp.questions.answer">Answer</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.answer}</dd>
            <dt>
              <span id="maxMarks">
                <Translate contentKey="risingArjunApp.questions.maxMarks">Max Marks</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.maxMarks}</dd>
            <dt>
              <span id="negativeMarks">
                <Translate contentKey="risingArjunApp.questions.negativeMarks">Negative Marks</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.negativeMarks}</dd>
            <dt>
              <span id="level">
                <Translate contentKey="risingArjunApp.questions.level">Level</Translate>
              </span>
            </dt>
            <dd>{questionsEntity.level}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.questions.course">Course</Translate>
            </dt>
            <dd>{questionsEntity.courseCourse ? questionsEntity.courseCourse : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.questions.subject">Subject</Translate>
            </dt>
            <dd>{questionsEntity.subjectSubjectTitle ? questionsEntity.subjectSubjectTitle : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.questions.chapter">Chapter</Translate>
            </dt>
            <dd>{questionsEntity.chapterChapterTitle ? questionsEntity.chapterChapterTitle : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/questions-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/questions-my-suffix/${questionsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ questions }: IRootState) => ({
  questionsEntity: questions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QuestionsMySuffixDetail);
