import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './courses-my-suffix.reducer';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICoursesMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class CoursesMySuffix extends React.Component<ICoursesMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { coursesList, match } = this.props;
    return (
      <div>
        <h2 id="courses-my-suffix-heading">
          <Translate contentKey="risingArjunApp.courses.home.title">Courses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.courses.home.createLabel">Create new Courses</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {coursesList && coursesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.courses.courseId">Course Id</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.courses.course">Course</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {coursesList.map((courses, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${courses.id}`} color="link" size="sm">
                        {courses.id}
                      </Button>
                    </td>
                    <td>{courses.courseId}</td>
                    <td>{courses.course}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${courses.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${courses.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${courses.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.courses.home.notFound">No Courses found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ courses }: IRootState) => ({
  coursesList: courses.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CoursesMySuffix);
