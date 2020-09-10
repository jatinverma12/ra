export interface IRoleAccessMySuffix {
  id?: number;
  create?: boolean;
  read?: boolean;
  update?: boolean;
  del?: boolean;
  roleName?: string;
  roleId?: number;
  featureFeatureDetail?: string;
  featureId?: number;
}

export const defaultValue: Readonly<IRoleAccessMySuffix> = {
  create: false,
  read: false,
  update: false,
  del: false
};
