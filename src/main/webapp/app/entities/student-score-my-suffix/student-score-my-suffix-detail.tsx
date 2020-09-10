import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './student-score-my-suffix.reducer';
import { IStudentScoreMySuffix } from 'app/shared/model/student-score-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentScoreMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudentScoreMySuffixDetail extends React.Component<IStudentScoreMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studentScoreEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.studentScore.detail.title">StudentScore</Translate> [<b>{studentScoreEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="answer">
                <Translate contentKey="risingArjunApp.studentScore.answer">Answer</Translate>
              </span>
            </dt>
            <dd>{studentScoreEntity.answer}</dd>
            <dt>
              <span id="score">
                <Translate contentKey="risingArjunApp.studentScore.score">Score</Translate>
              </span>
            </dt>
            <dd>{studentScoreEntity.score}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="risingArjunApp.studentScore.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={studentScoreEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentScore.student">Student</Translate>
            </dt>
            <dd>{studentScoreEntity.studentStudentRegId ? studentScoreEntity.studentStudentRegId : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentScore.questionId">Question Id</Translate>
            </dt>
            <dd>{studentScoreEntity.questionIdId ? studentScoreEntity.questionIdId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/student-score-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/student-score-my-suffix/${studentScoreEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ studentScore }: IRootState) => ({
  studentScoreEntity: studentScore.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentScoreMySuffixDetail);
