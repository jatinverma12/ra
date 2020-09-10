import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './scholarships-my-suffix.reducer';
import { IScholarshipsMySuffix } from 'app/shared/model/scholarships-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScholarshipsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ScholarshipsMySuffixDetail extends React.Component<IScholarshipsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { scholarshipsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.scholarships.detail.title">Scholarships</Translate> [<b>{scholarshipsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="minMarks">
                <Translate contentKey="risingArjunApp.scholarships.minMarks">Min Marks</Translate>
              </span>
            </dt>
            <dd>{scholarshipsEntity.minMarks}</dd>
            <dt>
              <span id="percent">
                <Translate contentKey="risingArjunApp.scholarships.percent">Percent</Translate>
              </span>
            </dt>
            <dd>{scholarshipsEntity.percent}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.scholarships.session">Session</Translate>
            </dt>
            <dd>{scholarshipsEntity.sessionAcadSession ? scholarshipsEntity.sessionAcadSession : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/scholarships-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/scholarships-my-suffix/${scholarshipsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ scholarships }: IRootState) => ({
  scholarshipsEntity: scholarships.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ScholarshipsMySuffixDetail);
