import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './chapters-my-suffix.reducer';
import { IChaptersMySuffix } from 'app/shared/model/chapters-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChaptersMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ChaptersMySuffixDetail extends React.Component<IChaptersMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { chaptersEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="risingArjunApp.chapters.detail.title">Chapters</Translate> [<b>{chaptersEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="chapterId">
                <Translate contentKey="risingArjunApp.chapters.chapterId">Chapter Id</Translate>
              </span>
            </dt>
            <dd>{chaptersEntity.chapterId}</dd>
            <dt>
              <span id="chapterTitle">
                <Translate contentKey="risingArjunApp.chapters.chapterTitle">Chapter Title</Translate>
              </span>
            </dt>
            <dd>{chaptersEntity.chapterTitle}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.chapters.course">Course</Translate>
            </dt>
            <dd>{chaptersEntity.courseCourse ? chaptersEntity.courseCourse : ''}</dd>
            <dt>
              <Translate contentKey="risingArjunApp.chapters.subject">Subject</Translate>
            </dt>
            <dd>{chaptersEntity.subjectSubjectTitle ? chaptersEntity.subjectSubjectTitle : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/chapters-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/chapters-my-suffix/${chaptersEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ chapters }: IRootState) => ({
  chaptersEntity: chapters.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ChaptersMySuffixDetail);
