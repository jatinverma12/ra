import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './employees-my-suffix.reducer';
import { IEmployeesMySuffix } from 'app/shared/model/employees-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeesMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeesMySuffixUpdateState {
  isNew: boolean;
  userId: string;
}

export class EmployeesMySuffixUpdate extends React.Component<IEmployeesMySuffixUpdateProps, IEmployeesMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { employeesEntity } = this.props;
      const entity = {
        ...employeesEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/employees-my-suffix');
  };

  render() {
    const { employeesEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    const { resume, resumeContentType } = employeesEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.employees.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.employees.home.createOrEditLabel">Create or edit a Employees</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employeesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employees-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="employees-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="employeeIdLabel" for="employees-my-suffix-employeeId">
                    <Translate contentKey="risingArjunApp.employees.employeeId">Employee Id</Translate>
                  </Label>
                  <AvField
                    id="employees-my-suffix-employeeId"
                    type="text"
                    name="employeeId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="jobNatureLabel" for="employees-my-suffix-jobNature">
                    <Translate contentKey="risingArjunApp.employees.jobNature">Job Nature</Translate>
                  </Label>
                  <AvInput
                    id="employees-my-suffix-jobNature"
                    type="select"
                    className="form-control"
                    name="jobNature"
                    value={(!isNew && employeesEntity.jobNature) || 'PARTTIME'}
                  >
                    <option value="PARTTIME">{translate('risingArjunApp.JobNature.PARTTIME')}</option>
                    <option value="FULLTIME">{translate('risingArjunApp.JobNature.FULLTIME')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="bgcLabel" check>
                    <AvInput id="employees-my-suffix-bgc" type="checkbox" className="form-control" name="bgc" />
                    <Translate contentKey="risingArjunApp.employees.bgc">Bgc</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="resumeLabel" for="resume">
                      <Translate contentKey="risingArjunApp.employees.resume">Resume</Translate>
                    </Label>
                    <br />
                    {resume ? (
                      <div>
                        <a onClick={openFile(resumeContentType, resume)}>
                          <img src={`data:${resumeContentType};base64,${resume}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {resumeContentType}, {byteSize(resume)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('resume')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_resume" type="file" onChange={this.onBlobChange(true, 'resume')} accept="image/*" />
                    <AvInput type="hidden" name="resume" value={resume} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="panLabel" for="employees-my-suffix-pan">
                    <Translate contentKey="risingArjunApp.employees.pan">Pan</Translate>
                  </Label>
                  <AvField
                    id="employees-my-suffix-pan"
                    type="text"
                    name="pan"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="accountNoLabel" for="employees-my-suffix-accountNo">
                    <Translate contentKey="risingArjunApp.employees.accountNo">Account No</Translate>
                  </Label>
                  <AvField
                    id="employees-my-suffix-accountNo"
                    type="text"
                    name="accountNo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="bankLabel" for="employees-my-suffix-bank">
                    <Translate contentKey="risingArjunApp.employees.bank">Bank</Translate>
                  </Label>
                  <AvField
                    id="employees-my-suffix-bank"
                    type="text"
                    name="bank"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="ifscLabel" for="employees-my-suffix-ifsc">
                    <Translate contentKey="risingArjunApp.employees.ifsc">Ifsc</Translate>
                  </Label>
                  <AvField
                    id="employees-my-suffix-ifsc"
                    type="text"
                    name="ifsc"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="employees-my-suffix-user">
                    <Translate contentKey="risingArjunApp.employees.user">User</Translate>
                  </Label>
                  <AvInput id="employees-my-suffix-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/employees-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  employeesEntity: storeState.employees.entity,
  loading: storeState.employees.loading,
  updating: storeState.employees.updating,
  updateSuccess: storeState.employees.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeesMySuffixUpdate);
