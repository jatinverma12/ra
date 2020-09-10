import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './teachers-share-my-suffix.reducer';
import { ITeachersShareMySuffix } from 'app/shared/model/teachers-share-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITeachersShareMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ITeachersShareMySuffixState = IPaginationBaseState;

export class TeachersShareMySuffix extends React.Component<ITeachersShareMySuffixProps, ITeachersShareMySuffixState> {
  state: ITeachersShareMySuffixState = {
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
    const { teachersShareList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="teachers-share-my-suffix-heading">
          <Translate contentKey="risingArjunApp.teachersShare.home.title">Teachers Shares</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.teachersShare.home.createLabel">Create new Teachers Share</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {teachersShareList && teachersShareList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('share')}>
                    <Translate contentKey="risingArjunApp.teachersShare.share">Share</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('plannedClasses')}>
                    <Translate contentKey="risingArjunApp.teachersShare.plannedClasses">Planned Classes</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('actualClasses')}>
                    <Translate contentKey="risingArjunApp.teachersShare.actualClasses">Actual Classes</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('shareCorrection')}>
                    <Translate contentKey="risingArjunApp.teachersShare.shareCorrection">Share Correction</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('month')}>
                    <Translate contentKey="risingArjunApp.teachersShare.month">Month</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="risingArjunApp.teachersShare.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachersShare.teacher">Teacher</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachersShare.subject">Subject</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachersShare.course">Course</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachersShare.session">Session</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {teachersShareList.map((teachersShare, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${teachersShare.id}`} color="link" size="sm">
                        {teachersShare.id}
                      </Button>
                    </td>
                    <td>{teachersShare.share}</td>
                    <td>{teachersShare.plannedClasses}</td>
                    <td>{teachersShare.actualClasses}</td>
                    <td>{teachersShare.shareCorrection}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.Month.${teachersShare.month}`} />
                    </td>
                    <td>{teachersShare.remarks}</td>
                    <td>
                      {teachersShare.teacherEmployeeId ? (
                        <Link to={`employees-my-suffix/${teachersShare.teacherId}`}>{teachersShare.teacherEmployeeId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {teachersShare.subjectSubjectTitle ? (
                        <Link to={`subjects-my-suffix/${teachersShare.subjectId}`}>{teachersShare.subjectSubjectTitle}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {teachersShare.courseCourse ? (
                        <Link to={`courses-my-suffix/${teachersShare.courseId}`}>{teachersShare.courseCourse}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {teachersShare.sessionAcadSessionId ? (
                        <Link to={`academic-sessions-my-suffix/${teachersShare.sessionId}`}>{teachersShare.sessionAcadSessionId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${teachersShare.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${teachersShare.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${teachersShare.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.teachersShare.home.notFound">No Teachers Shares found</Translate>
            </div>
          )}
        </div>
        <div className={teachersShareList && teachersShareList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ teachersShare }: IRootState) => ({
  teachersShareList: teachersShare.entities,
  totalItems: teachersShare.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TeachersShareMySuffix);
