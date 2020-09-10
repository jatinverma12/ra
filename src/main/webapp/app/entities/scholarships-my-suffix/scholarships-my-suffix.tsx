import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './scholarships-my-suffix.reducer';
import { IScholarshipsMySuffix } from 'app/shared/model/scholarships-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScholarshipsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ScholarshipsMySuffix extends React.Component<IScholarshipsMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { scholarshipsList, match } = this.props;
    return (
      <div>
        <h2 id="scholarships-my-suffix-heading">
          <Translate contentKey="risingArjunApp.scholarships.home.title">Scholarships</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.scholarships.home.createLabel">Create new Scholarships</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {scholarshipsList && scholarshipsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.scholarships.minMarks">Min Marks</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.scholarships.percent">Percent</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.scholarships.session">Session</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {scholarshipsList.map((scholarships, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${scholarships.id}`} color="link" size="sm">
                        {scholarships.id}
                      </Button>
                    </td>
                    <td>{scholarships.minMarks}</td>
                    <td>{scholarships.percent}</td>
                    <td>
                      {scholarships.sessionAcadSession ? (
                        <Link to={`academic-sessions-my-suffix/${scholarships.sessionId}`}>{scholarships.sessionAcadSession}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${scholarships.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${scholarships.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${scholarships.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.scholarships.home.notFound">No Scholarships found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ scholarships }: IRootState) => ({
  scholarshipsList: scholarships.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ScholarshipsMySuffix);
