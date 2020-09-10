import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './courses-my-suffix.reducer';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICoursesMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CoursesMySuffixDetail extends React.Component<ICoursesMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { coursesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.courses.detail.title">Courses</Translate> [<b>{coursesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="courseId">
                <Translate contentKey="risingArjunApp.courses.courseId">Course Id</Translate>
              </span>
            </dt>
            <dd>{coursesEntity.courseId}</dd>
            <dt>
              <span id="course">
                <Translate contentKey="risingArjunApp.courses.course">Course</Translate>
              </span>
            </dt>
            <dd>{coursesEntity.course}</dd>
          </dl>
          <Button tag={Link} to="/entity/courses-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/courses-my-suffix/${coursesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ courses }: IRootState) => ({
  coursesEntity: courses.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CoursesMySuffixDetail);
