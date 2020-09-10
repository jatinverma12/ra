import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './subjects-base-fee-my-suffix.reducer';
import { ISubjectsBaseFeeMySuffix } from 'app/shared/model/subjects-base-fee-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubjectsBaseFeeMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class SubjectsBaseFeeMySuffix extends React.Component<ISubjectsBaseFeeMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { subjectsBaseFeeList, match } = this.props;
    return (
      <div>
        <h2 id="subjects-base-fee-my-suffix-heading">
          <Translate contentKey="risingArjunApp.subjectsBaseFee.home.title">Subjects Base Fees</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.subjectsBaseFee.home.createLabel">Create new Subjects Base Fee</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {subjectsBaseFeeList && subjectsBaseFeeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.subjectsBaseFee.baseFee">Base Fee</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.subjectsBaseFee.course">Course</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.subjectsBaseFee.session">Session</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {subjectsBaseFeeList.map((subjectsBaseFee, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${subjectsBaseFee.id}`} color="link" size="sm">
                        {subjectsBaseFee.id}
                      </Button>
                    </td>
                    <td>{subjectsBaseFee.baseFee}</td>
                    <td>
                      {subjectsBaseFee.courseCourse ? (
                        <Link to={`courses-my-suffix/${subjectsBaseFee.courseId}`}>{subjectsBaseFee.courseCourse}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {subjectsBaseFee.sessionAcadSession ? (
                        <Link to={`academic-sessions-my-suffix/${subjectsBaseFee.sessionId}`}>{subjectsBaseFee.sessionAcadSession}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${subjectsBaseFee.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${subjectsBaseFee.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${subjectsBaseFee.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.subjectsBaseFee.home.notFound">No Subjects Base Fees found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ subjectsBaseFee }: IRootState) => ({
  subjectsBaseFeeList: subjectsBaseFee.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SubjectsBaseFeeMySuffix);
