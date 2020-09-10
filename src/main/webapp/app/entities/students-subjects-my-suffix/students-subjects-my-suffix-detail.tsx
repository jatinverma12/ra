import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './students-subjects-my-suffix.reducer';
import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentsSubjectsMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudentsSubjectsMySuffixDetail extends React.Component<IStudentsSubjectsMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studentsSubjectsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.studentsSubjects.detail.title">StudentsSubjects</Translate> [
            <b>{studentsSubjectsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="month">
                <Translate contentKey="risingArjunApp.studentsSubjects.month">Month</Translate>
              </span>
            </dt>
            <dd>{studentsSubjectsEntity.month}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentsSubjects.registrationno">Registrationno</Translate>
            </dt>
            <dd>{studentsSubjectsEntity.registrationnoStudentRegId ? studentsSubjectsEntity.registrationnoStudentRegId : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentsSubjects.session">Session</Translate>
            </dt>
            <dd>{studentsSubjectsEntity.sessionAcadSession ? studentsSubjectsEntity.sessionAcadSession : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentsSubjects.subjects">Subjects</Translate>
            </dt>
            <dd>
              {studentsSubjectsEntity.subjects
                ? studentsSubjectsEntity.subjects.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.subjectTitle}</a>
                      {i === studentsSubjectsEntity.subjects.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="risingArjunApp.studentsSubjects.course">Course</Translate>
            </dt>
            <dd>
              {studentsSubjectsEntity.courses
                ? studentsSubjectsEntity.courses.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.course}</a>
                      {i === studentsSubjectsEntity.courses.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/students-subjects-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/students-subjects-my-suffix/${studentsSubjectsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ studentsSubjects }: IRootState) => ({
  studentsSubjectsEntity: studentsSubjects.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentsSubjectsMySuffixDetail);
