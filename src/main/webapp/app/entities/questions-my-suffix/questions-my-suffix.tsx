import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './questions-my-suffix.reducer';
import { IQuestionsMySuffix } from 'app/shared/model/questions-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IQuestionsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IQuestionsMySuffixState = IPaginationBaseState;

export class QuestionsMySuffix extends React.Component<IQuestionsMySuffixProps, IQuestionsMySuffixState> {
  state: IQuestionsMySuffixState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { questionsList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="questions-my-suffix-heading">
          <Translate contentKey="risingArjunApp.questions.home.title">Questions</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.questions.home.createLabel">Create new Questions</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {questionsList && questionsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('question')}>
                    <Translate contentKey="risingArjunApp.questions.question">Question</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('diagram')}>
                    <Translate contentKey="risingArjunApp.questions.diagram">Diagram</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('option1')}>
                    <Translate contentKey="risingArjunApp.questions.option1">Option 1</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('option2')}>
                    <Translate contentKey="risingArjunApp.questions.option2">Option 2</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('option3')}>
                    <Translate contentKey="risingArjunApp.questions.option3">Option 3</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('option4')}>
                    <Translate contentKey="risingArjunApp.questions.option4">Option 4</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('answer')}>
                    <Translate contentKey="risingArjunApp.questions.answer">Answer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('maxMarks')}>
                    <Translate contentKey="risingArjunApp.questions.maxMarks">Max Marks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('negativeMarks')}>
                    <Translate contentKey="risingArjunApp.questions.negativeMarks">Negative Marks</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('level')}>
                    <Translate contentKey="risingArjunApp.questions.level">Level</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.questions.course">Course</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.questions.subject">Subject</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.questions.chapter">Chapter</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {questionsList.map((questions, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${questions.id}`} color="link" size="sm">
                        {questions.id}
                      </Button>
                    </td>
                    <td>{questions.question}</td>
                    <td>
                      {questions.diagram ? (
                        <div>
                          <a onClick={openFile(questions.diagramContentType, questions.diagram)}>
                            <img src={`data:${questions.diagramContentType};base64,${questions.diagram}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {questions.diagramContentType}, {byteSize(questions.diagram)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{questions.option1}</td>
                    <td>{questions.option2}</td>
                    <td>{questions.option3}</td>
                    <td>{questions.option4}</td>
                    <td>{questions.answer}</td>
                    <td>{questions.maxMarks}</td>
                    <td>{questions.negativeMarks}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.QuestionLevel.${questions.level}`} />
                    </td>
                    <td>
                      {questions.courseCourse ? <Link to={`courses-my-suffix/${questions.courseId}`}>{questions.courseCourse}</Link> : ''}
                    </td>
                    <td>
                      {questions.subjectSubjectTitle ? (
                        <Link to={`subjects-my-suffix/${questions.subjectId}`}>{questions.subjectSubjectTitle}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {questions.chapterChapterTitle ? (
                        <Link to={`chapters-my-suffix/${questions.chapterId}`}>{questions.chapterChapterTitle}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${questions.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${questions.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${questions.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="risingArjunApp.questions.home.notFound">No Questions found</Translate>
            </div>
          )}
        </div>
        <div className={questionsList && questionsList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ questions }: IRootState) => ({
  questionsList: questions.entities,
  totalItems: questions.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QuestionsMySuffix);
