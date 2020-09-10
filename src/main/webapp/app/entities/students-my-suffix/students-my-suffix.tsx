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
import { getEntities } from './students-my-suffix.reducer';
import { IStudentsMySuffix } from 'app/shared/model/students-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IStudentsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IStudentsMySuffixState = IPaginationBaseState;

export class StudentsMySuffix extends React.Component<IStudentsMySuffixProps, IStudentsMySuffixState> {
  state: IStudentsMySuffixState = {
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
    const { studentsList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="students-my-suffix-heading">
          <Translate contentKey="risingArjunApp.students.home.title">Students</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.students.home.createLabel">Create new Students</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {studentsList && studentsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('studentRegId')}>
                    <Translate contentKey="risingArjunApp.students.studentRegId">Student Reg Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('registrationForm')}>
                    <Translate contentKey="risingArjunApp.students.registrationForm">Registration Form</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('parentMobNo1')}>
                    <Translate contentKey="risingArjunApp.students.parentMobNo1">Parent Mob No 1</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('parentMobNo2')}>
                    <Translate contentKey="risingArjunApp.students.parentMobNo2">Parent Mob No 2</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('parentEmailId')}>
                    <Translate contentKey="risingArjunApp.students.parentEmailId">Parent Email Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('studentStatus')}>
                    <Translate contentKey="risingArjunApp.students.studentStatus">Student Status</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('leavingReason')}>
                    <Translate contentKey="risingArjunApp.students.leavingReason">Leaving Reason</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('infoSource')}>
                    <Translate contentKey="risingArjunApp.students.infoSource">Info Source</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.students.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {studentsList.map((students, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${students.id}`} color="link" size="sm">
                        {students.id}
                      </Button>
                    </td>
                    <td>{students.studentRegId}</td>
                    <td>
                      {students.registrationForm ? (
                        <div>
                          <a onClick={openFile(students.registrationFormContentType, students.registrationForm)}>
                            <img
                              src={`data:${students.registrationFormContentType};base64,${students.registrationForm}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                          <span>
                            {students.registrationFormContentType}, {byteSize(students.registrationForm)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{students.parentMobNo1}</td>
                    <td>{students.parentMobNo2}</td>
                    <td>{students.parentEmailId}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.StudentStatus.${students.studentStatus}`} />
                    </td>
                    <td>
                      <Translate contentKey={`risingArjunApp.LeavingReasons.${students.leavingReason}`} />
                    </td>
                    <td>
                      <Translate contentKey={`risingArjunApp.InfoSources.${students.infoSource}`} />
                    </td>
                    <td>{students.userLogin ? students.userLogin : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${students.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${students.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${students.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.students.home.notFound">No Students found</Translate>
            </div>
          )}
        </div>
        <div className={studentsList && studentsList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ students }: IRootState) => ({
  studentsList: students.entities,
  totalItems: students.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentsMySuffix);
