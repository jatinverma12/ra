import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './discounts-my-suffix.reducer';
import { IDiscountsMySuffix } from 'app/shared/model/discounts-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDiscountsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DiscountsMySuffixDetail extends React.Component<IDiscountsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { discountsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.discounts.detail.title">Discounts</Translate> [<b>{discountsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="subject2">
                <Translate contentKey="risingArjunApp.discounts.subject2">Subject 2</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject2}</dd>
            <dt>
              <span id="subject3">
                <Translate contentKey="risingArjunApp.discounts.subject3">Subject 3</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject3}</dd>
            <dt>
              <span id="subject4">
                <Translate contentKey="risingArjunApp.discounts.subject4">Subject 4</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject4}</dd>
            <dt>
              <span id="subject5">
                <Translate contentKey="risingArjunApp.discounts.subject5">Subject 5</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject5}</dd>
            <dt>
              <span id="subject6">
                <Translate contentKey="risingArjunApp.discounts.subject6">Subject 6</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject6}</dd>
            <dt>
              <span id="subject7">
                <Translate contentKey="risingArjunApp.discounts.subject7">Subject 7</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject7}</dd>
            <dt>
              <span id="subject8">
                <Translate contentKey="risingArjunApp.discounts.subject8">Subject 8</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.subject8}</dd>
            <dt>
              <span id="quarterly">
                <Translate contentKey="risingArjunApp.discounts.quarterly">Quarterly</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.quarterly}</dd>
            <dt>
              <span id="halfYearly">
                <Translate contentKey="risingArjunApp.discounts.halfYearly">Half Yearly</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.halfYearly}</dd>
            <dt>
              <span id="annually">
                <Translate contentKey="risingArjunApp.discounts.annually">Annually</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.annually}</dd>
            <dt>
              <span id="sibling">
                <Translate contentKey="risingArjunApp.discounts.sibling">Sibling</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.sibling}</dd>
            <dt>
              <span id="referral">
                <Translate contentKey="risingArjunApp.discounts.referral">Referral</Translate>
              </span>
            </dt>
            <dd>{discountsEntity.referral}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.discounts.session">Session</Translate>
            </dt>
            <dd>{discountsEntity.sessionAcadSession ? discountsEntity.sessionAcadSession : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/discounts-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/discounts-my-suffix/${discountsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ discounts }: IRootState) => ({
  discountsEntity: discounts.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DiscountsMySuffixDetail);
