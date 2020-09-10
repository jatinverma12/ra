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
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
import { getEntities as getCourses } from 'app/entities/courses-my-suffix/courses-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './students-my-suffix.reducer';
import { IStudentsMySuffix } from 'app/shared/model/students-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudentsMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudentsMySuffixUpdateState {
  isNew: boolean;
  idscourse: any[];
  userId: string;
}

export class StudentsMySuffixUpdate extends React.Component<IStudentsMySuffixUpdateProps, IStudentsMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idscourse: [],
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
    this.props.getCourses();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { studentsEntity } = this.props;
      const entity = {
        ...studentsEntity,
        ...values,
        courses: mapIdList(values.courses)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/students-my-suffix');
  };

  render() {
    const { studentsEntity, users, courses, loading, updating } = this.props;
    const { isNew } = this.state;

    const { registrationForm, registrationFormContentType } = studentsEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="risingArjunApp.students.home.createOrEditLabel">
              <Translate contentKey="risingArjunApp.students.home.createOrEditLabel">Create or edit a Students</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studentsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="students-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="students-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="studentRegIdLabel" for="students-my-suffix-studentRegId">
                    <Translate contentKey="risingArjunApp.students.studentRegId">Student Reg Id</Translate>
                  </Label>
                  <AvField
                    id="students-my-suffix-studentRegId"
                    type="text"
                    name="studentRegId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="registrationFormLabel" for="registrationForm">
                      <Translate contentKey="risingArjunApp.students.registrationForm">Registration Form</Translate>
                    </Label>
                    <br />
                    {registrationForm ? (
                      <div>
                        <a onClick={openFile(registrationFormContentType, registrationForm)}>
                          <img src={`data:${registrationFormContentType};base64,${registrationForm}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {registrationFormContentType}, {byteSize(registrationForm)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('registrationForm')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_registrationForm" type="file" onChange={this.onBlobChange(true, 'registrationForm')} accept="image/*" />
                    <AvInput type="hidden" name="registrationForm" value={registrationForm} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="parentMobNo1Label" for="students-my-suffix-parentMobNo1">
                    <Translate contentKey="risingArjunApp.students.parentMobNo1">Parent Mob No 1</Translate>
                  </Label>
                  <AvField id="students-my-suffix-parentMobNo1" type="text" name="parentMobNo1" />
                </AvGroup>
                <AvGroup>
                  <Label id="parentMobNo2Label" for="students-my-suffix-parentMobNo2">
                    <Translate contentKey="risingArjunApp.students.parentMobNo2">Parent Mob No 2</Translate>
                  </Label>
                  <AvField id="students-my-suffix-parentMobNo2" type="text" name="parentMobNo2" />
                </AvGroup>
                <AvGroup>
                  <Label id="parentEmailIdLabel" for="students-my-suffix-parentEmailId">
                    <Translate contentKey="risingArjunApp.students.parentEmailId">Parent Email Id</Translate>
                  </Label>
                  <AvField
                    id="students-my-suffix-parentEmailId"
                    type="text"
                    name="parentEmailId"
                    validate={{
                      pattern: {
                        value: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' })
                      }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="studentStatusLabel" for="students-my-suffix-studentStatus">
                    <Translate contentKey="risingArjunApp.students.studentStatus">Student Status</Translate>
                  </Label>
                  <AvInput
                    id="students-my-suffix-studentStatus"
                    type="select"
                    className="form-control"
                    name="studentStatus"
                    value={(!isNew && studentsEntity.studentStatus) || 'GRADUATED'}
                  >
                    <option value="GRADUATED">{translate('risingArjunApp.StudentStatus.GRADUATED')}</option>
                    <option value="JOINED">{translate('risingArjunApp.StudentStatus.JOINED')}</option>
                    <option value="LEFT">{translate('risingArjunApp.StudentStatus.LEFT')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="leavingReasonLabel" for="students-my-suffix-leavingReason">
                    <Translate contentKey="risingArjunApp.students.leavingReason">Leaving Reason</Translate>
                  </Label>
                  <AvInput
                    id="students-my-suffix-leavingReason"
                    type="select"
                    className="form-control"
                    name="leavingReason"
                    value={(!isNew && studentsEntity.leavingReason) || 'DISTANCEFACTOR'}
                  >
                    <option value="DISTANCEFACTOR">{translate('risingArjunApp.LeavingReasons.DISTANCEFACTOR')}</option>
                    <option value="UNHAPPYPHYSICS">{translate('risingArjunApp.LeavingReasons.UNHAPPYPHYSICS')}</option>
                    <option value="UNHAPPYMATHS">{translate('risingArjunApp.LeavingReasons.UNHAPPYMATHS')}</option>
                    <option value="UNHAPPYBIO">{translate('risingArjunApp.LeavingReasons.UNHAPPYBIO')}</option>
                    <option value="UNHAPPYCHEMISTRY">{translate('risingArjunApp.LeavingReasons.UNHAPPYCHEMISTRY')}</option>
                    <option value="UNHAPPYMANAGEMENT">{translate('risingArjunApp.LeavingReasons.UNHAPPYMANAGEMENT')}</option>
                    <option value="HIGHFEES">{translate('risingArjunApp.LeavingReasons.HIGHFEES')}</option>
                    <option value="CLASSESOVERLAP">{translate('risingArjunApp.LeavingReasons.CLASSESOVERLAP')}</option>
                    <option value="COURSECOMPLETED">{translate('risingArjunApp.LeavingReasons.COURSECOMPLETED')}</option>
                    <option value="BREAKEXAM">{translate('risingArjunApp.LeavingReasons.BREAKEXAM')}</option>
                    <option value="BREAKHOLIDAY">{translate('risingArjunApp.LeavingReasons.BREAKHOLIDAY')}</option>
                    <option value="PERSONALREASON">{translate('risingArjunApp.LeavingReasons.PERSONALREASON')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="infoSourceLabel" for="students-my-suffix-infoSource">
                    <Translate contentKey="risingArjunApp.students.infoSource">Info Source</Translate>
                  </Label>
                  <AvInput
                    id="students-my-suffix-infoSource"
                    type="select"
                    className="form-control"
                    name="infoSource"
                    value={(!isNew && studentsEntity.infoSource) || 'LOCATIONDIRECTLY'}
                  >
                    <option value="LOCATIONDIRECTLY">{translate('risingArjunApp.InfoSources.LOCATIONDIRECTLY')}</option>
                    <option value="FRIENDS">{translate('risingArjunApp.InfoSources.FRIENDS')}</option>
                    <option value="BANNER">{translate('risingArjunApp.InfoSources.BANNER')}</option>
                    <option value="INTERNET">{translate('risingArjunApp.InfoSources.INTERNET')}</option>
                    <option value="PAMPHLET">{translate('risingArjunApp.InfoSources.PAMPHLET')}</option>
                    <option value="NEWSPAPER">{translate('risingArjunApp.InfoSources.NEWSPAPER')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="students-my-suffix-user">
                    <Translate contentKey="risingArjunApp.students.user">User</Translate>
                  </Label>
                  <AvInput id="students-my-suffix-user" type="select" className="form-control" name="userId">
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
                <AvGroup>
                  <Label for="students-my-suffix-course">
                    <Translate contentKey="risingArjunApp.students.course">Course</Translate>
                  </Label>
                  <AvInput
                    id="students-my-suffix-course"
                    type="select"
                    multiple
                    className="form-control"
                    name="courses"
                    value={studentsEntity.courses && studentsEntity.courses.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {courses
                      ? courses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.course}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/students-my-suffix" replace color="info">
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
  courses: storeState.courses.entities,
  studentsEntity: storeState.students.entity,
  loading: storeState.students.loading,
  updating: storeState.students.updating,
  updateSuccess: storeState.students.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getCourses,
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
)(StudentsMySuffixUpdate);
