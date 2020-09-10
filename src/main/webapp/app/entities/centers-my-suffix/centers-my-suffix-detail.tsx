import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './centers-my-suffix.reducer';
import { ICentersMySuffix } from 'app/shared/model/centers-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICentersMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CentersMySuffixDetail extends React.Component<ICentersMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { centersEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.centers.detail.title">Centers</Translate> [<b>{centersEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="centerCode">
                <Translate contentKey="risingArjunApp.centers.centerCode">Center Code</Translate>
              </span>
            </dt>
            <dd>{centersEntity.centerCode}</dd>
            <dt>
              <span id="centerTitle">
                <Translate contentKey="risingArjunApp.centers.centerTitle">Center Title</Translate>
              </span>
            </dt>
            <dd>{centersEntity.centerTitle}</dd>
            <dt>
              <span id="streetNo">
                <Translate contentKey="risingArjunApp.centers.streetNo">Street No</Translate>
              </span>
            </dt>
            <dd>{centersEntity.streetNo}</dd>
            <dt>
              <span id="city">
                <Translate contentKey="risingArjunApp.centers.city">City</Translate>
              </span>
            </dt>
            <dd>{centersEntity.city}</dd>
            <dt>
              <span id="state">
                <Translate contentKey="risingArjunApp.centers.state">State</Translate>
              </span>
            </dt>
            <dd>{centersEntity.state}</dd>
            <dt>
              <span id="pincode">
                <Translate contentKey="risingArjunApp.centers.pincode">Pincode</Translate>
              </span>
            </dt>
            <dd>{centersEntity.pincode}</dd>
          </dl>
          <Button tag={Link} to="/entity/centers-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/centers-my-suffix/${centersEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ centers }: IRootState) => ({
  centersEntity: centers.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CentersMySuffixDetail);
