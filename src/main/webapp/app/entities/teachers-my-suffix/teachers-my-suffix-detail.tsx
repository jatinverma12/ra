import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './teachers-my-suffix.reducer';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITeachersMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TeachersMySuffixDetail extends React.Component<ITeachersMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { teachersEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.teachers.detail.title">Teachers</Translate> [<b>{teachersEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="risingArjunApp.teachers.teacher">Teacher</Translate>
            </dt>
            <dd>{teachersEntity.teacherEmployeeId ? teachersEntity.teacherEmployeeId : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.teachers.subjects">Subjects</Translate>
            </dt>
            <dd>
              {teachersEntity.subjects
                ? teachersEntity.subjects.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.subjectTitle}</a>
                      {i === teachersEntity.subjects.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="risingArjunApp.teachers.courses">Courses</Translate>
            </dt>
            <dd>
              {teachersEntity.courses
                ? teachersEntity.courses.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.course}</a>
                      {i === teachersEntity.courses.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/teachers-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/teachers-my-suffix/${teachersEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ teachers }: IRootState) => ({
  teachersEntity: teachers.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TeachersMySuffixDetail);
