import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './center-head-my-suffix.reducer';
import { ICenterHeadMySuffix } from 'app/shared/model/center-head-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICenterHeadMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class CenterHeadMySuffix extends React.Component<ICenterHeadMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { centerHeadList, match } = this.props;
    return (
      <div>
        <h2 id="center-head-my-suffix-heading">
          <Translate contentKey="risingArjunApp.centerHead.home.title">Center Heads</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="risingArjunApp.centerHead.home.createLabel">Create new Center Head</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {centerHeadList && centerHeadList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centerHead.centerhead">Centerhead</Translate>
                  </th>
                  <th>
                    <Translate contentKey="risingArjunApp.centerHead.center">Center</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {centerHeadList.map((centerHead, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${centerHead.id}`} color="link" size="sm">
                        {centerHead.id}
                      </Button>
                    </td>
                    <td>
                      {centerHead.centerheadEmployeeId ? (
                        <Link to={`employees-my-suffix/${centerHead.centerheadId}`}>{centerHead.centerheadEmployeeId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {centerHead.centers
                        ? centerHead.centers.map((val, j) => (
                            <span key={j}>
                              <Link to={`centers-my-suffix/${val.id}`}>{val.centerTitle}</Link>
                              {j === centerHead.centers.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${centerHead.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${centerHead.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${centerHead.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="risingArjunApp.centerHead.home.notFound">No Center Heads found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ centerHead }: IRootState) => ({
  centerHeadList: centerHead.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CenterHeadMySuffix);
