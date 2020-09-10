import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './teachers-my-suffix.reducer';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITeachersMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class TeachersMySuffix extends React.Component<ITeachersMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { teachersList, match } = this.props;
    return (
      <div>
        <h2 id="teachers-my-suffix-heading">
          <Translate contentKey="risingArjunApp.teachers.home.title">Teachers</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.teachers.home.createLabel">Create new Teachers</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {teachersList && teachersList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachers.teacher">Teacher</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachers.subjects">Subjects</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.teachers.courses">Courses</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {teachersList.map((teachers, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${teachers.id}`} color="link" size="sm">
                        {teachers.id}
                      </Button>
                    </td>
                    <td>
                      {teachers.teacherEmployeeId ? (
                        <Link to={`employees-my-suffix/${teachers.teacherId}`}>{teachers.teacherEmployeeId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {teachers.subjects
                        ? teachers.subjects.map((val, j) => (
                            <span key={j}>
                              <Link to={`subjects-my-suffix/${val.id}`}>{val.subjectTitle}</Link>
                              {j === teachers.subjects.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>
                      {teachers.courses
                        ? teachers.courses.map((val, j) => (
                            <span key={j}>
                              <Link to={`courses-my-suffix/${val.id}`}>{val.course}</Link>
                              {j === teachers.courses.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${teachers.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${teachers.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${teachers.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.teachers.home.notFound">No Teachers found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ teachers }: IRootState) => ({
  teachersList: teachers.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TeachersMySuffix);
