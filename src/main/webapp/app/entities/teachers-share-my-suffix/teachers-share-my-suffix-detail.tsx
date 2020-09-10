import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './teachers-share-my-suffix.reducer';
import { ITeachersShareMySuffix } from 'app/shared/model/teachers-share-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITeachersShareMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TeachersShareMySuffixDetail extends React.Component<ITeachersShareMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { teachersShareEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.teachersShare.detail.title">TeachersShare</Translate> [<b>{teachersShareEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="share">
                <Translate contentKey="risingArjunApp.teachersShare.share">Share</Translate>
              </span>
            </dt>
            <dd>{teachersShareEntity.share}</dd>
            <dt>
              <span id="plannedClasses">
                <Translate contentKey="risingArjunApp.teachersShare.plannedClasses">Planned Classes</Translate>
              </span>
            </dt>
            <dd>{teachersShareEntity.plannedClasses}</dd>
            <dt>
              <span id="actualClasses">
                <Translate contentKey="risingArjunApp.teachersShare.actualClasses">Actual Classes</Translate>
              </span>
            </dt>
            <dd>{teachersShareEntity.actualClasses}</dd>
            <dt>
              <span id="shareCorrection">
                <Translate contentKey="risingArjunApp.teachersShare.shareCorrection">Share Correction</Translate>
              </span>
            </dt>
            <dd>{teachersShareEntity.shareCorrection}</dd>
            <dt>
              <span id="month">
                <Translate contentKey="risingArjunApp.teachersShare.month">Month</Translate>
              </span>
            </dt>
            <dd>{teachersShareEntity.month}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="risingArjunApp.teachersShare.remarks">Remarks</Translate>
              </span>
            </dt>
            <dd>{teachersShareEntity.remarks}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.teachersShare.teacher">Teacher</Translate>
            </dt>
            <dd>{teachersShareEntity.teacherEmployeeId ? teachersShareEntity.teacherEmployeeId : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.teachersShare.subject">Subject</Translate>
            </dt>
            <dd>{teachersShareEntity.subjectSubjectTitle ? teachersShareEntity.subjectSubjectTitle : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.teachersShare.course">Course</Translate>
            </dt>
            <dd>{teachersShareEntity.courseCourse ? teachersShareEntity.courseCourse : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.teachersShare.session">Session</Translate>
            </dt>
            <dd>{teachersShareEntity.sessionAcadSessionId ? teachersShareEntity.sessionAcadSessionId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/teachers-share-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/teachers-share-my-suffix/${teachersShareEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ teachersShare }: IRootState) => ({
  teachersShareEntity: teachersShare.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TeachersShareMySuffixDetail);
