import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './center-head-my-suffix.reducer';
import { ICenterHeadMySuffix } from 'app/shared/model/center-head-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICenterHeadMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CenterHeadMySuffixDetail extends React.Component<ICenterHeadMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { centerHeadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.centerHead.detail.title">CenterHead</Translate> [<b>{centerHeadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="risingArjunApp.centerHead.centerhead">Centerhead</Translate>
            </dt>
            <dd>{centerHeadEntity.centerheadEmployeeId ? centerHeadEntity.centerheadEmployeeId : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.centerHead.center">Center</Translate>
            </dt>
            <dd>
              {centerHeadEntity.centers
                ? centerHeadEntity.centers.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.centerTitle}</a>
                      {i === centerHeadEntity.centers.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/center-head-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/center-head-my-suffix/${centerHeadEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ centerHead }: IRootState) => ({
  centerHeadEntity: centerHead.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CenterHeadMySuffixDetail);
