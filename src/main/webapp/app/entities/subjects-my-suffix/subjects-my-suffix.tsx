import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './subjects-my-suffix.reducer';
import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubjectsMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class SubjectsMySuffix extends React.Component<ISubjectsMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { subjectsList, match } = this.props;
    return (
      <div>
        <h2 id="subjects-my-suffix-heading">
          <Translate contentKey="risingArjunApp.subjects.home.title">Subjects</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.subjects.home.createLabel">Create new Subjects</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {subjectsList && subjectsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.subjects.subjectCode">Subject Code</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.subjects.subjectTitle">Subject Title</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {subjectsList.map((subjects, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${subjects.id}`} color="link" size="sm">
                        {subjects.id}
                      </Button>
                    </td>
                    <td>{subjects.subjectCode}</td>
                    <td>{subjects.subjectTitle}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${subjects.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${subjects.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${subjects.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.subjects.home.notFound">No Subjects found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ subjects }: IRootState) => ({
  subjectsList: subjects.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SubjectsMySuffix);
