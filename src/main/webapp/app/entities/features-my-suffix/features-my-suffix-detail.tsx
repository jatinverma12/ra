import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './features-my-suffix.reducer';
import { IFeaturesMySuffix } from 'app/shared/model/features-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeaturesMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FeaturesMySuffixDetail extends React.Component<IFeaturesMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { featuresEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.features.detail.title">Features</Translate> [<b>{featuresEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="featureId">
                <Translate contentKey="risingArjunApp.features.featureId">Feature Id</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.featureId}</dd>
            <dt>
              <span id="featureDetail">
                <Translate contentKey="risingArjunApp.features.featureDetail">Feature Detail</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.featureDetail}</dd>
          </dl>
          <Button tag={Link} to="/entity/features-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/features-my-suffix/${featuresEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ features }: IRootState) => ({
  featuresEntity: features.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FeaturesMySuffixDetail);
