import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/blog">
      <Translate contentKey="global.menu.entities.blog" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/entry">
      <Translate contentKey="global.menu.entities.entry" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/tag">
      <Translate contentKey="global.menu.entities.tag" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/jhiauthority-my-suffix">
      <Translate contentKey="global.menu.entities.jhiauthorityMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/features-my-suffix">
      <Translate contentKey="global.menu.entities.featuresMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/role-access-my-suffix">
      <Translate contentKey="global.menu.entities.roleAccessMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/user-details-my-suffix">
      <Translate contentKey="global.menu.entities.userDetailsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/user-preferences-my-suffix">
      <Translate contentKey="global.menu.entities.userPreferencesMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/courses-my-suffix">
      <Translate contentKey="global.menu.entities.coursesMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/students-my-suffix">
      <Translate contentKey="global.menu.entities.studentsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/subjects-my-suffix">
      <Translate contentKey="global.menu.entities.subjectsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/academic-sessions-my-suffix">
      <Translate contentKey="global.menu.entities.academicSessionsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/students-subjects-my-suffix">
      <Translate contentKey="global.menu.entities.studentsSubjectsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/subjects-base-fee-my-suffix">
      <Translate contentKey="global.menu.entities.subjectsBaseFeeMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/discounts-my-suffix">
      <Translate contentKey="global.menu.entities.discountsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/scholarships-my-suffix">
      <Translate contentKey="global.menu.entities.scholarshipsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/student-fees-my-suffix">
      <Translate contentKey="global.menu.entities.studentFeesMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/employees-my-suffix">
      <Translate contentKey="global.menu.entities.employeesMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/centers-my-suffix">
      <Translate contentKey="global.menu.entities.centersMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/center-head-my-suffix">
      <Translate contentKey="global.menu.entities.centerHeadMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/teachers-my-suffix">
      <Translate contentKey="global.menu.entities.teachersMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/teachers-share-my-suffix">
      <Translate contentKey="global.menu.entities.teachersShareMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/salaries-payment-my-suffix">
      <Translate contentKey="global.menu.entities.salariesPaymentMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/expenses-my-suffix">
      <Translate contentKey="global.menu.entities.expensesMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/chapters-my-suffix">
      <Translate contentKey="global.menu.entities.chaptersMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/questions-my-suffix">
      <Translate contentKey="global.menu.entities.questionsMySuffix" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/student-score-my-suffix">
      <Translate contentKey="global.menu.entities.studentScoreMySuffix" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
