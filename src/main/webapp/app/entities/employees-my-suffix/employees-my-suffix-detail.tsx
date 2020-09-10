import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employees-my-suffix.reducer';
import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeesMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeesMySuffixDetail extends React.Component<IEmployeesMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employeesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.employees.detail.title">Employees</Translate> [<b>{employeesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="employeeId">
                <Translate contentKey="risingArjunApp.employees.employeeId">Employee Id</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.employeeId}</dd>
            <dt>
              <span id="jobNature">
                <Translate contentKey="risingArjunApp.employees.jobNature">Job Nature</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.jobNature}</dd>
            <dt>
              <span id="bgc">
                <Translate contentKey="risingArjunApp.employees.bgc">Bgc</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.bgc ? 'true' : 'false'}</dd>
            <dt>
              <span id="resume">
                <Translate contentKey="risingArjunApp.employees.resume">Resume</Translate>
              </span>
            </dt>
            <dd>
              {employeesEntity.resume ? (
                <div>
                  <a onClick={openFile(employeesEntity.resumeContentType, employeesEntity.resume)}>
                    <img src={`data:${employeesEntity.resumeContentType};base64,${employeesEntity.resume}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {employeesEntity.resumeContentType}, {byteSize(employeesEntity.resume)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="pan">
                <Translate contentKey="risingArjunApp.employees.pan">Pan</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.pan}</dd>
            <dt>
              <span id="accountNo">
                <Translate contentKey="risingArjunApp.employees.accountNo">Account No</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.accountNo}</dd>
            <dt>
              <span id="bank">
                <Translate contentKey="risingArjunApp.employees.bank">Bank</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.bank}</dd>
            <dt>
              <span id="ifsc">
                <Translate contentKey="risingArjunApp.employees.ifsc">Ifsc</Translate>
              </span>
            </dt>
            <dd>{employeesEntity.ifsc}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.employees.user">User</Translate>
            </dt>
            <dd>{employeesEntity.userLogin ? employeesEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employees-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employees-my-suffix/${employeesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employees }: IRootState) => ({
  employeesEntity: employees.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeesMySuffixDetail);
