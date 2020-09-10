import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './academic-sessions-my-suffix.reducer';
import { IAcademicSessionsMySuffix } from 'app/shared/model/academic-sessions-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAcademicSessionsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class AcademicSessionsMySuffix extends React.Component<IAcademicSessionsMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { academicSessionsList, match } = this.props;
    return (
      <div>
        <h2 id="academic-sessions-my-suffix-heading">
          <Translate contentKey="risingArjunApp.academicSessions.home.title">Academic Sessions</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.academicSessions.home.createLabel">Create new Academic Sessions</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {academicSessionsList && academicSessionsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.academicSessions.acadSessionId">Acad Session Id</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.academicSessions.acadSession">Acad Session</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {academicSessionsList.map((academicSessions, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${academicSessions.id}`} color="link" size="sm">
                        {academicSessions.id}
                      </Button>
                    </td>
                    <td>{academicSessions.acadSessionId}</td>
                    <td>{academicSessions.acadSession}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${academicSessions.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${academicSessions.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${academicSessions.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.academicSessions.home.notFound">No Academic Sessions found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ academicSessions }: IRootState) => ({
  academicSessionsList: academicSessions.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AcademicSessionsMySuffix);
