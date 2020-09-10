import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './student-fees-my-suffix.reducer';
import { IStudentFeesMySuffix } from 'app/shared/model/student-fees-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentFeesMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudentFeesMySuffixDetail extends React.Component<IStudentFeesMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studentFeesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.studentFees.detail.title">StudentFees</Translate> [<b>{studentFeesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fee">
                <Translate contentKey="risingArjunApp.studentFees.fee">Fee</Translate>
              </span>
            </dt>
            <dd>{studentFeesEntity.fee}</dd>
            <dt>
              <span id="feeCorrection">
                <Translate contentKey="risingArjunApp.studentFees.feeCorrection">Fee Correction</Translate>
              </span>
            </dt>
            <dd>{studentFeesEntity.feeCorrection}</dd>
            <dt>
              <span id="month">
                <Translate contentKey="risingArjunApp.studentFees.month">Month</Translate>
              </span>
            </dt>
            <dd>{studentFeesEntity.month}</dd>
            <dt>
              <span id="feeStatus">
                <Translate contentKey="risingArjunApp.studentFees.feeStatus">Fee Status</Translate>
              </span>
            </dt>
            <dd>{studentFeesEntity.feeStatus ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="risingArjunApp.studentFees.remarks">Remarks</Translate>
              </span>
            </dt>
            <dd>{studentFeesEntity.remarks}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentFees.registrationno">Registrationno</Translate>
            </dt>
            <dd>{studentFeesEntity.registrationnoStudentRegId ? studentFeesEntity.registrationnoStudentRegId : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentFees.subject">Subject</Translate>
            </dt>
            <dd>{studentFeesEntity.subjectSubjectTitle ? studentFeesEntity.subjectSubjectTitle : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentFees.session">Session</Translate>
            </dt>
            <dd>{studentFeesEntity.sessionAcadSession ? studentFeesEntity.sessionAcadSession : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentFees.teacher">Teacher</Translate>
            </dt>
            <dd>{studentFeesEntity.teacherEmployeeId ? studentFeesEntity.teacherEmployeeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/student-fees-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/student-fees-my-suffix/${studentFeesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ studentFees }: IRootState) => ({
  studentFeesEntity: studentFees.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentFeesMySuffixDetail);
