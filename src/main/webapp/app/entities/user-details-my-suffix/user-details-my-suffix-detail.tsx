import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-details-my-suffix.reducer';
import { IUserDetailsMySuffix } from 'app/shared/model/user-details-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserDetailsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserDetailsMySuffixDetail extends React.Component<IUserDetailsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.userDetails.detail.title">UserDetails</Translate> [<b>{userDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="mobileNo">
                <Translate contentKey="risingArjunApp.userDetails.mobileNo">Mobile No</Translate>
              </span>
            </dt>
            <dd>{userDetailsEntity.mobileNo}</dd>
            <dt>
              <span id="dob">
                <Translate contentKey="risingArjunApp.userDetails.dob">Dob</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={userDetailsEntity.dob} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="houseNo">
                <Translate contentKey="risingArjunApp.userDetails.houseNo">House No</Translate>
              </span>
            </dt>
            <dd>{userDetailsEntity.houseNo}</dd>
            <dt>
              <span id="streetNo">
                <Translate contentKey="risingArjunApp.userDetails.streetNo">Street No</Translate>
              </span>
            </dt>
            <dd>{userDetailsEntity.streetNo}</dd>
            <dt>
              <span id="city">
                <Translate contentKey="risingArjunApp.userDetails.city">City</Translate>
              </span>
            </dt>
            <dd>{userDetailsEntity.city}</dd>
            <dt>
              <span id="state">
                <Translate contentKey="risingArjunApp.userDetails.state">State</Translate>
              </span>
            </dt>
            <dd>{userDetailsEntity.state}</dd>
            <dt>
              <span id="pincode">
                <Translate contentKey="risingArjunApp.userDetails.pincode">Pincode</Translate>
              </span>
            </dt>
            <dd>{userDetailsEntity.pincode}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.userDetails.user">User</Translate>
            </dt>
            <dd>{userDetailsEntity.userLogin ? userDetailsEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-details-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-details-my-suffix/${userDetailsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ userDetails }: IRootState) => ({
  userDetailsEntity: userDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserDetailsMySuffixDetail);
