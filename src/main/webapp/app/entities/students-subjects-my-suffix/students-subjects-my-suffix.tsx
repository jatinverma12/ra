import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './students-subjects-my-suffix.reducer';
import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentsSubjectsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class StudentsSubjectsMySuffix extends React.Component<IStudentsSubjectsMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { studentsSubjectsList, match } = this.props;
    return (
      <div>
        <h2 id="students-subjects-my-suffix-heading">
          <Translate contentKey="risingArjunApp.studentsSubjects.home.title">Students Subjects</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.studentsSubjects.home.createLabel">Create new Students Subjects</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {studentsSubjectsList && studentsSubjectsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.studentsSubjects.month">Month</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.studentsSubjects.registrationno">Registrationno</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.studentsSubjects.session">Session</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.studentsSubjects.subjects">Subjects</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.studentsSubjects.course">Course</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {studentsSubjectsList.map((studentsSubjects, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${studentsSubjects.id}`} color="link" size="sm">
                        {studentsSubjects.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`risingArjunApp.Month.${studentsSubjects.month}`} />
                    </td>
                    <td>
                      {studentsSubjects.registrationnoStudentRegId ? (
                        <Link to={`students-my-suffix/${studentsSubjects.registrationnoId}`}>
                          {studentsSubjects.registrationnoStudentRegId}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {studentsSubjects.sessionAcadSession ? (
                        <Link to={`academic-sessions-my-suffix/${studentsSubjects.sessionId}`}>{studentsSubjects.sessionAcadSession}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {studentsSubjects.subjects
                        ? studentsSubjects.subjects.map((val, j) => (
                            <span key={j}>
                              <Link to={`subjects-my-suffix/${val.id}`}>{val.subjectTitle}</Link>
                              {j === studentsSubjects.subjects.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>
                      {studentsSubjects.courses
                        ? studentsSubjects.courses.map((val, j) => (
                            <span key={j}>
                              <Link to={`courses-my-suffix/${val.id}`}>{val.course}</Link>
                              {j === studentsSubjects.courses.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${studentsSubjects.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${studentsSubjects.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${studentsSubjects.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.studentsSubjects.home.notFound">No Students Subjects found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ studentsSubjects }: IRootState) => ({
  studentsSubjectsList: studentsSubjects.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentsSubjectsMySuffix);
