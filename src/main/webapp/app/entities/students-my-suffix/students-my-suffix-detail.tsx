import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './students-my-suffix.reducer';
import { IStudentsMySuffix } from 'app/shared/model/students-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudentsMySuffixDetail extends React.Component<IStudentsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studentsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.students.detail.title">Students</Translate> [<b>{studentsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="studentRegId">
                <Translate contentKey="risingArjunApp.students.studentRegId">Student Reg Id</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.studentRegId}</dd>
            <dt>
              <span id="registrationForm">
                <Translate contentKey="risingArjunApp.students.registrationForm">Registration Form</Translate>
              </span>
            </dt>
            <dd>
              {studentsEntity.registrationForm ? (
                <div>
                  <a onClick={openFile(studentsEntity.registrationFormContentType, studentsEntity.registrationForm)}>
                    <img
                      src={`data:${studentsEntity.registrationFormContentType};base64,${studentsEntity.registrationForm}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {studentsEntity.registrationFormContentType}, {byteSize(studentsEntity.registrationForm)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="parentMobNo1">
                <Translate contentKey="risingArjunApp.students.parentMobNo1">Parent Mob No 1</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.parentMobNo1}</dd>
            <dt>
              <span id="parentMobNo2">
                <Translate contentKey="risingArjunApp.students.parentMobNo2">Parent Mob No 2</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.parentMobNo2}</dd>
            <dt>
              <span id="parentEmailId">
                <Translate contentKey="risingArjunApp.students.parentEmailId">Parent Email Id</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.parentEmailId}</dd>
            <dt>
              <span id="studentStatus">
                <Translate contentKey="risingArjunApp.students.studentStatus">Student Status</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.studentStatus}</dd>
            <dt>
              <span id="leavingReason">
                <Translate contentKey="risingArjunApp.students.leavingReason">Leaving Reason</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.leavingReason}</dd>
            <dt>
              <span id="infoSource">
                <Translate contentKey="risingArjunApp.students.infoSource">Info Source</Translate>
              </span>
            </dt>
            <dd>{studentsEntity.infoSource}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.students.user">User</Translate>
            </dt>
            <dd>{studentsEntity.userLogin ? studentsEntity.userLogin : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.students.course">Course</Translate>
            </dt>
            <dd>
              {studentsEntity.courses
                ? studentsEntity.courses.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.course}</a>
                      {i === studentsEntity.courses.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/students-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/students-my-suffix/${studentsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ students }: IRootState) => ({
  studentsEntity: students.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentsMySuffixDetail);
