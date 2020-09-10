import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './subjects-base-fee-my-suffix.reducer';
import { ISubjectsBaseFeeMySuffix } from 'app/shared/model/subjects-base-fee-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubjectsBaseFeeMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SubjectsBaseFeeMySuffixDetail extends React.Component<ISubjectsBaseFeeMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { subjectsBaseFeeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.subjectsBaseFee.detail.title">SubjectsBaseFee</Translate> [
            <b>{subjectsBaseFeeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="baseFee">
                <Translate contentKey="risingArjunApp.subjectsBaseFee.baseFee">Base Fee</Translate>
              </span>
            </dt>
            <dd>{subjectsBaseFeeEntity.baseFee}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.subjectsBaseFee.course">Course</Translate>
            </dt>
            <dd>{subjectsBaseFeeEntity.courseCourse ? subjectsBaseFeeEntity.courseCourse : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.subjectsBaseFee.session">Session</Translate>
            </dt>
            <dd>{subjectsBaseFeeEntity.sessionAcadSession ? subjectsBaseFeeEntity.sessionAcadSession : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/subjects-base-fee-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/subjects-base-fee-my-suffix/${subjectsBaseFeeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ subjectsBaseFee }: IRootState) => ({
  subjectsBaseFeeEntity: subjectsBaseFee.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SubjectsBaseFeeMySuffixDetail);
