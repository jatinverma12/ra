import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './chapters-my-suffix.reducer';
import { IChaptersMySuffix } from 'app/shared/model/chapters-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChaptersMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ChaptersMySuffix extends React.Component<IChaptersMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { chaptersList, match } = this.props;
    return (
      <div>
        <h2 id="chapters-my-suffix-heading">
          <Translate contentKey="risingArjunApp.chapters.home.title">Chapters</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.chapters.home.createLabel">Create new Chapters</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {chaptersList && chaptersList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.chapters.chapterId">Chapter Id</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.chapters.chapterTitle">Chapter Title</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.chapters.course">Course</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.chapters.subject">Subject</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {chaptersList.map((chapters, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${chapters.id}`} color="link" size="sm">
                        {chapters.id}
                      </Button>
                    </td>
                    <td>{chapters.chapterId}</td>
                    <td>{chapters.chapterTitle}</td>
                    <td>
                      {chapters.courseCourse ? <Link to={`courses-my-suffix/${chapters.courseId}`}>{chapters.courseCourse}</Link> : ''}
                    </td>
                    <td>
                      {chapters.subjectSubjectTitle ? (
                        <Link to={`subjects-my-suffix/${chapters.subjectId}`}>{chapters.subjectSubjectTitle}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${chapters.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${chapters.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${chapters.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.chapters.home.notFound">No Chapters found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ chapters }: IRootState) => ({
  chaptersList: chapters.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ChaptersMySuffix);
