import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employees-my-suffix.reducer';
import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeesMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EmployeesMySuffix extends React.Component<IEmployeesMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { employeesList, match } = this.props;
    return (
      <div>
        <h2 id="employees-my-suffix-heading">
          <Translate contentKey="risingArjunApp.employees.home.title">Employees</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.employees.home.createLabel">Create new Employees</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {employeesList && employeesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.employeeId">Employee Id</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.jobNature">Job Nature</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.bgc">Bgc</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.resume">Resume</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.pan">Pan</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.accountNo">Account No</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.bank">Bank</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.ifsc">Ifsc</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.employees.user">User</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {employeesList.map((employees, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${employees.id}`} color="link" size="sm">
                        {employees.id}
                      </Button>
                    </td>
                    <td>{employees.employeeId}</td>
                    <td>
                      <Translate contentKey={`risingArjunApp.JobNature.${employees.jobNature}`} />
                    </td>
                    <td>{employees.bgc ? 'true' : 'false'}</td>
                    <td>
                      {employees.resume ? (
                        <div>
                          <a onClick={openFile(employees.resumeContentType, employees.resume)}>
                            <img src={`data:${employees.resumeContentType};base64,${employees.resume}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {employees.resumeContentType}, {byteSize(employees.resume)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{employees.pan}</td>
                    <td>{employees.accountNo}</td>
                    <td>{employees.bank}</td>
                    <td>{employees.ifsc}</td>
                    <td>{employees.userLogin ? employees.userLogin : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${employees.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employees.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employees.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="risingArjunApp.employees.home.notFound">No Employees found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employees }: IRootState) => ({
  employeesList: employees.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeesMySuffix);
